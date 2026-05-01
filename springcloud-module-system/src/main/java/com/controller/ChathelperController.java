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

import com.entity.ChathelperEntity;
import com.entity.view.ChathelperView;

import com.service.ChathelperService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
/**
 * 聊天助手表
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
@RestController
@RequestMapping("/chathelper")
public class ChathelperController {
    @Autowired
    private ChathelperService chathelperService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ChathelperEntity chathelper,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ChathelperEntity> ew = new EntityWrapper<ChathelperEntity>();


        //查询结果
		PageUtils page = chathelperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chathelper), params), params));
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
    public R list(@RequestParam Map<String, Object> params,ChathelperEntity chathelper, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ChathelperEntity> ew = new EntityWrapper<ChathelperEntity>();

        //查询结果
		PageUtils page = chathelperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chathelper), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ChathelperEntity chathelper){
       	EntityWrapper<ChathelperEntity> ew = new EntityWrapper<ChathelperEntity>();
      	ew.allEq(MPUtil.allEQMapPre( chathelper, "chathelper")); 
        return R.ok().put("data", chathelperService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ChathelperEntity chathelper){
        EntityWrapper< ChathelperEntity> ew = new EntityWrapper< ChathelperEntity>();
 		ew.allEq(MPUtil.allEQMapPre( chathelper, "chathelper")); 
		ChathelperView chathelperView =  chathelperService.selectView(ew);
		return R.ok("查询聊天助手表成功").put("data", chathelperView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ChathelperEntity chathelper = chathelperService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(chathelper,deSens);
        return R.ok().put("data", chathelper);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ChathelperEntity chathelper = chathelperService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(chathelper,deSens);
        return R.ok().put("data", chathelper);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增聊天助手表") 
    public R save(@RequestBody ChathelperEntity chathelper, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chathelper);
        chathelperService.insert(chathelper);
        return R.ok().put("data",chathelper.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增聊天助手表")
    @RequestMapping("/add")
    public R add(@RequestBody ChathelperEntity chathelper, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chathelper);
        chathelperService.insert(chathelper);
        return R.ok().put("data",chathelper.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        ChathelperEntity chathelper = chathelperService.selectOne(new EntityWrapper<ChathelperEntity>().eq("", username));
        return R.ok().put("data", chathelper);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody ChathelperEntity chathelper, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chathelper);
        //全部更新
        chathelperService.updateById(chathelper);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除聊天助手表")
    public R delete(@RequestBody Long[] ids){
        chathelperService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,ChathelperEntity chathelper, HttpServletRequest request,String pre){
        EntityWrapper<ChathelperEntity> ew = new EntityWrapper<ChathelperEntity>();
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
		PageUtils page = chathelperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chathelper), params), params));
        return R.ok().put("data", page);
    }






}
