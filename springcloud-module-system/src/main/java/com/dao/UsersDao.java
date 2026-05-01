package com.dao;

import com.entity.UsersEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.UsersVO;
import com.entity.view.UsersView;


/**
 * 管理员
 * 
 * @author 
 * @email 
 * @date 2026-04-28 09:32:59
 */
public interface UsersDao extends BaseMapper<UsersEntity> {
	
	List<UsersVO> selectListVO(@Param("ew") Wrapper<UsersEntity> wrapper);
	
	UsersVO selectVO(@Param("ew") Wrapper<UsersEntity> wrapper);
	
	List<UsersView> selectListView(@Param("ew") Wrapper<UsersEntity> wrapper);

	List<UsersView> selectListView(Pagination page,@Param("ew") Wrapper<UsersEntity> wrapper);

    long selectListViewCount(@Param("ew") Wrapper<UsersEntity> wrapper);

	
	UsersView selectView(@Param("ew") Wrapper<UsersEntity> wrapper);
	

}
