package com.entity.vo;

import com.entity.FuwuquerenEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 服务确认
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
public class FuwuquerenVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 项目编号
	 */
	
	private String xiangmubianhao;
		
	/**
	 * 项目名称
	 */
	
	private String xiangmumingcheng;
		
	/**
	 * 服务类型
	 */
	
	private String fuwuleixing;
		
	/**
	 * 服务费用
	 */
	
	private Double fuwufeiyong;
		
	/**
	 * 完成时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date wanchengshijian;
		
	/**
	 * 反馈图片
	 */
	
	private String fankuitupian;
		
	/**
	 * 评分
	 */
	
	private Integer pingfen;
		
	/**
	 * 服务评价
	 */
	
	private String fuwupingjia;
		
	/**
	 * 员工姓名
	 */
	
	private String yuangongxingming;
		
	/**
	 * 员工账号
	 */
	
	private String yuangongzhanghao;
		
	/**
	 * 用户账号
	 */
	
	private String yonghuzhanghao;
		
	/**
	 * 用户姓名
	 */
	
	private String yonghuxingming;
		
	/**
	 * 结算状态
	 */
	
	private String jiesuanzhuangtai;
		
	/**
	 * 回复内容
	 */
	
	private String shhf;
				
	
	/**
	 * 设置：项目编号
	 */
	 
	public void setXiangmubianhao(String xiangmubianhao) {
		this.xiangmubianhao = xiangmubianhao;
	}
	
	/**
	 * 获取：项目编号
	 */
	public String getXiangmubianhao() {
		return xiangmubianhao;
	}
				
	
	/**
	 * 设置：项目名称
	 */
	 
	public void setXiangmumingcheng(String xiangmumingcheng) {
		this.xiangmumingcheng = xiangmumingcheng;
	}
	
	/**
	 * 获取：项目名称
	 */
	public String getXiangmumingcheng() {
		return xiangmumingcheng;
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
	 * 设置：服务费用
	 */
	 
	public void setFuwufeiyong(Double fuwufeiyong) {
		this.fuwufeiyong = fuwufeiyong;
	}
	
	/**
	 * 获取：服务费用
	 */
	public Double getFuwufeiyong() {
		return fuwufeiyong;
	}
				
	
	/**
	 * 设置：完成时间
	 */
	 
	public void setWanchengshijian(Date wanchengshijian) {
		this.wanchengshijian = wanchengshijian;
	}
	
	/**
	 * 获取：完成时间
	 */
	public Date getWanchengshijian() {
		return wanchengshijian;
	}
				
	
	/**
	 * 设置：反馈图片
	 */
	 
	public void setFankuitupian(String fankuitupian) {
		this.fankuitupian = fankuitupian;
	}
	
	/**
	 * 获取：反馈图片
	 */
	public String getFankuitupian() {
		return fankuitupian;
	}
				
	
	/**
	 * 设置：评分
	 */
	 
	public void setPingfen(Integer pingfen) {
		this.pingfen = pingfen;
	}
	
	/**
	 * 获取：评分
	 */
	public Integer getPingfen() {
		return pingfen;
	}
				
	
	/**
	 * 设置：服务评价
	 */
	 
	public void setFuwupingjia(String fuwupingjia) {
		this.fuwupingjia = fuwupingjia;
	}
	
	/**
	 * 获取：服务评价
	 */
	public String getFuwupingjia() {
		return fuwupingjia;
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
	 * 设置：员工账号
	 */
	 
	public void setYuangongzhanghao(String yuangongzhanghao) {
		this.yuangongzhanghao = yuangongzhanghao;
	}
	
	/**
	 * 获取：员工账号
	 */
	public String getYuangongzhanghao() {
		return yuangongzhanghao;
	}
				
	
	/**
	 * 设置：用户账号
	 */
	 
	public void setYonghuzhanghao(String yonghuzhanghao) {
		this.yonghuzhanghao = yonghuzhanghao;
	}
	
	/**
	 * 获取：用户账号
	 */
	public String getYonghuzhanghao() {
		return yonghuzhanghao;
	}
				
	
	/**
	 * 设置：用户姓名
	 */
	 
	public void setYonghuxingming(String yonghuxingming) {
		this.yonghuxingming = yonghuxingming;
	}
	
	/**
	 * 获取：用户姓名
	 */
	public String getYonghuxingming() {
		return yonghuxingming;
	}
				
	
	/**
	 * 设置：结算状态
	 */
	 
	public void setJiesuanzhuangtai(String jiesuanzhuangtai) {
		this.jiesuanzhuangtai = jiesuanzhuangtai;
	}
	
	/**
	 * 获取：结算状态
	 */
	public String getJiesuanzhuangtai() {
		return jiesuanzhuangtai;
	}
				
	
	/**
	 * 设置：回复内容
	 */
	 
	public void setShhf(String shhf) {
		this.shhf = shhf;
	}
	
	/**
	 * 获取：回复内容
	 */
	public String getShhf() {
		return shhf;
	}
			
}
