package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ShourumingxiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ShourumingxiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ShourumingxiView;


/**
 * 收入明细
 *
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
public interface ShourumingxiService extends IService<ShourumingxiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ShourumingxiVO> selectListVO(Wrapper<ShourumingxiEntity> wrapper);
   	
   	ShourumingxiVO selectVO(@Param("ew") Wrapper<ShourumingxiEntity> wrapper);
   	
   	List<ShourumingxiView> selectListView(Wrapper<ShourumingxiEntity> wrapper);
   	
   	ShourumingxiView selectView(@Param("ew") Wrapper<ShourumingxiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ShourumingxiEntity> wrapper);

   	

    List<Map<String, Object>> selectValue(Map<String, Object> params,Wrapper<ShourumingxiEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params,Wrapper<ShourumingxiEntity> wrapper);

    List<Map<String, Object>> selectGroup(Map<String, Object> params,Wrapper<ShourumingxiEntity> wrapper);



}

