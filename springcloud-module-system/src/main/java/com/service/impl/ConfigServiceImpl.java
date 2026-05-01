
package com.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ConfigDao;
import com.entity.ConfigEntity;
import com.service.ConfigService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 */
@Service("configService")
public class ConfigServiceImpl extends ServiceImpl<ConfigDao, ConfigEntity> implements ConfigService {
	// 静态缓存对象，用于存储所有配置
	private static final Map<String, ConfigEntity> CONFIG_CACHE = new HashMap<>();

	/**
	 * 初始化缓存 - 应用启动时加载所有配置
	 */
	@Override
	public boolean insertOrUpdate(ConfigEntity entity) {
		boolean res = super.insertOrUpdate(entity);
		if (res) {
			// 更新缓存
			CONFIG_CACHE.put(entity.getName(), entity);
		}
		return res;
	}

	/**
	 * 应用启动时初始化所有配置到缓存
	 */
	@PostConstruct
	public void initConfigCache() {
		CONFIG_CACHE.isEmpty();
		List<ConfigEntity> configList = this.selectList(new EntityWrapper<ConfigEntity>());
		for (ConfigEntity config : configList) {
			CONFIG_CACHE.put(config.getName(), config);
		}
	}

	/**
	 * 获取所有配置缓存
	 *
	 * @return 配置缓存Map
	 */
	public static Map<String, ConfigEntity> getConfigCache() {
		return CONFIG_CACHE;
	}

	/**
	 * 根据name获取配置（优先从缓存获取）
	 *
	 * @param name 配置名称
	 * @return 配置实体
	 */
	public ConfigEntity getConfigByName(String name) {
		// 先从缓存获取
		ConfigEntity config = CONFIG_CACHE.get(name);
		if (config == null) {
			// 缓存未命中则查询数据库
			config = this.selectOne(new EntityWrapper<ConfigEntity>().eq("name", name));
			if (config != null) {
				CONFIG_CACHE.put(name, config); // 放入缓存
			}
		}
		return config;
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ConfigEntity> wrapper) {
		Page<ConfigEntity> page = this.selectPage(
				new Query<ConfigEntity>(params).getPage(),
				wrapper
		);
		return new PageUtils(page);
	}

	/**
	 * 重写更新方法，同步更新缓存
	 */
	@Override
	@Transactional
	public boolean updateById(ConfigEntity entity) {
		boolean result = super.updateById(entity);
		if (result) {
			CONFIG_CACHE.put(entity.getName(), entity);
		}
		return result;
	}

	/**
	 * 重写插入方法，同步更新缓存
	 */
	@Override
	@Transactional
	public boolean insert(ConfigEntity entity) {
		boolean result = super.insert(entity);
		if (result) {
			CONFIG_CACHE.put(entity.getName(), entity);
		}
		return result;
	}

	/**
	 * 重写删除方法，同步更新缓存
	 */
	@Override
	@Transactional
	public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
		// 先查询要删除的配置
		List<ConfigEntity> configs = this.selectBatchIds(idList);
		boolean result = super.deleteBatchIds(idList);
		if (result) {
			// 从缓存中移除
			for (ConfigEntity config : configs) {
				CONFIG_CACHE.remove(config.getName());
			}
		}
		return result;
	}
}
