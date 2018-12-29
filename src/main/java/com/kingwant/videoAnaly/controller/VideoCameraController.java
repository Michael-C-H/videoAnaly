package com.kingwant.videoAnaly.controller;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Objects;
import com.kingwant.videoAnaly.entity.BootstrapPageEntity;
import com.kingwant.videoAnaly.entity.BootstrapPageResult;
import com.kingwant.videoAnaly.entity.VideoCamera;
import com.kingwant.videoAnaly.service.IVideoCameraService;
import com.kingwant.videoAnaly.util.ComUtil;
import com.kingwant.videoAnaly.util.KwHelper;

import springfox.documentation.annotations.ApiIgnore;
import xyz.michaelch.mchtools.hepler.RequestHelper;

@RestController
@ApiIgnore()
public class VideoCameraController {
	
	@Resource
	private IVideoCameraService vcm;

	@RequestMapping("/videocameralist")
    @ResponseBody
    public BootstrapPageResult<VideoCamera> test(HttpServletRequest request,BootstrapPageEntity<VideoCamera> page){
    	
    	//获取搜索字段值
    	String name=RequestHelper.toStr(request, "name",null);
    	String code=RequestHelper.toStr(request, "code",null);
    	Wrapper<VideoCamera> wrapper=new EntityWrapper<VideoCamera>();
    	//判断搜索值是否为空。拼接搜索条件
    	if (!KwHelper.isNullOrEmpty(name)&&!KwHelper.isNullOrEmpty(code)) {			
    		wrapper.like("name", name).and().like("code", code);
		}
    	if (!KwHelper.isNullOrEmpty(name)&&KwHelper.isNullOrEmpty(code)) {			
    		wrapper.like("name", name);
    	}
    	if (KwHelper.isNullOrEmpty(name)&&!KwHelper.isNullOrEmpty(code)) {			
    		wrapper.like("code", code);
    	}
    	//执行查询，返回list
    	Page<VideoCamera> list=vcm.selectPage(page.getPageObj(),wrapper);
    	Page<VideoCamera> updatelist = vcm.updateAnalyType(list);
        return new BootstrapPageResult<VideoCamera>(updatelist,page.getDraw());
    }
	
