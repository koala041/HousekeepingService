package com.entity.vo;

import com.entity.FuwurenyuanEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 服务人员
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
public class FuwurenyuanVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
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
				
	
	/**
	 * 设置：密码
	 */
	 
	public void setMima(String mima) {
		this.mima = mima;
	}
	
	/**
	 * 获取：密码
	 */
	public String getMima() {
		return mima;
	}
				
	
	/**
	 * 设置：员工姓名
	 */
	 
	public void setYuangongxingming(String yuangongxingming) {
		this.yuangongxingming = yuangongxingming;
	}
	
	/**
	 * 获取：员工姓名
	 */
	public String getYuangongxingming() {
		return yuangongxingming;
	}
				
	
	/**
	 * 设置：头像
	 */
	 
	public void setTouxiang(String touxiang) {
		this.touxiang = touxiang;
	}
	
	/**
	 * 获取：头像
	 */
	public String getTouxiang() {
		return touxiang;
	}
				
	
	/**
	 * 设置：服务技能
	 */
	 
	public void setFuwujineng(String fuwujineng) {
		this.fuwujineng = fuwujineng;
	}
	
	/**
	 * 获取：服务技能
	 */
	public String getFuwujineng() {
		return fuwujineng;
	}
				
	
	/**
	 * 设置：服务状态
	 */
	 
	public void setFuwuzhuangtai(String fuwuzhuangtai) {
		this.fuwuzhuangtai = fuwuzhuangtai;
	}
	
	/**
	 * 获取：服务状态
	 */
	public String getFuwuzhuangtai() {
		return fuwuzhuangtai;
	}
				
	
	/**
	 * 设置：收益
	 */
	 
	public void setShouyi(Double shouyi) {
		this.shouyi = shouyi;
	}
	
	/**
	 * 获取：收益
	 */
	public Double getShouyi() {
		return shouyi;
	}
				
	
	/**
	 * 设置：资质证书
	 */
	 
	public void setZizhizhengshu(String zizhizhengshu) {
		this.zizhizhengshu = zizhizhengshu;
	}
	
	/**
	 * 获取：资质证书
	 */
	public String getZizhizhengshu() {
		return zizhizhengshu;
	}
				
	
	/**
	 * 设置：服务范围
	 */
	 
	public void setFuwufanwei(String fuwufanwei) {
		this.fuwufanwei = fuwufanwei;
	}
	
	/**
	 * 获取：服务范围
	 */
	public String getFuwufanwei() {
		return fuwufanwei;
	}
				
	
	/**
	 * 设置：服务类型
	 */
	 
	public void setFuwuleixing(String fuwuleixing) {
		this.fuwuleixing = fuwuleixing;
	}
	
	/**
	 * 获取：服务类型
	 */
	public String getFuwuleixing() {
		return fuwuleixing;
	}
				
	
	/**
	 * 设置：手机号
	 */
	 
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 获取：手机号
	 */
	public String getMobile() {
		return mobile;
	}
				
	
	/**
	 * 设置：状态
	 */
	 
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 * 获取：状态
	 */
	public Integer getStatus() {
		return status;
	}
			
}
