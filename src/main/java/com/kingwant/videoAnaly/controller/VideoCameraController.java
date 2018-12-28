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
    	
    	
    /*	Page<VideoCamera> page=new Page<>();
    	page.setSize(length);
    	int current=start==0?1:(start/length+1);
    	page.setCurrent(current);*/
    	
    	
    	//搜索
    	String name=RequestHelper.toStr(request, "name",null);
    	String code=RequestHelper.toStr(request, "code",null);
    	Wrapper<VideoCamera> wrapper=new EntityWrapper<VideoCamera>();
    	if (!KwHelper.isNullOrEmpty(name)&&!KwHelper.isNullOrEmpty(code)) {			
    		wrapper.like("name", name).and().like("code", code);
		}
    	if (!KwHelper.isNullOrEmpty(name)&&KwHelper.isNullOrEmpty(code)) {			
    		wrapper.like("name", name);
    	}
    	if (KwHelper.isNullOrEmpty(name)&&!KwHelper.isNullOrEmpty(code)) {			
    		wrapper.like("code", code);
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
		
		//分析拼接类型数据ABC
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
			VideoCamera selectsource = vcm.selectuptsource(SOURCE,id);
			if(!ComUtil.isEmpty(selectsource)){
				return new BootstrapPageResult<String>("数据源不能重复",false);
			}
			VideoCamera videoCamera = vcm.selectById(id);
			if(Objects.equal(null, videoCamera)){
				return new BootstrapPageResult<String>("修改的该条数据已经不存在",false);
			}
			videoCamera = setvalue(CODE, NAME, SOURCE, ONLINE_NUM, TOTAL, sb, videoCamera, id,anltsisType,false);
			try{
				vcm.updateById(videoCamera);
				return new BootstrapPageResult<String>("修改成功",true);
			}catch (Exception e) {
				return new BootstrapPageResult<String>("修改失败"+e.getMessage(),false);
			}
			
		}
		
	}
	//修改或者新增的时候
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
		videoCamera.setOnlineNum("");
		videoCamera.setTotal("");
		if(ComUtil.isEmpty(anltsisType)){
			
			return videoCamera;
		}else{
			
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