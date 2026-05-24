package com.dao;

import com.entity.ChatEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ChatVO;
import com.entity.view.ChatView;


/**
 * 在线客服
 * 
 * @author GG Bond
 * @email 
 * @date 2026-04-28 09:32:58
 */
public interface ChatDao extends BaseMapper<ChatEntity> {

	/**
	 * 查询所有记录
	 * @param wrapper
	 * @return
	 */
	List<ChatVO> selectListVO(@Param("ew") Wrapper<ChatEntity> wrapper);

	/**
	 * 根据实体查询
	 * @param wrapper
	 * @return
	 */
	ChatVO selectVO(@Param("ew") Wrapper<ChatEntity> wrapper);

	/**
	 * 查询所有记录
	 * @param wrapper
	 * @return
	 */
	List<ChatView> selectListView(@Param("ew") Wrapper<ChatEntity> wrapper);

	/**
	 * 根据实体查询
	 * @param wrapper
	 * @return
	 */
	List<ChatView> selectListView(Pagination page,@Param("ew") Wrapper<ChatEntity> wrapper);

	/**
	 * 查询所有记录
	 * @param wrapper
	 * @return
	 */
    long selectListViewCount(@Param("ew") Wrapper<ChatEntity> wrapper);

	/**
	 * 获取实体
	 * @param wrapper
	 * @return
	 */
	ChatView selectView(@Param("ew") Wrapper<ChatEntity> wrapper);
	

}
