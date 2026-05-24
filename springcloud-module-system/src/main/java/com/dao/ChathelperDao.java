package com.dao;

import com.entity.ChathelperEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ChathelperVO;
import com.entity.view.ChathelperView;


/**
 * 聊天助手表
 * 
 * @author GG Bond
 * @email 
 * @date 2026-04-28 09:32:58
 */
public interface ChathelperDao extends BaseMapper<ChathelperEntity> {

	/**
	 * 查询所有记录
	 * @param wrapper
	 * @return
	 */
	List<ChathelperVO> selectListVO(@Param("ew") Wrapper<ChathelperEntity> wrapper);

	/**
	 * 根据实体作为筛选条件查询
	 * @param wrapper
	 * @return
	 */
	ChathelperVO selectVO(@Param("ew") Wrapper<ChathelperEntity> wrapper);

	/**
	 * 查询所有记录
	 * @param wrapper
	 * @return
	 */
	List<ChathelperView> selectListView(@Param("ew") Wrapper<ChathelperEntity> wrapper);

	/**
	 * 根据实体作为筛选条件查询
	 * @param page
	 * @return
	 */
	List<ChathelperView> selectListView(Pagination page,@Param("ew") Wrapper<ChathelperEntity> wrapper);

	/**
	 * 查询所有记录
	 * @param wrapper
	 * @return
	 */
    long selectListViewCount(@Param("ew") Wrapper<ChathelperEntity> wrapper);

	/**
	 * 获取实体
	 * @param wrapper
	 * @return
	 */
	ChathelperView selectView(@Param("ew") Wrapper<ChathelperEntity> wrapper);
	

}
