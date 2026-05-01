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


import com.dao.ShourumingxiDao;
import com.entity.ShourumingxiEntity;
import com.service.ShourumingxiService;
import com.entity.vo.ShourumingxiVO;
import com.entity.view.ShourumingxiView;

@Service("shourumingxiService")
public class ShourumingxiServiceImpl extends ServiceImpl<ShourumingxiDao, ShourumingxiEntity> implements ShourumingxiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ShourumingxiEntity> page = this.selectPage(
                new Query<ShourumingxiEntity>(params).getPage(),
                new EntityWrapper<ShourumingxiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ShourumingxiEntity> wrapper) {
		  Page<ShourumingxiView> page =new Query<ShourumingxiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
            page.setTotal(baseMapper.selectListViewCount(wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ShourumingxiVO> selectListVO(Wrapper<ShourumingxiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ShourumingxiVO selectVO(Wrapper<ShourumingxiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ShourumingxiView> selectListView(Wrapper<ShourumingxiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ShourumingxiView selectView(Wrapper<ShourumingxiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

    @Override
    public List<Map<String, Object>> selectValue(Map<String, Object> params, Wrapper<ShourumingxiEntity> wrapper) {
        return baseMapper.selectValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params, Wrapper<ShourumingxiEntity> wrapper) {
        return baseMapper.selectTimeStatValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectGroup(Map<String, Object> params, Wrapper<ShourumingxiEntity> wrapper) {
        return baseMapper.selectGroup(params, wrapper);
    }




}
