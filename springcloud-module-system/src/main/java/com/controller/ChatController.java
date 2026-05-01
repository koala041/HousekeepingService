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

import com.entity.ChatEntity;
import com.entity.view.ChatView;

import com.service.ChatService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
/**
 * 在线客服
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ChatEntity chat,
		HttpServletRequest request){
        if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
            chat.setUserid((Long)request.getSession().getAttribute("userId"));
        }
        //设置查询条件
        EntityWrapper<ChatEntity> ew = new EntityWrapper<ChatEntity>();


        if (null != params.get("userid")) {
            ChatEntity new_chat = new ChatEntity<>();
            new_chat.setIsread(1);
            chatService.update(new_chat, new EntityWrapper<ChatEntity>().eq("userid" , params.get("userid")).eq("isread","0"));
        }
        //查询结果
		PageUtils page = chatService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chat), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 前台列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ChatEntity chat, 
                @RequestParam(required = false) Double isreplystart,
                @RequestParam(required = false) Double isreplyend,
                @RequestParam(required = false) Double isreadstart,
                @RequestParam(required = false) Double isreadend,
                @RequestParam(required = false) Double typestart,
                @RequestParam(required = false) Double typeend,
		HttpServletRequest request){
    	if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
    		chat.setUserid((Long)request.getSession().getAttribute("userId"));
    	}
        //设置查询条件
        EntityWrapper<ChatEntity> ew = new EntityWrapper<ChatEntity>();
        if(isreplystart!=null) ew.ge("isreply", isreplystart);
        if(isreplyend!=null) ew.le("isreply", isreplyend);
        if(isreadstart!=null) ew.ge("isread", isreadstart);
        if(isreadend!=null) ew.le("isread", isreadend);
        if(typestart!=null) ew.ge("type", typestart);
        if(typeend!=null) ew.le("type", typeend);

        //查询结果
		PageUtils page = chatService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chat), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ChatEntity chat){
       	EntityWrapper<ChatEntity> ew = new EntityWrapper<ChatEntity>();
      	ew.allEq(MPUtil.allEQMapPre( chat, "chat")); 
        return R.ok().put("data", chatService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ChatEntity chat){
        EntityWrapper< ChatEntity> ew = new EntityWrapper< ChatEntity>();
 		ew.allEq(MPUtil.allEQMapPre( chat, "chat")); 
		ChatView chatView =  chatService.selectView(ew);
		return R.ok("查询在线客服成功").put("data", chatView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ChatEntity chat = chatService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(chat,deSens);
        return R.ok().put("data", chat);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ChatEntity chat = chatService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(chat,deSens);
        return R.ok().put("data", chat);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增在线客服") 
    public R save(@RequestBody ChatEntity chat, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chat);
    	if(StringUtils.isNotBlank(chat.getAsk())) {
			chatService.updateForSet("isreply=0", new EntityWrapper<ChatEntity>().eq("userid", request.getSession().getAttribute("userId")));
    		chat.setUserid((Long)request.getSession().getAttribute("userId"));
    		chat.setIsreply(1);
    	}
    	if(StringUtils.isNotBlank(chat.getReply())) {
    		chatService.updateForSet("isreply=0", new EntityWrapper<ChatEntity>().eq("userid", chat.getUserid()));
    		chat.setAdminid((Long)request.getSession().getAttribute("userId"));
    	}
        chatService.insert(chat);
        return R.ok().put("data",chat.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增在线客服")
    @RequestMapping("/add")
    public R add(@RequestBody ChatEntity chat, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chat);
    	chat.setUserid((Long)request.getSession().getAttribute("userId"));
    	if(StringUtils.isNotBlank(chat.getAsk())) {
			chatService.updateForSet("isreply=0", new EntityWrapper<ChatEntity>().eq("userid", request.getSession().getAttribute("userId")));
    		chat.setUserid((Long)request.getSession().getAttribute("userId"));
    		chat.setIsreply(1);
    	}
    	if(StringUtils.isNotBlank(chat.getReply())) {
    		chatService.updateForSet("isreply=0", new EntityWrapper<ChatEntity>().eq("userid", chat.getUserid()));
    		chat.setAdminid((Long)request.getSession().getAttribute("userId"));
    	}
        chatService.insert(chat);
        return R.ok().put("data",chat.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        ChatEntity chat = chatService.selectOne(new EntityWrapper<ChatEntity>().eq("", username));
        return R.ok().put("data", chat);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody ChatEntity chat, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chat);
        //全部更新
        chatService.updateById(chat);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除在线客服")
    public R delete(@RequestBody Long[] ids){
        chatService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,ChatEntity chat, HttpServletRequest request,String pre){
        EntityWrapper<ChatEntity> ew = new EntityWrapper<ChatEntity>();
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
		PageUtils page = chatService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chat), params), params));
        return R.ok().put("data", page);
    }






}
