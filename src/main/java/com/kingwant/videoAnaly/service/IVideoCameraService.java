package com.kingwant.videoAnaly.service;

import com.kingwant.videoAnaly.entity.VideoCamera;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MCH123
 * @since 2018-12-17
 */
public interface IVideoCameraService extends IService<VideoCamera> {
	//获取相机信息列表
	List<VideoCamera> getVedioCameraList();

	Page<VideoCamera> updateAnalyType(Page<VideoCamera> list);

	VideoCamera selectsource(String sOURCE);

	VideoCamera selectuptsource(String sOURCE, String id);
	
	
	
	
}
