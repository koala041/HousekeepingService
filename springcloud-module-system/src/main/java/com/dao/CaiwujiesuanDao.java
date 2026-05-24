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
 * @author GG Bond
 * @email 
 * @date 2026-04-28 09:32:58
 */
public interface CaiwujiesuanDao extends BaseMapper<CaiwujiesuanEntity> {
	/**
	 * 查询所有记录
	 * @return
	 */
	List<CaiwujiesuanVO> selectListVO(@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);

	/**
	 * 根据ID查询记录
	 * @param id
	 * @return
	 */
	CaiwujiesuanVO selectVO(@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);

	/**
	 * 查询所有记录
	 * @return
	 */
	List<CaiwujiesuanView> selectListView(@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);

	/**
	 * 根据ID查询记录
	 * @param page
	 * @return
	 */
	List<CaiwujiesuanView> selectListView(Pagination page,@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);

	/**
	 * 查询所有记录
	 * @return
	 */
    long selectListViewCount(@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);

	/**
	 * 根据ID查询记录
	 * @param id
	 * @return
	 */
	CaiwujiesuanView selectView(@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);
	

}
