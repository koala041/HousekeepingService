package com.dao;

import com.entity.FuwuquerenEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.FuwuquerenVO;
import com.entity.view.FuwuquerenView;


/**
 * 服务确认
 * 
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
public interface FuwuquerenDao extends BaseMapper<FuwuquerenEntity> {
	
	List<FuwuquerenVO> selectListVO(@Param("ew") Wrapper<FuwuquerenEntity> wrapper);
	
	FuwuquerenVO selectVO(@Param("ew") Wrapper<FuwuquerenEntity> wrapper);
	
	List<FuwuquerenView> selectListView(@Param("ew") Wrapper<FuwuquerenEntity> wrapper);

	List<FuwuquerenView> selectListView(Pagination page,@Param("ew") Wrapper<FuwuquerenEntity> wrapper);

    long selectListViewCount(@Param("ew") Wrapper<FuwuquerenEntity> wrapper);

	
	FuwuquerenView selectView(@Param("ew") Wrapper<FuwuquerenEntity> wrapper);
	

    List<Map<String, Object>> selectValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<FuwuquerenEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<FuwuquerenEntity> wrapper);

    List<Map<String, Object>> selectGroup(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<FuwuquerenEntity> wrapper);



}
