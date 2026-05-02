
package com.service;

import java.util.Map;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.ConfigEntity;
import com.utils.PageUtils;


/**
 * 系统用户
 * @author koala
 */
public interface ConfigService extends IService<ConfigEntity> {
	/**
	 * 分页查询
	 * @param params
	 * @param wrapper
	 * @return
	 */
	PageUtils queryPage(Map<String, Object> params,Wrapper<ConfigEntity> wrapper);
}
