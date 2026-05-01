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
 * 好友表
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
@TableName("friend")
@Data
public class FriendEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public FriendEntity() {
		
	}
	
	public FriendEntity(T t) {
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
	 * 用户ID
	 */
	private Long uid;
	
	/**
	 * 好友用户ID
	 */
	private Long fid;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 图片
	 */
	private String picture;
	
	/**
	 * 角色
	 */
	private String role;
	
	/**
	 * 表名
	 */
	private String tablename;
	
	/**
	 * 别名
	 */
	private String alias;
	
	/**
	 * 类型(0:好友申请，1:好友，2:消息)
	 */
	private Integer type;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
