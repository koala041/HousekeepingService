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
 * 服务信息评论
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-28 09:32:59
 */
@TableName("discussfuwuxinxi")
@Data
public class DiscussfuwuxinxiEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public DiscussfuwuxinxiEntity() {
		
	}
	
	public DiscussfuwuxinxiEntity(T t) {
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
	 * 关联表id
	 */
	private Long refid;
	
	/**
	 * 用户id
	 */
	private Long userid;
	
	/**
	 * 头像
	 */
	private String avatarurl;
	
	/**
	 * 用户名
	 */
	private String nickname;
	
	/**
	 * 评论内容
	 */
	private String content;
	
	/**
	 * 评分
	 */
	private Double score;
	
	/**
	 * 回复内容
	 */
	private String reply;
	
	/**
	 * 赞
	 */
	private Integer thumbsupnum;
	
	/**
	 * 踩
	 */
	private Integer crazilynum;
	
	/**
	 * 置顶(1:置顶,0:非置顶)
	 */
	private Integer istop;
	
	/**
	 * 赞用户ids
	 */
	private String tuserids;
	
	/**
	 * 踩用户ids
	 */
	private String cuserids;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
