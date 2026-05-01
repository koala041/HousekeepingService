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
 * 在线客服
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
@TableName("chat")
@Data
public class ChatEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public ChatEntity() {
		
	}
	
	public ChatEntity(T t) {
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
	 * 用户id
	 */
	private Long userid;
	
	/**
	 * 管理员id
	 */
	private Long adminid;
	
	/**
	 * 提问
	 */
	private String ask;
	
	/**
	 * 回复
	 */
	private String reply;
	
	/**
	 * 是否回复
	 */
	private Integer isreply;
	
	/**
	 * 已读/未读(1:已读,0:未读)
	 */
	private Integer isread;
	
	/**
	 * 用户名
	 */
	private String uname;
	
	/**
	 * 用户头像
	 */
	private String uimage;
	
	/**
	 * 内容类型(1:文本,2:图片,3:视频,4:文件,5:表情)
	 */
	private Integer type;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
