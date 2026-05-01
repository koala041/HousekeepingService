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
 * 服务人员
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
@TableName("fuwurenyuan")
@Data
public class FuwurenyuanEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public FuwurenyuanEntity() {
		
	}
	
	public FuwurenyuanEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 主键id
	 */
    @TableId
    private Long id;
	/**
	 * 员工账号
	 */
	private String yuangongzhanghao;
	
	/**
	 * 密码
	 */
	private String mima;
	
	/**
	 * 员工姓名
	 */
	private String yuangongxingming;
	
	/**
	 * 头像
	 */
	private String touxiang;
	
	/**
	 * 服务技能
	 */
	private String fuwujineng;
	
	/**
	 * 服务状态
	 */
	private String fuwuzhuangtai;
	
	/**
	 * 收益
	 */
	private Double shouyi;
	
	/**
	 * 资质证书
	 */
	private String zizhizhengshu;
	
	/**
	 * 服务范围
	 */
	private String fuwufanwei;
	
	/**
	 * 服务类型
	 */
	private String fuwuleixing;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