	@RequestMapping("/updateorinsertvc")
	public BootstrapPageResult<String> updatevc(HttpServletRequest request){
		//获取前端编辑框或者新增框传的值
		String id = request.getParameter("formid");
		String CODE = request.getParameter("CODE");
		String NAME = request.getParameter("NAME");
		String SOURCE = request.getParameter("SOURCE");
		String[] anltsisType=request.getParameterValues("anltsisType");
		
		String ONLINE_NUM = request.getParameter("ONLINE_NUM");
		String TOTAL = request.getParameter("TOTAL");
		StringBuilder sb = new StringBuilder();
		if(ComUtil.isEmpty(CODE)){
			return new BootstrapPageResult<String>("编号为空",false);
		}
		if(ComUtil.isEmpty(NAME)){
			return new BootstrapPageResult<String>("名称为空",false);
		}
		if(ComUtil.isEmpty(SOURCE)){
			return new BootstrapPageResult<String>("数据源地址为空",false);
		}
		
		//分析拼接----（分析类型）数据，数据库存储A，B，C形式
		if(ComUtil.isEmpty(anltsisType)){
			sb = null;
		}else{
			List<String> list=Arrays.asList(anltsisType);
			if(list.contains("A")){
				sb =sb.append("A,");
			}
			if(list.contains("B")){
				if(ComUtil.isEmpty(ONLINE_NUM)){
					return new BootstrapPageResult<String>("可见范围阀值为空",false);
				}
				sb =sb.append("B,");
			}
			if(list.contains("C")){
				if(ComUtil.isEmpty(TOTAL)){
					return new BootstrapPageResult<String>("总人数阀值为空",false);
				}
				sb = sb.append("C,");
			}
			sb = sb.deleteCharAt(sb.length()-1);
		}
		//当新增的时候，页面传入后台id为空
		if(ComUtil.isEmpty(id)){
			VideoCamera selectsource = vcm.selectsource(SOURCE);
			if(!ComUtil.isEmpty(selectsource)){
				return new BootstrapPageResult<String>("数据源不能重复",false);
			}
			VideoCamera videoCamera = new VideoCamera();
			String uuid = UUID.randomUUID().toString();
			uuid = uuid.replace("-", "");
			videoCamera = setvalue(CODE, NAME, SOURCE, ONLINE_NUM, TOTAL, sb, videoCamera, uuid,anltsisType,true);
			try{
				vcm.insert(videoCamera);
				return new BootstrapPageResult<String>("新增成功",true);
			}catch (Exception e) {
				return new BootstrapPageResult<String>("新增失败"+e.getMessage(),false);
			}
		}else{
			//当编辑的时候，页面传入后台，当前编辑数据的Id
			//判断数据源是否重复
			VideoCamera selectsource = vcm.selectuptsource(SOURCE,id);
			if(!ComUtil.isEmpty(selectsource)){
				return new BootstrapPageResult<String>("数据源不能重复",false);
			}
			VideoCamera videoCamera = vcm.selectById(id);
			if(Objects.equal(null, videoCamera)){
				return new BootstrapPageResult<String>("修改的该条数据已经不存在",false);
			}
			//将页面传入的值设入VideoCamera对象
			videoCamera = setvalue(CODE, NAME, SOURCE, ONLINE_NUM, TOTAL, sb, videoCamera, id,anltsisType,false);
			try{
				vcm.updateById(videoCamera);
				return new BootstrapPageResult<String>("修改成功",true);
			}catch (Exception e) {
				return new BootstrapPageResult<String>("修改失败"+e.getMessage(),false);
			}
			
		}
		
	}
	//修改或者新增的时候,往VideoCamera中设值
	private VideoCamera setvalue(String CODE, String NAME, String SOURCE, String ONLINE_NUM, String TOTAL, StringBuilder sb,
			VideoCamera videoCamera, String uuid,String[] anltsisType,Boolean flag) {
		videoCamera.setId(uuid);
		videoCamera.setCode(CODE);
		videoCamera.setName(NAME);
		videoCamera.setSource(SOURCE);
		if(Objects.equal(null, sb)){
			videoCamera.setAnltsisType("");
		}else{
			videoCamera.setAnltsisType(sb.toString());
		}
		//这里onlinenum和total先设为空字符串。因为当页面没选择分析类型，
		//依然会传入onlinenum和total值
		videoCamera.setOnlineNum("");
		videoCamera.setTotal("");
		if(ComUtil.isEmpty(anltsisType)){
			
			return videoCamera;
		}else{
			//判断分析类型是否选中
			List<String> list=Arrays.asList(anltsisType);
			if(list.contains("B")){
				videoCamera.setOnlineNum(ONLINE_NUM);
			}
			if(list.contains("C")){
				videoCamera.setTotal(TOTAL);
			}
			
		}
		return videoCamera;
	}
	/*
	 * 根据id删除一条数据
	 */
	@RequestMapping("/deletevc")
	public BootstrapPageResult<String> deletevc(String id){
		VideoCamera videoCamera = vcm.selectById(id);
		if(Objects.equal(null, videoCamera)){
			return new BootstrapPageResult<String>("删除的该条数据已经不存在",true);
		}
		try{
			vcm.deleteById(id);
			return new BootstrapPageResult<String>("删除成功",true);
		}catch (Exception e) {
			return new BootstrapPageResult<String>("删除失败",false);
		}
	}
	@RequestMapping("/selectone")
	@ResponseBody
	public BootstrapPageResult<VideoCamera> selectone(String id){
		try{
			VideoCamera videoCamera = vcm.selectById(id);
			return new BootstrapPageResult<VideoCamera>(true,"成功", videoCamera);
		}catch (Exception e) {
			return new BootstrapPageResult<VideoCamera>("后台查询系统错误", false);
		}
	}
    
    
    
    
    
    
}