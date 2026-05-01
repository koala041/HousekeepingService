package com.entity.vo;

import com.entity.NewsEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 通知公告
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
public class NewsVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 内容
	 */
	
	private String content;
		
	/**
	 * 发布人
	 */
	
	private String name;
		
	/**
	 * 头像
	 */
	
	private String headportrait;
		
	/**
	 * 简介
	 */
	
	private String introduction;
		
	/**
	 * 图片
	 */
	
	private String picture;
				
	
	/**
	 * 设置：内容
	 */
	 
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
				
	
	/**
	 * 设置：发布人
	 */
	 
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取：发布人
	 */
	public String getName() {
		return name;
	}
				
	
	/**
	 * 设置：头像
	 */
	 
	public void setHeadportrait(String headportrait) {
		this.headportrait = headportrait;
	}
	
	/**
	 * 获取：头像
	 */
	public String getHeadportrait() {
		return headportrait;
	}
				
	
	/**
	 * 设置：简介
	 */
	 
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	/**
	 * 获取：简介
	 */
	public String getIntroduction() {
		return introduction;
	}
				
	
	/**
	 * 设置：图片
	 */
	 
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	/**
	 * 获取：图片
	 */
	public String getPicture() {
		return picture;
	}
			
}
