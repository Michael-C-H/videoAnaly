package com.kingwant.videoAnaly.service.impl;

import com.kingwant.videoAnaly.entity.VideoNumberAbnormal;
import com.kingwant.videoAnaly.mapper.VideoNumberAbnormalMapper;
import com.kingwant.videoAnaly.service.IVideoNumberAbnormalService;
import com.kingwant.videoAnaly.util.ComUtil;
import com.kingwant.videoAnaly.util.KwHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author MCH123
 * @since 2018-12-17
 */
@Service
public class VideoNumberAbnormalServiceImpl extends ServiceImpl<VideoNumberAbnormalMapper, VideoNumberAbnormal> implements IVideoNumberAbnormalService {

	@Override
	public Wrapper<VideoNumberAbnormal> selectTime(Wrapper<VideoNumberAbnormal> wrapper, String code, String begintime,
			String endtime) {
		if(ComUtil.isEmpty(code)){
    		if (!KwHelper.isNullOrEmpty(begintime)&&!KwHelper.isNullOrEmpty(endtime)){	
        		wrapper.where("EX_DATE<=to_date({0}, ' yyyy-MM-dd hh24:mi:ss')", endtime).and("EX_DATE>=to_date({0}, ' yyyy-MM-dd hh24:mi:ss')", begintime);
        	}
        	if (KwHelper.isNullOrEmpty(begintime)&&!KwHelper.isNullOrEmpty(endtime)){	
        		wrapper.where("EX_DATE<=to_date({0}, ' yyyy-MM-dd hh24:mi:ss')", endtime);
        	}
        	if (!KwHelper.isNullOrEmpty(begintime)&&KwHelper.isNullOrEmpty(endtime)){	
        		wrapper.where("EX_DATE>=to_date({0}, ' yyyy-MM-dd hh24:mi:ss')", endtime);
        	}
    	}else if(!ComUtil.isEmpty(code)){
    		if (!KwHelper.isNullOrEmpty(begintime)&&!KwHelper.isNullOrEmpty(endtime)){	
        		wrapper.like("code", code).where("EX_DATE<=to_date({0}, ' yyyy-MM-dd hh24:mi:ss')", endtime).and("EX_DATE>=to_date({0}, ' yyyy-MM-dd hh24:mi:ss')", begintime);
        	}
        	if (KwHelper.isNullOrEmpty(begintime)&&!KwHelper.isNullOrEmpty(endtime)){	
        		wrapper.where("EX_DATE<=to_date({0}, ' yyyy-MM-dd hh24:mi:ss')", endtime).like("code", code);
        	}
        	if (!KwHelper.isNullOrEmpty(begintime)&&KwHelper.isNullOrEmpty(endtime)){	
        		wrapper.where("EX_DATE>=to_date({0}, ' yyyy-MM-dd hh24:mi:ss')", begintime).like("code", code);
        	}
        	if (KwHelper.isNullOrEmpty(begintime)&&KwHelper.isNullOrEmpty(endtime)){	
        		wrapper.like("code", code);
        	}
    	}
		return wrapper;
	}

}
