package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 服务记录
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
@TableName("fuwujilu")
@Data
public class FuwujiluEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public FuwujiluEntity() {
		
	}
	
	public FuwujiluEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 主键id
	 */
    @TableId(type = IdType.AUTO)
    private Long id;
	/**
	 * 预约编号
	 */
	private String yuyuebianhao;
	
	/**
	 * 项目编号
	 */
	private String xiangmubianhao;
	
	/**
	 * 项目名称
	 */
	private String xiangmumingcheng;
	
	/**
	 * 员工账号
	 */
	private String yuangongzhanghao;
	
	/**
	 * 员工姓名
	 */
	private String yuangongxingming;
	
	/**
	 * 服务类型
	 */
	private String fuwuleixing;
	
	/**
	 * 服务费用
	 */
	private Double fuwufeiyong;
	
	/**
	 * 用户账号
	 */
	private String yonghuzhanghao;
	
	/**
	 * 用户姓名
	 */
	private String yonghuxingming;
	
	/**
	 * 手机号
	 */
	private String shoujihao;
	
	/**
	 * 服务时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date fuwushijian;
	
	/**
	 * 服务确认
	 */
	private String fuwuqueren;
	
	/**
	 * 服务图片
	 */
	private String fuwutupian;
	
	/**
	 * 服务记录
	 */
	private String fuwujilu;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
