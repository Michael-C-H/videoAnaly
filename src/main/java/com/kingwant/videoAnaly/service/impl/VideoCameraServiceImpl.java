package com.kingwant.videoAnaly.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Objects;
import com.kingwant.videoAnaly.entity.VideoCamera;
import com.kingwant.videoAnaly.mapper.VideoCameraMapper;
import com.kingwant.videoAnaly.service.IVideoCameraService;
import com.kingwant.videoAnaly.util.ComUtil;

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

	@Override
	public Page<VideoCamera> updateAnalyType(Page<VideoCamera> list) {
		List<VideoCamera> records = list.getRecords();
    	for (VideoCamera videoCamera : records) {
			String anltsisType = videoCamera.getAnltsisType();
			if(!ComUtil.isEmpty(anltsisType)){
				StringBuilder stringBuilder = new StringBuilder();
				if(anltsisType.indexOf("A")!=-1){
					stringBuilder.append("着装,");
				}
				if(anltsisType.indexOf("B")!=-1){
					stringBuilder.append("区域范围人数,");
				}
				if(anltsisType.indexOf("C")!=-1){
					stringBuilder.append("总人数,");
				}
				if(!Objects.equal(null,stringBuilder)){
					stringBuilder = stringBuilder.deleteCharAt(stringBuilder.length()-1);
					videoCamera.setAnltsisType(stringBuilder.toString());
				}
			}
			
		}
    	return list;
	}

	@Override
	public VideoCamera selectsource(String source) {
		EntityWrapper<VideoCamera> ew = new EntityWrapper<>();
		ew.where("SOURCE={0}", source);
		return this.selectOne(ew);
	}

	@Override
	public VideoCamera selectuptsource(String source, String id) {
		EntityWrapper<VideoCamera> ew = new EntityWrapper<>();
		ew.where("SOURCE={0}", source).and("ID!={0}", id);
		return this.selectOne(ew);
	}

	

	

	

}
