package com.entity.view;

import com.entity.FuwurenyuanEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.utils.EncryptUtil;
 

/**
 * 服务人员
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
@TableName("fuwurenyuan")
public class FuwurenyuanView  extends FuwurenyuanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public FuwurenyuanView(){
	}
 
 	public FuwurenyuanView(FuwurenyuanEntity fuwurenyuanEntity){
 	try {
			BeanUtils.copyProperties(this, fuwurenyuanEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
