package com.kingwant.videoAnaly.controller;
import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kingwant.videoAnaly.entity.VideoCamera;
import com.kingwant.videoAnaly.mapper.VideoCameraMapper;

@RestController
public class IndexController {
	
	@Resource
	VideoCameraMapper vcm;

    @RequestMapping("/")
    public String hello(){
        return "Welcome to start SpringBoot!";
    }
    
    
    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "无权限!";
    }
    
    
    @RequestMapping("/login")
    public String login(){
        return "请登录!";
    }
    
    
    @RequestMapping("/test")
    public VideoCamera test(){
        return vcm.selectById("123");
    }
    
    
    
}