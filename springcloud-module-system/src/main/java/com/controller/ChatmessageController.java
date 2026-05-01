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

import com.entity.ChatmessageEntity;
import com.entity.view.ChatmessageView;

import com.service.ChatmessageService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
/**
 * 消息表
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
@RestController
@RequestMapping("/chatmessage")
public class ChatmessageController {
    @Autowired
    private ChatmessageService chatmessageService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ChatmessageEntity chatmessage,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ChatmessageEntity> ew = new EntityWrapper<ChatmessageEntity>();


        //查询结果
		PageUtils page = chatmessageService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chatmessage), params), params));
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
    public R list(@RequestParam Map<String, Object> params,ChatmessageEntity chatmessage, 
                @RequestParam(required = false) Double formatstart,
                @RequestParam(required = false) Double formatend,
                @RequestParam(required = false) Double isreadstart,
                @RequestParam(required = false) Double isreadend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ChatmessageEntity> ew = new EntityWrapper<ChatmessageEntity>();
        if(formatstart!=null) ew.ge("format", formatstart);
        if(formatend!=null) ew.le("format", formatend);
        if(isreadstart!=null) ew.ge("isread", isreadstart);
        if(isreadend!=null) ew.le("isread", isreadend);

        //查询结果
		PageUtils page = chatmessageService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chatmessage), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }

    /**
     * 消息列表
     */
    @RequestMapping("/mlist")
    public R mlist(@RequestParam Map<String, Object> params,ChatmessageEntity chatmessage, HttpServletRequest request){
        EntityWrapper<ChatmessageEntity> ew = new EntityWrapper<ChatmessageEntity>();
        ew.eq("uid", chatmessage.getUid()).eq("fid", chatmessage.getFid()).orNew().eq("fid", chatmessage.getUid()).eq("uid", chatmessage.getFid());
        PageUtils page = chatmessageService.queryPage(params, ew);
        if(null!=chatmessage.getFid())
        {
            chatmessageService.updateForSet("isread=1", new EntityWrapper<ChatmessageEntity>().eq("isread", 0).eq("fid", chatmessage.getUid()).eq("uid", chatmessage.getFid()));
        }
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ChatmessageEntity chatmessage){
       	EntityWrapper<ChatmessageEntity> ew = new EntityWrapper<ChatmessageEntity>();
      	ew.allEq(MPUtil.allEQMapPre( chatmessage, "chatmessage")); 
        return R.ok().put("data", chatmessageService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ChatmessageEntity chatmessage){
        EntityWrapper< ChatmessageEntity> ew = new EntityWrapper< ChatmessageEntity>();
 		ew.allEq(MPUtil.allEQMapPre( chatmessage, "chatmessage")); 
		ChatmessageView chatmessageView =  chatmessageService.selectView(ew);
		return R.ok("查询消息表成功").put("data", chatmessageView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ChatmessageEntity chatmessage = chatmessageService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(chatmessage,deSens);
        return R.ok().put("data", chatmessage);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ChatmessageEntity chatmessage = chatmessageService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(chatmessage,deSens);
        return R.ok().put("data", chatmessage);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增消息表") 
    public R save(@RequestBody ChatmessageEntity chatmessage, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chatmessage);
        chatmessageService.insert(chatmessage);
        return R.ok().put("data",chatmessage.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增消息表")
    @RequestMapping("/add")
    public R add(@RequestBody ChatmessageEntity chatmessage, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chatmessage);
        chatmessageService.insert(chatmessage);
        return R.ok().put("data",chatmessage.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        ChatmessageEntity chatmessage = chatmessageService.selectOne(new EntityWrapper<ChatmessageEntity>().eq("", username));
        return R.ok().put("data", chatmessage);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody ChatmessageEntity chatmessage, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chatmessage);
        //全部更新
        chatmessageService.updateById(chatmessage);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除消息表")
    public R delete(@RequestBody Long[] ids){
        chatmessageService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,ChatmessageEntity chatmessage, HttpServletRequest request,String pre){
        EntityWrapper<ChatmessageEntity> ew = new EntityWrapper<ChatmessageEntity>();
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
		PageUtils page = chatmessageService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chatmessage), params), params));
        return R.ok().put("data", page);
    }






}
