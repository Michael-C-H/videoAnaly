package com.kingwant.videoAnaly.service;

import com.kingwant.videoAnaly.entity.VideoDressAbnormal;
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
public interface IVideoDressAbnormalService extends IService<VideoDressAbnormal> {

	Wrapper<VideoDressAbnormal> selectTime(Wrapper<VideoDressAbnormal> wrapper, String code, String begintime,
			String endtime);


}
