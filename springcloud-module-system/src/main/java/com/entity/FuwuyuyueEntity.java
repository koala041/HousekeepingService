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
 * 服务预约
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
@TableName("fuwuyuyue")
@Data
public class FuwuyuyueEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public FuwuyuyueEntity() {
		
	}
	
	public FuwuyuyueEntity(T t) {
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
	 * 项目图片
	 */
	private String xiangmutupian;
	
	/**
	 * 服务类型
	 */
	private String fuwuleixing;
	
	/**
	 * 服务费用
	 */
	private Double fuwufeiyong;
	
	/**
	 * 位置
	 */
	private String weizhi;
	
	/**
	 * 服务内容
	 */
	private String fuwuneirong;
	
	/**
	 * 预约日期
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat
	private Date yuyueriqi;
	
	/**
	 * 预约时段
	 */
	private String yuyueshiduan;
	
	/**
	 * 预约时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date yuyueshijian;
	
	/**
	 * 订单状态
	 */
	private String dingdanzhuangtai;
	
	/**
	 * 用户账号
	 */
	private String yonghuzhanghao;
	
	/**
	 * 用户姓名
	 */
	private String yonghuxingming;
	
	/**
	 * 服务地址
	 */
	private String fuwudizhi;
	
	/**
	 * 手机号
	 */
	private String shoujihao;
	
	/**
	 * 是否支付
	 */
	private String ispay;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
