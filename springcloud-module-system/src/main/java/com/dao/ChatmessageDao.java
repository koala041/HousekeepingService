package com.dao;

import com.entity.ChatmessageEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ChatmessageVO;
import com.entity.view.ChatmessageView;


/**
 * 消息表
 * 
 * @author GG Bond
 * @email 
 * @date 2026-04-28 09:32:58
 */
public interface ChatmessageDao extends BaseMapper<ChatmessageEntity> {

	/**
	 * 查询所有记录
	 * @return
	 */
	List<ChatmessageVO> selectListVO(@Param("ew") Wrapper<ChatmessageEntity> wrapper);

	/**
	 * 根据实体作为筛选条件查询
	 * @param wrapper
	 * @return
	 */
	ChatmessageVO selectVO(@Param("ew") Wrapper<ChatmessageEntity> wrapper);

	/**
	 * 查询所有记录
	 * @return
	 */
	List<ChatmessageView> selectListView(@Param("ew") Wrapper<ChatmessageEntity> wrapper);

	/**
	 * 根据实体作为筛选条件查询
	 * @param wrapper
	 * @return
	 */
	List<ChatmessageView> selectListView(Pagination page,@Param("ew") Wrapper<ChatmessageEntity> wrapper);

	/**
	 * 获取查询总数
	 * @param wrapper
	 * @return
	 */
    long selectListViewCount(@Param("ew") Wrapper<ChatmessageEntity> wrapper);

	/**
	 * 获取实体
	 * @param wrapper
	 * @return
	 */
	ChatmessageView selectView(@Param("ew") Wrapper<ChatmessageEntity> wrapper);
	

}
