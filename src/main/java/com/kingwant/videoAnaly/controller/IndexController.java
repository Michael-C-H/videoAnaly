package com.kingwant.videoAnaly.controller;



import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
import com.kingwant.videoAnaly.entity.VideoDressAbnormal;
import com.kingwant.videoAnaly.entity.VideoNumberAbnormal;
import com.kingwant.videoAnaly.service.IVideoCameraService;
import com.kingwant.videoAnaly.service.IVideoDressAbnormalService;
import com.kingwant.videoAnaly.service.IVideoNumberAbnormalService;
import com.kingwant.videoAnaly.util.ComUtil;
import com.kingwant.videoAnaly.util.KwHelper;

import io.swagger.annotations.ApiOperation;
import xyz.michaelch.mchtools.hepler.DateHelper;
import xyz.michaelch.mchtools.hepler.RequestHelper;

@Controller
public class IndexController {
	
	@Resource
	private IVideoCameraService vcm;
	@Resource
	private IVideoDressAbnormalService vas;
	@Resource
	private IVideoNumberAbnormalService vns;
	
	@RequestMapping("/dress/detail")
    public String dressnewpage(String id,Model model){
    	
    	VideoDressAbnormal videoDressAbnormal = vas.selectById(id);
		Date exDate = videoDressAbnormal.getExDate();
		String formatStr =DateHelper.dateToString(exDate,"yyyy-MM-dd hh:mm:ss");
		videoDressAbnormal.setStringDate(formatStr);
		if(videoDressAbnormal.getImage()!=null){
			videoDressAbnormal.setImgData(Base64.encodeBase64String(videoDressAbnormal.getImage()));
			videoDressAbnormal.setImage(null);
		}
    	model.addAttribute("videoDressAbnormal",videoDressAbnormal);
    	
        return "dressEx/detail";
    }
	@RequestMapping("/numberEx/detail")
	public String numnewpage(String id,Model model){
		
		VideoNumberAbnormal videoNumberAbnormal = vns.selectById(id);
		Date exDate = videoNumberAbnormal.getExDate();
		String formatStr =DateHelper.dateToString(exDate,"yyyy-MM-dd hh:mm:ss");
		videoNumberAbnormal.setStringDate(formatStr);
		if(videoNumberAbnormal.getImage()!=null){
			videoNumberAbnormal.setImgData(Base64.encodeBase64String(videoNumberAbnormal.getImage()));
			videoNumberAbnormal.setImage(null);
		}
		model.addAttribute("videoNumberAbnormal",videoNumberAbnormal);
		return "numberEx/detail";
	}

    
    @RequestMapping("/")
    public String root(){
    	return "camera/list";
    }
    @RequestMapping("/camera/list")
    public String camera(){
    	return "camera/list";
    }
    @RequestMapping("/dressEx/list")
    public String dress(){
    	return "dressEx/list";
    } 
    @RequestMapping("/numberEx/list")
    public String num(){
    	return "numberEx/list";
    }
    
    
    @RequestMapping("/unauthorized")
    @ResponseBody
    public String unauthorized(){
        return "无权限!";
    }
    
    

    @RequestMapping("/login")
    public String login(Model model){
    	model.addAttribute("message", "");
        return "login";
    }
    @RequestMapping("/loginDo")
    public String login(HttpServletRequest request, Model model, String username, String password) throws Exception{
        
    	String error = ""; 
        /*调用shiro判断当前用户是否是系统用户*/
        //得到当前用户
        Subject subject = SecurityUtils.getSubject();
        //判断是否登录，如果未登录，则登录
        
            //创建用户名/密码验证Token, shiro是将用户录入的登录名和密码（未加密）封装到uPasswordToken对象中
            UsernamePasswordToken uPasswordToken = new UsernamePasswordToken(username,password);
            //自动调用AuthRealm.doGetAuthenticationInfo
            try {  
            	subject.login(uPasswordToken);
            } catch (UnknownAccountException ex) {// 用户名没有找到  
                error = "您输入的用户名不存在";  
            } catch (IncorrectCredentialsException ex) {// 用户名密码不匹配  
                error = "用户名密码不匹配 ";  
            }  
            catch(ExcessiveAttemptsException e){  
                error="密码错误次数已超五次，账号锁定1小时";  
            }  
            catch (AuthenticationException ex) {// 其他的登录错误  
                ex.printStackTrace();  
                error = ex.getMessage();  
            }  
            // 验证是否成功登录的方法  
            if (subject.isAuthenticated()) {  
            	 return  "redirect:/";  
            } else {  
                model.addAttribute("message", error);
                model.addAttribute("loginUser", username);
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                subject.logout();  
                return "login";  
            }  
        }
    @RequestMapping("/loginOut")
    public String loginout() throws Exception{
    	Subject subject = SecurityUtils.getSubject();
    	subject.logout();
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