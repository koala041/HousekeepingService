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
 * 消息表
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
@TableName("chatmessage")
@Data
public class ChatmessageEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public ChatmessageEntity() {
		
	}
	
	public ChatmessageEntity(T t) {
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
	 * 内容
	 */
	private String content;
	
	/**
	 * 格式(1:文字，2:图片)
	 */
	private Integer format;
	
	/**
	 * 消息已读(0:未读，1:已读)
	 */
	private Integer isread;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
