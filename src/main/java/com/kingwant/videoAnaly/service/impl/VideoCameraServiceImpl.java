package com.kingwant.videoAnaly.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kingwant.videoAnaly.entity.VideoCamera;
import com.kingwant.videoAnaly.mapper.VideoCameraMapper;
import com.kingwant.videoAnaly.service.IVideoCameraService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author MCH123
 * @since 2018-12-17
 */
@Service
public class VideoCameraServiceImpl extends ServiceImpl<VideoCameraMapper, VideoCamera> implements IVideoCameraService {

	@Override
	public List<VideoCamera> getVedioCameraList() {
		EntityWrapper<VideoCamera> ew = new EntityWrapper<>();
		return this.selectList(ew);
	}

	

	

}
