package com.entity.vo;

import com.entity.UsersEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 管理员
 * @author 
 * @email 
 * @date 2026-04-28 09:32:59
 */
public class UsersVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 密码
	 */
	
	private String password;
		
	/**
	 * 角色
	 */
	
	private String role;
		
	/**
	 * 头像
	 */
	
	private String image;
				
	
	/**
	 * 设置：密码
	 */
	 
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
				
	
	/**
	 * 设置：角色
	 */
	 
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * 获取：角色
	 */
	public String getRole() {
		return role;
	}
				
	
	/**
	 * 设置：头像
	 */
	 
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * 获取：头像
	 */
	public String getImage() {
		return image;
	}
			
}
