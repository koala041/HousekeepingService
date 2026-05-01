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


import com.dao.UsersDao;
import com.entity.UsersEntity;
import com.service.UsersService;
import com.entity.vo.UsersVO;
import com.entity.view.UsersView;

@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UsersEntity> page = this.selectPage(
                new Query<UsersEntity>(params).getPage(),
                new EntityWrapper<UsersEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<UsersEntity> wrapper) {
		  Page<UsersView> page =new Query<UsersView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
            page.setTotal(baseMapper.selectListViewCount(wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<UsersVO> selectListVO(Wrapper<UsersEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public UsersVO selectVO(Wrapper<UsersEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<UsersView> selectListView(Wrapper<UsersEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public UsersView selectView(Wrapper<UsersEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
