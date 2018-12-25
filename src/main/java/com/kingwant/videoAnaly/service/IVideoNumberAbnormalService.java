package com.kingwant.videoAnaly.service;

import com.kingwant.videoAnaly.entity.VideoNumberAbnormal;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MCH123
 * @since 2018-12-17
 */
public interface IVideoNumberAbnormalService extends IService<VideoNumberAbnormal> {

	Wrapper<VideoNumberAbnormal> selectTime(Wrapper<VideoNumberAbnormal> wrapper, String code, String begintime,
			String endtime);

}
