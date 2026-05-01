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

import com.entity.SyslogEntity;
import com.entity.view.SyslogView;

import com.service.SyslogService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
/**
 * 系统日志
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
@RestController
@RequestMapping("/syslog")
public class SyslogController {
    @Autowired
    private SyslogService syslogService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,SyslogEntity syslog,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<SyslogEntity> ew = new EntityWrapper<SyslogEntity>();


        //查询结果
		PageUtils page = syslogService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, syslog), params), params));
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
    public R list(@RequestParam Map<String, Object> params,SyslogEntity syslog, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<SyslogEntity> ew = new EntityWrapper<SyslogEntity>();

        //查询结果
		PageUtils page = syslogService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, syslog), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( SyslogEntity syslog){
       	EntityWrapper<SyslogEntity> ew = new EntityWrapper<SyslogEntity>();
      	ew.allEq(MPUtil.allEQMapPre( syslog, "syslog")); 
        return R.ok().put("data", syslogService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(SyslogEntity syslog){
        EntityWrapper< SyslogEntity> ew = new EntityWrapper< SyslogEntity>();
 		ew.allEq(MPUtil.allEQMapPre( syslog, "syslog")); 
		SyslogView syslogView =  syslogService.selectView(ew);
		return R.ok("查询系统日志成功").put("data", syslogView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SyslogEntity syslog = syslogService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(syslog,deSens);
        return R.ok().put("data", syslog);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        SyslogEntity syslog = syslogService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(syslog,deSens);
        return R.ok().put("data", syslog);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增系统日志") 
    public R save(@RequestBody SyslogEntity syslog, HttpServletRequest request){
        //ValidatorUtils.validateEntity(syslog);
        syslogService.insert(syslog);
        return R.ok().put("data",syslog.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增系统日志")
    @RequestMapping("/add")
    public R add(@RequestBody SyslogEntity syslog, HttpServletRequest request){
        //ValidatorUtils.validateEntity(syslog);
        syslogService.insert(syslog);
        return R.ok().put("data",syslog.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        SyslogEntity syslog = syslogService.selectOne(new EntityWrapper<SyslogEntity>().eq("", username));
        return R.ok().put("data", syslog);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody SyslogEntity syslog, HttpServletRequest request){
        //ValidatorUtils.validateEntity(syslog);
        //全部更新
        syslogService.updateById(syslog);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除系统日志")
    public R delete(@RequestBody Long[] ids){
        syslogService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,SyslogEntity syslog, HttpServletRequest request,String pre){
        EntityWrapper<SyslogEntity> ew = new EntityWrapper<SyslogEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        // 组装参数
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        params.put("order", "desc");
		PageUtils page = syslogService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, syslog), params), params));
        return R.ok().put("data", page);
    }






}
