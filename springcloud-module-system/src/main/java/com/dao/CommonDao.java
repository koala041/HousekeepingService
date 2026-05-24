
package com.dao;

import java.util.List;
import java.util.Map;

/**
 * 通用接口
 * @author GG Bond
 */
public interface CommonDao{

	/**
	 * 获取下拉框数据
	 * @param params
	 * @return
	 */
	List<String> getOption(Map<String, Object> params);

	Map<String, Object> getFollowByOption(Map<String, Object> params);
	
	List<String> getFollowByOption2(Map<String, Object> params);
	
	void sh(Map<String, Object> params);
	
	int remindCount(Map<String, Object> params);
	
	Map<String, Object> selectCal(Map<String, Object> params);
	
	List<Map<String, Object>> selectGroup(Map<String, Object> params);
	
	List<Map<String, Object>> selectValue(Map<String, Object> params);

	List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params);

	int updateColumn(Map<String, Object> params);

	int deleteColumn(Map<String, Object> params);
}
