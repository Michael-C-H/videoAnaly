package com.kingwant.videoAnaly.controller;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kingwant.videoAnaly.entity.BootstrapPageEntity;
import com.kingwant.videoAnaly.entity.BootstrapPageResult;
import com.kingwant.videoAnaly.entity.VideoCamera;
import com.kingwant.videoAnaly.service.IVideoCameraService;
import com.kingwant.videoAnaly.util.KwHelper;

import xyz.michaelch.mchtools.hepler.RequestHelper;

@Controller
public class IndexController {
	
	@Resource
	private IVideoCameraService vcm;

    @RequestMapping("/")
    public String hello(){
        return "camera/list";
    }
    
    
    @RequestMapping("/unauthorized")
    @ResponseBody
    public String unauthorized(){
        return "无权限!";
    }
    
    
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    
    
    @RequestMapping("/test")
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
    	
    	
        return new BootstrapPageResult<VideoCamera>(list,page.getDraw());
    }
    
    
    
}