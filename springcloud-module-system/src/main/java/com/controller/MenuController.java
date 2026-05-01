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

import com.entity.MenuEntity;
import com.entity.view.MenuView;

import com.service.MenuService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
/**
 * 菜单
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-28 09:32:59
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,MenuEntity menu,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<MenuEntity> ew = new EntityWrapper<MenuEntity>();


        //查询结果
		PageUtils page = menuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, menu), params), params));
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
    public R list(@RequestParam Map<String, Object> params,MenuEntity menu, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<MenuEntity> ew = new EntityWrapper<MenuEntity>();

        //查询结果
		PageUtils page = menuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, menu), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( MenuEntity menu){
       	EntityWrapper<MenuEntity> ew = new EntityWrapper<MenuEntity>();
      	ew.allEq(MPUtil.allEQMapPre( menu, "menu")); 
        return R.ok().put("data", menuService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(MenuEntity menu){
        EntityWrapper< MenuEntity> ew = new EntityWrapper< MenuEntity>();
 		ew.allEq(MPUtil.allEQMapPre( menu, "menu")); 
		MenuView menuView =  menuService.selectView(ew);
		return R.ok("查询菜单成功").put("data", menuView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        MenuEntity menu = menuService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(menu,deSens);
        return R.ok().put("data", menu);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        MenuEntity menu = menuService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(menu,deSens);
        return R.ok().put("data", menu);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增菜单") 
    public R save(@RequestBody MenuEntity menu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(menu);
        menuService.insert(menu);
        return R.ok().put("data",menu.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增菜单")
    @RequestMapping("/add")
    public R add(@RequestBody MenuEntity menu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(menu);
        menuService.insert(menu);
        return R.ok().put("data",menu.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        MenuEntity menu = menuService.selectOne(new EntityWrapper<MenuEntity>().eq("", username));
        return R.ok().put("data", menu);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody MenuEntity menu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(menu);
        //全部更新
        menuService.updateById(menu);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除菜单")
    public R delete(@RequestBody Long[] ids){
        menuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,MenuEntity menu, HttpServletRequest request,String pre){
        EntityWrapper<MenuEntity> ew = new EntityWrapper<MenuEntity>();
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
		PageUtils page = menuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, menu), params), params));
        return R.ok().put("data", page);
    }






}
