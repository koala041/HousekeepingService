package com.dao;

import com.entity.CaiwujiesuanEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.CaiwujiesuanVO;
import com.entity.view.CaiwujiesuanView;


/**
 * 财务结算
 * 
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
public interface CaiwujiesuanDao extends BaseMapper<CaiwujiesuanEntity> {
	
	List<CaiwujiesuanVO> selectListVO(@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);
	
	CaiwujiesuanVO selectVO(@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);
	
	List<CaiwujiesuanView> selectListView(@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);

	List<CaiwujiesuanView> selectListView(Pagination page,@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);

    long selectListViewCount(@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);

	
	CaiwujiesuanView selectView(@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);
	

}
