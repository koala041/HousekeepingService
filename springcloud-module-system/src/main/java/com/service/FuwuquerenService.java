package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.FuwuquerenEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.FuwuquerenVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.FuwuquerenView;


/**
 * 服务确认
 *
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
public interface FuwuquerenService extends IService<FuwuquerenEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<FuwuquerenVO> selectListVO(Wrapper<FuwuquerenEntity> wrapper);
   	
   	FuwuquerenVO selectVO(@Param("ew") Wrapper<FuwuquerenEntity> wrapper);
   	
   	List<FuwuquerenView> selectListView(Wrapper<FuwuquerenEntity> wrapper);
   	
   	FuwuquerenView selectView(@Param("ew") Wrapper<FuwuquerenEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<FuwuquerenEntity> wrapper);

   	

    List<Map<String, Object>> selectValue(Map<String, Object> params,Wrapper<FuwuquerenEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params,Wrapper<FuwuquerenEntity> wrapper);

    List<Map<String, Object>> selectGroup(Map<String, Object> params,Wrapper<FuwuquerenEntity> wrapper);



}

