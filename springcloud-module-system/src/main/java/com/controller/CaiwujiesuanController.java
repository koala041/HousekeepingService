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

import com.entity.CaiwujiesuanEntity;
import com.entity.view.CaiwujiesuanView;

import com.service.CaiwujiesuanService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
/**
 * 财务结算
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-28 09:32:58
 */
@RestController
@RequestMapping("/caiwujiesuan")
public class CaiwujiesuanController {
    @Autowired
    private CaiwujiesuanService caiwujiesuanService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,CaiwujiesuanEntity caiwujiesuan,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("fuwurenyuan")) {
			caiwujiesuan.setYuangongzhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<CaiwujiesuanEntity> ew = new EntityWrapper<CaiwujiesuanEntity>();


        //查询结果
		PageUtils page = caiwujiesuanService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, caiwujiesuan), params), params));
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
    public R list(@RequestParam Map<String, Object> params,CaiwujiesuanEntity caiwujiesuan, 
                @RequestParam(required = false) Double fuwufeiyongstart,
                @RequestParam(required = false) Double fuwufeiyongend,
                @RequestParam(required = false) Double yongjinzhanbistart,
                @RequestParam(required = false) Double yongjinzhanbiend,
                @RequestParam(required = false) Double shouyistart,
                @RequestParam(required = false) Double shouyiend,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date jiesuanriqistart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date jiesuanriqiend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<CaiwujiesuanEntity> ew = new EntityWrapper<CaiwujiesuanEntity>();
        if(fuwufeiyongstart!=null) ew.ge("fuwufeiyong", fuwufeiyongstart);
        if(fuwufeiyongend!=null) ew.le("fuwufeiyong", fuwufeiyongend);
        if(yongjinzhanbistart!=null) ew.ge("yongjinzhanbi", yongjinzhanbistart);
        if(yongjinzhanbiend!=null) ew.le("yongjinzhanbi", yongjinzhanbiend);
        if(shouyistart!=null) ew.ge("shouyi", shouyistart);
        if(shouyiend!=null) ew.le("shouyi", shouyiend);
        if(jiesuanriqistart!=null) ew.ge("jiesuanriqi", jiesuanriqistart);
        if(jiesuanriqiend!=null) ew.le("jiesuanriqi", jiesuanriqiend);

        //查询结果
		PageUtils page = caiwujiesuanService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, caiwujiesuan), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( CaiwujiesuanEntity caiwujiesuan){
       	EntityWrapper<CaiwujiesuanEntity> ew = new EntityWrapper<CaiwujiesuanEntity>();
      	ew.allEq(MPUtil.allEQMapPre( caiwujiesuan, "caiwujiesuan")); 
        return R.ok().put("data", caiwujiesuanService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CaiwujiesuanEntity caiwujiesuan){
        EntityWrapper< CaiwujiesuanEntity> ew = new EntityWrapper< CaiwujiesuanEntity>();
 		ew.allEq(MPUtil.allEQMapPre( caiwujiesuan, "caiwujiesuan")); 
		CaiwujiesuanView caiwujiesuanView =  caiwujiesuanService.selectView(ew);
		return R.ok("查询财务结算成功").put("data", caiwujiesuanView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CaiwujiesuanEntity caiwujiesuan = caiwujiesuanService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(caiwujiesuan,deSens);
        return R.ok().put("data", caiwujiesuan);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CaiwujiesuanEntity caiwujiesuan = caiwujiesuanService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(caiwujiesuan,deSens);
        return R.ok().put("data", caiwujiesuan);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增财务结算") 
    public R save(@RequestBody CaiwujiesuanEntity caiwujiesuan, HttpServletRequest request){
        //ValidatorUtils.validateEntity(caiwujiesuan);
        caiwujiesuanService.insert(caiwujiesuan);
        return R.ok().put("data",caiwujiesuan.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增财务结算")
    @RequestMapping("/add")
    public R add(@RequestBody CaiwujiesuanEntity caiwujiesuan, HttpServletRequest request){
        //ValidatorUtils.validateEntity(caiwujiesuan);
        caiwujiesuanService.insert(caiwujiesuan);
        return R.ok().put("data",caiwujiesuan.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改财务结算")
    public R update(@RequestBody CaiwujiesuanEntity caiwujiesuan, HttpServletRequest request){
        //ValidatorUtils.validateEntity(caiwujiesuan);
        //全部更新
        caiwujiesuanService.updateById(caiwujiesuan);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除财务结算")
    public R delete(@RequestBody Long[] ids){
        caiwujiesuanService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
    public R remindCount(@PathVariable("columnName") String columnName, @PathVariable("type") String type, HttpServletRequest request) {

        Wrapper<CaiwujiesuanEntity> wrapper = new EntityWrapper<CaiwujiesuanEntity>();

        Map<String, Object> params = CommonUtil.parseRequest(request);
        // 从map中获取remindStart和remindEnd，组装提醒过滤条件
        Object remindStart = params.get("remindstart") != null ? params.get("remindstart").toString() : null;
        Object remindEnd = params.get("remindend") != null ? params.get("remindend").toString() : null;
        params.remove("remindstart");
        params.remove("remindend");
        params.keySet().forEach(key -> {
            if (params.get(key) != null && params.get(key) != "") {
                wrapper.eq(key, params.get(key));
            }
        });
        switch (type) {
            case "1":
                if (remindStart != null && remindEnd != null) {
                    if (Double.parseDouble(remindStart.toString()) < Double.parseDouble(remindEnd.toString())) {
                        wrapper.ge(columnName, remindStart).le(columnName, remindEnd);
                    } else {
                        wrapper.ge(columnName, remindStart).or().le(columnName, remindEnd);
                    }
                } else if (remindStart == null && remindEnd != null) {
                    wrapper.le(columnName, remindEnd);
                } else if (remindStart != null && remindEnd == null) {
                    wrapper.ge(columnName, remindStart);
                }
                break;
            case "2":
                Date startDate = null;
                Date endDate = null;
                if (null != remindStart) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(remindStart.toString()));
                    startDate = c.getTime();
                }
                if (null != remindEnd) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(remindEnd.toString()));
                    endDate = c.getTime();
                }
                if (startDate != null && endDate != null) {
                    if (startDate.before(endDate)) {
                        wrapper.ge(columnName, startDate).le(columnName, endDate);
                    } else {
                        wrapper.ge(columnName, startDate).or().le(columnName, endDate);
                    }
                } else if (startDate == null && endDate != null) {
                    wrapper.le(columnName, endDate);
                } else if (startDate != null && endDate == null) {
                    wrapper.ge(columnName, startDate);
                }
                break;
            case "3":
                wrapper.in(columnName, Arrays.asList(remindStart.toString().replace("，",",").split(",")));
                break;
        }

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("fuwurenyuan")) {
			wrapper.andNew().eq("yuangongzhanghao", (String)request.getSession().getAttribute("username"));
		}
        //根据条件，查询结果
        List<CaiwujiesuanEntity> list = caiwujiesuanService.selectList(wrapper);
        Map<String,Object> res= new HashMap<>();
        res.put("count", list.size());
        res.put("data",list.stream().map(CaiwujiesuanEntity::getXiangmubianhao).collect(Collectors.toList()));
        return R.ok(res);
	}






}
