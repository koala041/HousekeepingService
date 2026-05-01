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


import com.dao.FuwuquerenDao;
import com.entity.FuwuquerenEntity;
import com.service.FuwuquerenService;
import com.entity.vo.FuwuquerenVO;
import com.entity.view.FuwuquerenView;

@Service("fuwuquerenService")
public class FuwuquerenServiceImpl extends ServiceImpl<FuwuquerenDao, FuwuquerenEntity> implements FuwuquerenService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<FuwuquerenEntity> page = this.selectPage(
                new Query<FuwuquerenEntity>(params).getPage(),
                new EntityWrapper<FuwuquerenEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<FuwuquerenEntity> wrapper) {
		  Page<FuwuquerenView> page =new Query<FuwuquerenView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
            page.setTotal(baseMapper.selectListViewCount(wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<FuwuquerenVO> selectListVO(Wrapper<FuwuquerenEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public FuwuquerenVO selectVO(Wrapper<FuwuquerenEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<FuwuquerenView> selectListView(Wrapper<FuwuquerenEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public FuwuquerenView selectView(Wrapper<FuwuquerenEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

    @Override
    public List<Map<String, Object>> selectValue(Map<String, Object> params, Wrapper<FuwuquerenEntity> wrapper) {
        return baseMapper.selectValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params, Wrapper<FuwuquerenEntity> wrapper) {
        return baseMapper.selectTimeStatValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectGroup(Map<String, Object> params, Wrapper<FuwuquerenEntity> wrapper) {
        return baseMapper.selectGroup(params, wrapper);
    }




}
