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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.base.Objects;
import com.kingwant.videoAnaly.annonation.ValidationParam;
import com.kingwant.videoAnaly.base.PublicResult;
import com.kingwant.videoAnaly.base.PublicResultConstant;
import com.kingwant.videoAnaly.entity.User;
import com.kingwant.videoAnaly.entity.VideoCamera;
import com.kingwant.videoAnaly.mapper.VideoCameraMapper;
import com.kingwant.videoAnaly.service.IVideoCameraService;
import com.kingwant.videoAnaly.util.ComUtil;

import io.swagger.annotations.ApiOperation;

@RestController
public class VideoCameraController {
	
	@Resource
	IVideoCameraService videoCameraService;

    @RequestMapping("/vedioCameraList")
    public ModelAndView getVideoCameraList(){
    	ModelAndView mav = new ModelAndView();
    	try{
    		List<VideoCamera> vedioCameraList = videoCameraService.getVedioCameraList();
    		mav.addObject("vedioCameraList",vedioCameraList);
    		mav.setViewName("");
    		return mav;
    	}catch (Exception e) {
    		return null;
		}
    }
    @RequestMapping("/updateorinsertvideocamera")
    public PublicResult<String> updateVideoCamera(VideoCamera videoCamera){
    	try{
    		if(Objects.equal(null, videoCamera)){
    			return new PublicResult<>("传入对象为空", null) ;
    		}
    		String id = videoCamera.getId();
    		if(ComUtil.isEmpty(id)){
    			//如果为空,手动设32位UUID,替换掉——
    			UUID uuid=UUID.randomUUID();
    		    String str = uuid.toString();
    		    String replaceid = str.replace("-", ""); 
    			videoCamera.setId(replaceid);
    		}
    		boolean updateById = videoCameraService.insertOrUpdate(videoCamera);
    		return new PublicResult<String>(PublicResultConstant.SUCCESS, null) ;
    	}catch (Exception e) {
    		return new PublicResult<>(PublicResultConstant.FAILED, null) ;
		}
    }
    @RequestMapping("/deletevideocamera")
    public PublicResult<String> deletevideocamera(String id){
    	try{
    		if(ComUtil.isEmpty(id)){
    			return new PublicResult<>("传入参数为空", null) ;
    		}
    		
    		videoCameraService.deleteById(id);
    		return new PublicResult<String>(PublicResultConstant.SUCCESS, null) ;
    	}catch (Exception e) {
    		return new PublicResult<>(PublicResultConstant.FAILED, null) ;
    	}
    }
   
    
    
    
    
    
    
}