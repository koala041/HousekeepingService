package com.entity.vo;

import com.entity.FuwuyuyueEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 服务预约
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
public class FuwuyuyueVO  implements Serializable {
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
	 * 位置
	 */
	
	private String weizhi;
		
	/**
	 * 服务内容
	 */
	
	private String fuwuneirong;
		
	/**
	 * 预约日期
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date yuyueriqi;
		
	/**
	 * 预约时段
	 */
	
	private String yuyueshiduan;
		
	/**
	 * 预约时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date yuyueshijian;
		
	/**
	 * 订单状态
	 */
	
	private String dingdanzhuangtai;
		
	/**
	 * 用户账号
	 */
	
	private String yonghuzhanghao;
		
	/**
	 * 用户姓名
	 */
	
	private String yonghuxingming;
		
	/**
	 * 服务地址
	 */
	
	private String fuwudizhi;
		
	/**
	 * 手机号
	 */
	
	private String shoujihao;
		
	/**
	 * 是否支付
	 */
	
	private String ispay;
				
	
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
	 * 设置：项目图片
	 */
	 
	public void setXiangmutupian(String xiangmutupian) {
		this.xiangmutupian = xiangmutupian;
	}
	
	/**
	 * 获取：项目图片
	 */
	public String getXiangmutupian() {
		return xiangmutupian;
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
	 * 设置：位置
	 */
	 
	public void setWeizhi(String weizhi) {
		this.weizhi = weizhi;
	}
	
	/**
	 * 获取：位置
	 */
	public String getWeizhi() {
		return weizhi;
	}
				
	
	/**
	 * 设置：服务内容
	 */
	 
	public void setFuwuneirong(String fuwuneirong) {
		this.fuwuneirong = fuwuneirong;
	}
	
	/**
	 * 获取：服务内容
	 */
	public String getFuwuneirong() {
		return fuwuneirong;
	}
				
	
	/**
	 * 设置：预约日期
	 */
	 
	public void setYuyueriqi(Date yuyueriqi) {
		this.yuyueriqi = yuyueriqi;
	}
	
	/**
	 * 获取：预约日期
	 */
	public Date getYuyueriqi() {
		return yuyueriqi;
	}
				
	
	/**
	 * 设置：预约时段
	 */
	 
	public void setYuyueshiduan(String yuyueshiduan) {
		this.yuyueshiduan = yuyueshiduan;
	}
	
	/**
	 * 获取：预约时段
	 */
	public String getYuyueshiduan() {
		return yuyueshiduan;
	}
				
	
	/**
	 * 设置：预约时间
	 */
	 
	public void setYuyueshijian(Date yuyueshijian) {
		this.yuyueshijian = yuyueshijian;
	}
	
	/**
	 * 获取：预约时间
	 */
	public Date getYuyueshijian() {
		return yuyueshijian;
	}
				
	
	/**
	 * 设置：订单状态
	 */
	 
	public void setDingdanzhuangtai(String dingdanzhuangtai) {
		this.dingdanzhuangtai = dingdanzhuangtai;
	}
	
	/**
	 * 获取：订单状态
	 */
	public String getDingdanzhuangtai() {
		return dingdanzhuangtai;
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
	 * 设置：服务地址
	 */
	 
	public void setFuwudizhi(String fuwudizhi) {
		this.fuwudizhi = fuwudizhi;
	}
	
	/**
	 * 获取：服务地址
	 */
	public String getFuwudizhi() {
		return fuwudizhi;
	}
				
	
	/**
	 * 设置：手机号
	 */
	 
	public void setShoujihao(String shoujihao) {
		this.shoujihao = shoujihao;
	}
	
	/**
	 * 获取：手机号
	 */
	public String getShoujihao() {
		return shoujihao;
	}
				
	
	/**
	 * 设置：是否支付
	 */
	 
	public void setIspay(String ispay) {
		this.ispay = ispay;
	}
	
	/**
	 * 获取：是否支付
	 */
	public String getIspay() {
		return ispay;
	}
			
}
