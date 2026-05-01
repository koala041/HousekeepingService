package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.UsersEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.UsersVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.UsersView;


/**
 * 管理员
 *
 * @author 
 * @email 
 * @date 2026-04-28 09:32:59
 */
public interface UsersService extends IService<UsersEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<UsersVO> selectListVO(Wrapper<UsersEntity> wrapper);
   	
   	UsersVO selectVO(@Param("ew") Wrapper<UsersEntity> wrapper);
   	
   	List<UsersView> selectListView(Wrapper<UsersEntity> wrapper);
   	
   	UsersView selectView(@Param("ew") Wrapper<UsersEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<UsersEntity> wrapper);

   	

}

