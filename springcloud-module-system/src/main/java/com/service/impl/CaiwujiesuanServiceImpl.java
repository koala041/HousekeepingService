package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.CaiwujiesuanDao;
import com.entity.CaiwujiesuanEntity;
import com.service.CaiwujiesuanService;
import com.entity.vo.CaiwujiesuanVO;
import com.entity.view.CaiwujiesuanView;

@Service("caiwujiesuanService")
public class CaiwujiesuanServiceImpl extends ServiceImpl<CaiwujiesuanDao, CaiwujiesuanEntity> implements CaiwujiesuanService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CaiwujiesuanEntity> page = this.selectPage(
                new Query<CaiwujiesuanEntity>(params).getPage(),
                new EntityWrapper<CaiwujiesuanEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<CaiwujiesuanEntity> wrapper) {
		  Page<CaiwujiesuanView> page =new Query<CaiwujiesuanView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
            page.setTotal(baseMapper.selectListViewCount(wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<CaiwujiesuanVO> selectListVO(Wrapper<CaiwujiesuanEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public CaiwujiesuanVO selectVO(Wrapper<CaiwujiesuanEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<CaiwujiesuanView> selectListView(Wrapper<CaiwujiesuanEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public CaiwujiesuanView selectView(Wrapper<CaiwujiesuanEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
