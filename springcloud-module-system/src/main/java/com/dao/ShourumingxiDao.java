package com.dao;

import com.entity.ShourumingxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ShourumingxiVO;
import com.entity.view.ShourumingxiView;


/**
 * 收入明细
 * 
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
public interface ShourumingxiDao extends BaseMapper<ShourumingxiEntity> {
	
	List<ShourumingxiVO> selectListVO(@Param("ew") Wrapper<ShourumingxiEntity> wrapper);
	
	ShourumingxiVO selectVO(@Param("ew") Wrapper<ShourumingxiEntity> wrapper);
	
	List<ShourumingxiView> selectListView(@Param("ew") Wrapper<ShourumingxiEntity> wrapper);

	List<ShourumingxiView> selectListView(Pagination page,@Param("ew") Wrapper<ShourumingxiEntity> wrapper);

    long selectListViewCount(@Param("ew") Wrapper<ShourumingxiEntity> wrapper);

	
	ShourumingxiView selectView(@Param("ew") Wrapper<ShourumingxiEntity> wrapper);
	

    List<Map<String, Object>> selectValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<ShourumingxiEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<ShourumingxiEntity> wrapper);

    List<Map<String, Object>> selectGroup(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<ShourumingxiEntity> wrapper);



}
