package com.entity.vo;

import com.entity.YonghuEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 用户
 * @author GG Bond
 * @email 
 * @date 2026-04-28 09:32:56
 */
@Data
public class YonghuVO  implements Serializable {
	private static final long serialVersionUID = 1L;
	 			
	/**
	 * 密码
	 */
	private String mima;
		
	/**
	 * 用户姓名
	 */
	private String yonghuxingming;
		
	/**
	 * 性别
	 */
	private String xingbie;
		
	/**
	 * 头像
	 */
	private String touxiang;
		
	/**
	 * 手机号
	 */
	private String mobile;
		
	/**
	 * 状态
	 */
	private Integer status;

}
