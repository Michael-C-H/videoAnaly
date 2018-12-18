package com.kingwant.videoAnaly.controller;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kingwant.videoAnaly.annonation.ValidationParam;
import com.kingwant.videoAnaly.base.PublicResult;
import com.kingwant.videoAnaly.base.PublicResultConstant;
import com.kingwant.videoAnaly.entity.BootstrapPageEntity;
import com.kingwant.videoAnaly.entity.BootstrapPageResult;
import com.kingwant.videoAnaly.entity.VideoCamera;
import com.kingwant.videoAnaly.service.IVideoCameraService;
import com.kingwant.videoAnaly.util.ComUtil;
import com.kingwant.videoAnaly.util.KwHelper;

import io.swagger.annotations.ApiOperation;
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
    @PostMapping("/login")
    @ApiOperation(value="用户登录",notes="管理员")
    public PublicResult<String> login(
            @ValidationParam("userName,passWord")@RequestBody JSONObject requestJson) throws Exception{
        String userName = requestJson.getString("userName");
        String passWord = requestJson.getString("passWord");
        if (ComUtil.isEmpty(userName) || ComUtil.isEmpty(passWord)) {
            return new PublicResult<>(PublicResultConstant.PARAM_ERROR, null);
        }
        /*调用shiro判断当前用户是否是系统用户*/
        //得到当前用户
        Subject subject = SecurityUtils.getSubject();
        //判断是否登录，如果未登录，则登录
        if (!subject.isAuthenticated()) {
            //创建用户名/密码验证Token, shiro是将用户录入的登录名和密码（未加密）封装到uPasswordToken对象中
            UsernamePasswordToken uPasswordToken = new UsernamePasswordToken(userName,passWord);
            //自动调用AuthRealm.doGetAuthenticationInfo
            try {
                //执行登录，如果登录未成功，则捕获相应的异常
                subject.login(uPasswordToken);
                return new PublicResult<>(PublicResultConstant.SUCCESS, "登录成功");
            }catch (Exception e) {
                // 捕获异常
            	return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
            }
        }
        return new PublicResult<>(PublicResultConstant.SUCCESS, "登录成功");

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