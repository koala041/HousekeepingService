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
 * 服务信息
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
@TableName("fuwuxinxi")
@Data
public class FuwuxinxiEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public FuwuxinxiEntity() {
		
	}
	
	public FuwuxinxiEntity(T t) {
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
	 * 服务内容
	 */
	private String fuwuneirong;
	
	/**
	 * 服务说明
	 */
	private String fuwushuoming;
	
	/**
	 * 位置
	 */
	private String weizhi;
	
	/**
	 * 营业时间
	 */
	private String yingyeshijian;
	
	/**
	 * 联系电话
	 */
	private String lianxidianhua;
	
	/**
	 * 上下架
	 */
	private Integer onshelves;
	
	/**
	 * 销量
	 */
	private Integer xiaoliang;
	
	/**
	 * 赞
	 */
	private Integer thumbsupnum;
	
	/**
	 * 踩
	 */
	private Integer crazilynum;
	
	/**
	 * 最近点击时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date clicktime;
	
	/**
	 * 评论数
	 */
	private Integer discussnum;
	
	/**
	 * 评分
	 */
	private Double totalscore;
	
	/**
	 * 收藏数
	 */
	private Integer storeupnum;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
