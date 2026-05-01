package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.CaiwujiesuanEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.CaiwujiesuanVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.CaiwujiesuanView;


/**
 * 财务结算
 *
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
public interface CaiwujiesuanService extends IService<CaiwujiesuanEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<CaiwujiesuanVO> selectListVO(Wrapper<CaiwujiesuanEntity> wrapper);
   	
   	CaiwujiesuanVO selectVO(@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);
   	
   	List<CaiwujiesuanView> selectListView(Wrapper<CaiwujiesuanEntity> wrapper);
   	
   	CaiwujiesuanView selectView(@Param("ew") Wrapper<CaiwujiesuanEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<CaiwujiesuanEntity> wrapper);

   	

}

