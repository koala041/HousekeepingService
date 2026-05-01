package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.lang.*;
import java.math.*;
import com.utils.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;
import com.annotation.SysLog;

import com.entity.FuwuleixingEntity;
import com.entity.view.FuwuleixingView;

import com.service.FuwuleixingService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
/**
 * 服务类型
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
@RestController
@RequestMapping("/fuwuleixing")
public class FuwuleixingController {
    @Autowired
    private FuwuleixingService fuwuleixingService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,FuwuleixingEntity fuwuleixing,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<FuwuleixingEntity> ew = new EntityWrapper<FuwuleixingEntity>();


        //查询结果
		PageUtils page = fuwuleixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fuwuleixing), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,FuwuleixingEntity fuwuleixing, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<FuwuleixingEntity> ew = new EntityWrapper<FuwuleixingEntity>();

        //查询结果
		PageUtils page = fuwuleixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fuwuleixing), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( FuwuleixingEntity fuwuleixing){
       	EntityWrapper<FuwuleixingEntity> ew = new EntityWrapper<FuwuleixingEntity>();
      	ew.allEq(MPUtil.allEQMapPre( fuwuleixing, "fuwuleixing")); 
        return R.ok().put("data", fuwuleixingService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(FuwuleixingEntity fuwuleixing){
        EntityWrapper< FuwuleixingEntity> ew = new EntityWrapper< FuwuleixingEntity>();
 		ew.allEq(MPUtil.allEQMapPre( fuwuleixing, "fuwuleixing")); 
		FuwuleixingView fuwuleixingView =  fuwuleixingService.selectView(ew);
		return R.ok("查询服务类型成功").put("data", fuwuleixingView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        FuwuleixingEntity fuwuleixing = fuwuleixingService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(fuwuleixing,deSens);
        return R.ok().put("data", fuwuleixing);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        FuwuleixingEntity fuwuleixing = fuwuleixingService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(fuwuleixing,deSens);
        return R.ok().put("data", fuwuleixing);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增服务类型") 
    public R save(@RequestBody FuwuleixingEntity fuwuleixing, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(fuwuleixingService.selectCount(new EntityWrapper<FuwuleixingEntity>().eq("fuwuleixing", fuwuleixing.getFuwuleixing()))>0) {
            return R.error("服务类型已存在");
        }
        //ValidatorUtils.validateEntity(fuwuleixing);
        fuwuleixingService.insert(fuwuleixing);
        return R.ok().put("data",fuwuleixing.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增服务类型")
    @RequestMapping("/add")
    public R add(@RequestBody FuwuleixingEntity fuwuleixing, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(fuwuleixingService.selectCount(new EntityWrapper<FuwuleixingEntity>().eq("fuwuleixing", fuwuleixing.getFuwuleixing()))>0) {
            return R.error("服务类型已存在");
        }
        //ValidatorUtils.validateEntity(fuwuleixing);
        fuwuleixingService.insert(fuwuleixing);
        return R.ok().put("data",fuwuleixing.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改服务类型")
    public R update(@RequestBody FuwuleixingEntity fuwuleixing, HttpServletRequest request){
        //ValidatorUtils.validateEntity(fuwuleixing);
        //验证字段唯一性，否则返回错误信息
        if(fuwuleixingService.selectCount(new EntityWrapper<FuwuleixingEntity>().ne("id", fuwuleixing.getId()).eq("fuwuleixing", fuwuleixing.getFuwuleixing()))>0) {
            return R.error("服务类型已存在");
        }
        //全部更新
        fuwuleixingService.updateById(fuwuleixing);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除服务类型")
    public R delete(@RequestBody Long[] ids){
        fuwuleixingService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    






}
