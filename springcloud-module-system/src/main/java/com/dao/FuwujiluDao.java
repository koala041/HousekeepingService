package com.dao;

import com.entity.FuwujiluEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.FuwujiluVO;
import com.entity.view.FuwujiluView;


/**
 * 服务记录
 * 
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
public interface FuwujiluDao extends BaseMapper<FuwujiluEntity> {
	
	List<FuwujiluVO> selectListVO(@Param("ew") Wrapper<FuwujiluEntity> wrapper);
	
	FuwujiluVO selectVO(@Param("ew") Wrapper<FuwujiluEntity> wrapper);
	
	List<FuwujiluView> selectListView(@Param("ew") Wrapper<FuwujiluEntity> wrapper);

	List<FuwujiluView> selectListView(Pagination page,@Param("ew") Wrapper<FuwujiluEntity> wrapper);

    long selectListViewCount(@Param("ew") Wrapper<FuwujiluEntity> wrapper);

	
	FuwujiluView selectView(@Param("ew") Wrapper<FuwujiluEntity> wrapper);
	

}
