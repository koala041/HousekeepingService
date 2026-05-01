package com.entity.vo;

import com.entity.CaiwujiesuanEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 财务结算
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
public class CaiwujiesuanVO  implements Serializable {
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
	 * 佣金占比
	 */
	
	private Double yongjinzhanbi;
		
	/**
	 * 收益
	 */
	
	private Double shouyi;
		
	/**
	 * 员工账号
	 */
	
	private String yuangongzhanghao;
		
	/**
	 * 员工姓名
	 */
	
	private String yuangongxingming;
		
	/**
	 * 结算日期
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date jiesuanriqi;
		
	/**
	 * 提现状态
	 */
	
	private String tixianzhuangtai;
				
	
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
	 * 设置：佣金占比
	 */
	 
	public void setYongjinzhanbi(Double yongjinzhanbi) {
		this.yongjinzhanbi = yongjinzhanbi;
	}
	
	/**
	 * 获取：佣金占比
	 */
	public Double getYongjinzhanbi() {
		return yongjinzhanbi;
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
	 * 设置：结算日期
	 */
	 
	public void setJiesuanriqi(Date jiesuanriqi) {
		this.jiesuanriqi = jiesuanriqi;
	}
	
	/**
	 * 获取：结算日期
	 */
	public Date getJiesuanriqi() {
		return jiesuanriqi;
	}
				
	
	/**
	 * 设置：提现状态
	 */
	 
	public void setTixianzhuangtai(String tixianzhuangtai) {
		this.tixianzhuangtai = tixianzhuangtai;
	}
	
	/**
	 * 获取：提现状态
	 */
	public String getTixianzhuangtai() {
		return tixianzhuangtai;
	}
			
}
