package com.kingwant.videoAnaly.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.base.Objects;
import com.kingwant.videoAnaly.annonation.ValidationParam;
import com.kingwant.videoAnaly.entity.BootstrapPageResult;
import com.kingwant.videoAnaly.entity.VideoCamera;
import com.kingwant.videoAnaly.service.IVideoCameraService;
import com.kingwant.videoAnaly.service.IVideoDressAbnormalService;
import com.kingwant.videoAnaly.service.IVideoNumberAbnormalService;
import com.kingwant.videoAnaly.util.ComUtil;
import com.kingwant.videoAnaly.util.HttpUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags="对外接口")
public class ApiController {
	
	@Resource
	private IVideoCameraService vcm;
	@Resource
	private IVideoNumberAbnormalService vns;
	@Resource
	private IVideoDressAbnormalService vds;
	@Value("${sys.sendmsg.url}")
    private String URL;
	
	
	//1.设置人数阀值(B/S) type=1修改total type=2修改online
	@PostMapping("/video/setNumber")
    @ResponseBody
    @ApiOperation(value="设置人数阀值",notes="需要传入参数,{type:1,number:5,id:'201811207'")
    public BootstrapPageResult<String> videoSetNumber(@ValidationParam("type,number,id")@RequestBody JSONObject requestJson){
		try {
			String type = requestJson.getString("type");
			String number = requestJson.getString("number");
			String id = requestJson.getString("id");
			if(ComUtil.isEmpty(type)||ComUtil.isEmpty(number)||ComUtil.isEmpty(id)){
				return new BootstrapPageResult<>(false,"输入参数存在空值",null);
			}
			VideoCamera videoCamera = vcm.selectById(id);
			if(Objects.equal(null, videoCamera)){
				return new BootstrapPageResult<>(false,"后台根据id查询数据为空",null);
			}
			if("1".equals(type)){
				videoCamera.setTotal(number);
			}else if("2".equals(type)){
				videoCamera.setOnlineNum(number);
			}
			vcm.updateById(videoCamera);
			return new BootstrapPageResult<>(true,"设置成功",null);
		} catch (Exception e) {
			return new BootstrapPageResult<>(false,"设置失败"+e.getMessage(),null);
		}
    }
	//2.获取摄像头列表(B/S) 
	@GetMapping("/video/getCameras")
	@ApiOperation(value="获取摄像头配置列表",notes="不需要传入参数")
	@ResponseBody
	public BootstrapPageResult<List<VideoCamera>> videoGetCameras(){
		try {
			Wrapper<VideoCamera> wrapper=new EntityWrapper<VideoCamera>();
			List<VideoCamera> vclist = vcm.selectList(wrapper);
			return new BootstrapPageResult<>(true,"获取列表成功",vclist);
		} catch (Exception e) {
			return new BootstrapPageResult<>(false,"获取列表失败"+e.getMessage(),null);
		}
	}
	//3.视频分析应用推送异常消息(C/S),接收CS端推送的消息
	@PostMapping("/video/pushMessage")
	@ApiOperation(value="视频分析应用向视频分析系统推送异常消息",notes="需要传入参数:json数组")
	@ResponseBody
	public BootstrapPageResult<String> pushMessage(@RequestBody JSONArray obj){
		try {
			if(ComUtil.isEmpty(obj)){
				return new BootstrapPageResult<>(false,"获取推送消息失败",null);
			}
			
			HttpUtil.doPost(URL, obj.toString());
			return new BootstrapPageResult<>(true,"推送消息成功",null);
		} catch (Exception e) {
			return new BootstrapPageResult<>(false,"推送消息失败"+e.getMessage(),null);
		}
	}
	//4.测试接收推送消息
	@PostMapping("/video/getMessage")
	@ApiOperation(value="测试接收推送消息",notes="需要传入参数:json数组")
	@ResponseBody
	public BootstrapPageResult<String> videoGetMessage(HttpServletResponse rep,HttpServletRequest request){
		try {
			//String json = request.getParameter("param");  //这是通过通过get方式去url 拼接的键值对，post方式取不到值。
			request.setCharacterEncoding("UTF-8");         //返回页面防止出现中文乱码
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));//post方式传递读取字符流
			String jsonStr = null;
			StringBuilder result = new StringBuilder();
			try {
			while ((jsonStr = reader.readLine()) != null) {
			result.append(jsonStr);
			}
			} catch (IOException e) {
			e.printStackTrace();
			}
			reader.close();// 关闭输入流
			JSONObject jsonObject = JSONObject.parseObject(result.toString()); // 取一个json转换为对象
			return new BootstrapPageResult<>(true,"推送消息成功",null);
		} catch (Exception e) {
			return new BootstrapPageResult<>(false,"推送消息失败"+e.getMessage(),null);
		}
	}
	
	
    
    
    
    
    
    
}