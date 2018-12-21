package com.kingwant.videoAnaly.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Objects;
import com.kingwant.videoAnaly.annonation.ValidationParam;
import com.kingwant.videoAnaly.base.PublicResult;
import com.kingwant.videoAnaly.base.PublicResultConstant;
import com.kingwant.videoAnaly.entity.BootstrapPageEntity;
import com.kingwant.videoAnaly.entity.BootstrapPageResult;
import com.kingwant.videoAnaly.entity.User;
import com.kingwant.videoAnaly.entity.VideoCamera;
import com.kingwant.videoAnaly.entity.VideoNumberAbnormal;
import com.kingwant.videoAnaly.mapper.VideoCameraMapper;
import com.kingwant.videoAnaly.service.IVideoCameraService;
import com.kingwant.videoAnaly.service.IVideoDressAbnormalService;
import com.kingwant.videoAnaly.service.IVideoNumberAbnormalService;
import com.kingwant.videoAnaly.util.ComUtil;
import com.kingwant.videoAnaly.util.HttpUtil;
import com.kingwant.videoAnaly.util.KwHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;

import io.swagger.annotations.ApiOperation;
import xyz.michaelch.mchtools.hepler.RequestHelper;

@RestController
@RequestMapping("/api")
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
    public BootstrapPageResult<String> videoSetNumber(@ValidationParam("type,number,id")@RequestBody JSONObject requestJson){
		try {
			String type = requestJson.getString("type");
			String number = requestJson.getString("numbber");
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
	@GetMapping("/video/pushMessage")
	@ResponseBody
	public BootstrapPageResult<List<VideoCamera>> videoGetMessage(JsonArray message){
		try {
			if(ComUtil.isEmpty(message)){
				return new BootstrapPageResult<>(false,"获取推送消息失败",null);
			}
			
			HttpUtil.doPost(URL, message.toString());
			return new BootstrapPageResult<>(true,"推送消息成功",null);
		} catch (Exception e) {
			return new BootstrapPageResult<>(false,"推送消息失败"+e.getMessage(),null);
		}
	}
	
	
    
    
    
    
    
    
}