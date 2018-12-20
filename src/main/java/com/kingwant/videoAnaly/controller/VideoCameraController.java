package com.kingwant.videoAnaly.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
import com.kingwant.videoAnaly.mapper.VideoCameraMapper;
import com.kingwant.videoAnaly.service.IVideoCameraService;
import com.kingwant.videoAnaly.util.ComUtil;
import com.kingwant.videoAnaly.util.KwHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;

import io.swagger.annotations.ApiOperation;
import xyz.michaelch.mchtools.hepler.RequestHelper;

@RestController
public class VideoCameraController {
	
	@Resource
	private IVideoCameraService vcm;

	@RequestMapping("/videocameralist")
    @ResponseBody
    public BootstrapPageResult<VideoCamera> test(HttpServletRequest request,BootstrapPageEntity<VideoCamera> page){
    	
    	
    /*	Page<VideoCamera> page=new Page<>();
    	page.setSize(length);
    	int current=start==0?1:(start/length+1);
    	page.setCurrent(current);*/
    	
    	
    	//搜索
    	String search=RequestHelper.toStr(request, "search[value]",null);
    	Wrapper<VideoCamera> wrapper=new EntityWrapper<VideoCamera>();
    	if (!KwHelper.isNullOrEmpty(search)) {			
    		wrapper.like("name", search).or().like("code", search);
		}
    	Page<VideoCamera> list=vcm.selectPage(page.getPageObj(),wrapper);
    	Page<VideoCamera> updatelist = vcm.updateAnalyType(list);
        return new BootstrapPageResult<VideoCamera>(updatelist,page.getDraw());
    }
	
	@RequestMapping("/updateorinsertvc")
	public BootstrapPageResult<String> updatevc(HttpServletRequest request){
		String id = request.getParameter("formid");
		String CODE = request.getParameter("CODE");
		String NAME = request.getParameter("NAME");
		String SOURCE = request.getParameter("SOURCE");
		String dress = request.getParameter("dress");
		String ONLINE = request.getParameter("ONLINE");
		String NUM = request.getParameter("NUM");
		String ONLINE_NUM = request.getParameter("ONLINE_NUM");
		String TOTAL = request.getParameter("TOTAL");
		StringBuilder sb = new StringBuilder();
		//分析拼接类型数据ABC
		if(ComUtil.isEmpty(dress)&&ComUtil.isEmpty(ONLINE)&&ComUtil.isEmpty(NUM)){
			sb = null;
		}else{
			if(!ComUtil.isEmpty(dress)){
				sb =sb.append("A,");
			}
			if(!ComUtil.isEmpty(ONLINE)){
				sb =sb.append("B,");
			}
			if(!ComUtil.isEmpty(NUM)){
				sb = sb.append("C,");
			}
			sb = sb.deleteCharAt(sb.length()-1);
		}
		
		if(ComUtil.isEmpty(id)){
			VideoCamera videoCamera = new VideoCamera();
			String uuid = UUID.randomUUID().toString();
			uuid = uuid.replace("-", "");
			videoCamera.setId(uuid);
			videoCamera.setCode(CODE);
			videoCamera.setName(NAME);
			videoCamera.setSource(SOURCE);
			if(Objects.equal(null, sb)){
				videoCamera.setAnltsisType("");
			}else{
				videoCamera.setAnltsisType(sb.toString());
			}
			videoCamera.setOnlineNum(ONLINE_NUM);
			videoCamera.setTotal(TOTAL);
			try{
				boolean flag = vcm.insert(videoCamera);
				return new BootstrapPageResult<String>("新增成功",true);
			}catch (Exception e) {
				return new BootstrapPageResult<String>("新增失败",false);
			}
		}else{
			VideoCamera videoCamera = vcm.selectById(id);
			if(Objects.equal(null, videoCamera)){
				return new BootstrapPageResult<String>("修改的该条数据已经不存在",false);
			}
			videoCamera.setCode(CODE);
			videoCamera.setName(NAME);
			videoCamera.setSource(SOURCE);
			if(Objects.equal(null, sb)){
				videoCamera.setAnltsisType("");
			}else{
				videoCamera.setAnltsisType(sb.toString());
			}
			videoCamera.setOnlineNum(ONLINE_NUM);
			videoCamera.setTotal(TOTAL);
			try{
				vcm.updateById(videoCamera);
				return new BootstrapPageResult<String>("修改成功",true);
			}catch (Exception e) {
				return new BootstrapPageResult<String>("修改失败",false);
			}
			
		}
		
	}
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
			return new BootstrapPageResult<VideoCamera>(videoCamera,"成功", true);
		}catch (Exception e) {
			return new BootstrapPageResult<VideoCamera>("后台查询系统错误", false);
		}
	}
    
    
    
    
    
    
}