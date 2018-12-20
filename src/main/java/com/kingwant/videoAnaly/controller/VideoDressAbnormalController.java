package com.kingwant.videoAnaly.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.ui.Model;
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
import com.kingwant.videoAnaly.entity.VideoDressAbnormal;
import com.kingwant.videoAnaly.mapper.VideoCameraMapper;
import com.kingwant.videoAnaly.service.IVideoCameraService;
import com.kingwant.videoAnaly.service.IVideoDressAbnormalService;
import com.kingwant.videoAnaly.util.ComUtil;
import com.kingwant.videoAnaly.util.KwHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;

import io.swagger.annotations.ApiOperation;
import xyz.michaelch.mchtools.hepler.RequestHelper;

@RestController
public class VideoDressAbnormalController {
	
	@Resource
	private IVideoDressAbnormalService vds;
	
	
	@RequestMapping("/dresslist")
    @ResponseBody
    public BootstrapPageResult<VideoDressAbnormal> test(HttpServletRequest request,BootstrapPageEntity<VideoDressAbnormal> page){
    	
    	
    /*	Page<VideoCamera> page=new Page<>();
    	page.setSize(length);
    	int current=start==0?1:(start/length+1);
    	page.setCurrent(current);*/
    	
    	
    	//搜索
    	String search=RequestHelper.toStr(request, "search[value]",null);
    	Wrapper<VideoDressAbnormal> wrapper=new EntityWrapper<VideoDressAbnormal>();
    	wrapper.setSqlSelect(" ID AS id, CODE AS code, NAME AS name, EX_DATE AS exDate, REASON AS reason");
    	wrapper.orderBy("EX_DATE", false);
    	if (!KwHelper.isNullOrEmpty(search)) {	
    		wrapper.like("name", search).or().like("code", search);
		}
    	
    	Page<VideoDressAbnormal> list=vds.selectPage(page.getPageObj(),wrapper);
    	List<VideoDressAbnormal> records = list.getRecords();
    	for (VideoDressAbnormal videoDressAbnormal : records) {
			Date exDate = videoDressAbnormal.getExDate();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatStr =formatter.format(exDate);
			videoDressAbnormal.setStringDate(formatStr);
		}
    	
        return new BootstrapPageResult<VideoDressAbnormal>(list,page.getDraw());
    }
	
	@RequestMapping("/selectonedress")
	@ResponseBody
	public BootstrapPageResult<VideoDressAbnormal> selectone(String id){
		try{
			VideoDressAbnormal videoDressAbnormal = vds.selectById(id);
			Date exDate = videoDressAbnormal.getExDate();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatStr =formatter.format(exDate);
			videoDressAbnormal.setStringDate(formatStr);
			return new BootstrapPageResult<VideoDressAbnormal>(videoDressAbnormal,"成功", true);
		}catch (Exception e) {
			return new BootstrapPageResult<VideoDressAbnormal>("后台查询系统错误", false);
		}
	}
	
	
	
	
	
    
    
    
    
}