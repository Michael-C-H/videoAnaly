package com.kingwant.videoAnaly.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Objects;
import com.kingwant.videoAnaly.entity.VideoCamera;
import com.kingwant.videoAnaly.mapper.VideoCameraMapper;
import com.kingwant.videoAnaly.service.IResetVideoService;
import com.kingwant.videoAnaly.service.IVideoCameraService;
import com.kingwant.videoAnaly.util.HttpUtil;

import net.sf.json.JSONObject;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author MCH123
 * @since 2018-12-17
 */
@Service
public class ResetVideoServiceImpl implements IResetVideoService{
	
	@Resource
	private IVideoCameraService vcm;
	@Value("${sys.setvc.url}")
    private String URL;
	@Override
	public void resetVideoCamera(String id) {
		VideoCamera videoCamera = vcm.selectById(id);
		JSONObject json = JSONObject.fromObject(videoCamera);
		HttpUtil.doPost(URL, json.toString());
	}
	
	

	

}
