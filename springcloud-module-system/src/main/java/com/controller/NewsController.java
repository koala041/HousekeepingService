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

import com.entity.NewsEntity;
import com.entity.view.NewsView;

import com.service.NewsService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
/**
 * 通知公告
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,NewsEntity news,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<NewsEntity> ew = new EntityWrapper<NewsEntity>();


        //查询结果
		PageUtils page = newsService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, news), params), params));
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
    public R list(@RequestParam Map<String, Object> params,NewsEntity news, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<NewsEntity> ew = new EntityWrapper<NewsEntity>();

        //查询结果
		PageUtils page = newsService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, news), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( NewsEntity news){
       	EntityWrapper<NewsEntity> ew = new EntityWrapper<NewsEntity>();
      	ew.allEq(MPUtil.allEQMapPre( news, "news")); 
        return R.ok().put("data", newsService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(NewsEntity news){
        EntityWrapper< NewsEntity> ew = new EntityWrapper< NewsEntity>();
 		ew.allEq(MPUtil.allEQMapPre( news, "news")); 
		NewsView newsView =  newsService.selectView(ew);
		return R.ok("查询通知公告成功").put("data", newsView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        NewsEntity news = newsService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(news,deSens);
        return R.ok().put("data", news);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        NewsEntity news = newsService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(news,deSens);
        return R.ok().put("data", news);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增通知公告") 
    public R save(@RequestBody NewsEntity news, HttpServletRequest request){
        //ValidatorUtils.validateEntity(news);
        newsService.insert(news);
        return R.ok().put("data",news.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增通知公告")
    @RequestMapping("/add")
    public R add(@RequestBody NewsEntity news, HttpServletRequest request){
        //ValidatorUtils.validateEntity(news);
        newsService.insert(news);
        return R.ok().put("data",news.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改通知公告")
    public R update(@RequestBody NewsEntity news, HttpServletRequest request){
        //ValidatorUtils.validateEntity(news);
        //全部更新
        newsService.updateById(news);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除通知公告")
    public R delete(@RequestBody Long[] ids){
        newsService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    






}
