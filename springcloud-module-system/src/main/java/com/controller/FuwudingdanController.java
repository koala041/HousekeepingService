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

import com.entity.FuwudingdanEntity;
import com.entity.view.FuwudingdanView;

import com.service.FuwudingdanService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
/**
 * 服务订单
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
@RestController
@RequestMapping("/fuwudingdan")
public class FuwudingdanController {
    @Autowired
    private FuwudingdanService fuwudingdanService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,FuwudingdanEntity fuwudingdan,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			fuwudingdan.setYonghuzhanghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("fuwurenyuan")) {
			fuwudingdan.setYuangongzhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<FuwudingdanEntity> ew = new EntityWrapper<FuwudingdanEntity>();


        //查询结果
		PageUtils page = fuwudingdanService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fuwudingdan), params), params));
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
    public R list(@RequestParam Map<String, Object> params,FuwudingdanEntity fuwudingdan, 
                @RequestParam(required = false) Double fuwufeiyongstart,
                @RequestParam(required = false) Double fuwufeiyongend,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date jiedanshijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date jiedanshijianend,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date yujidaodashijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date yujidaodashijianend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<FuwudingdanEntity> ew = new EntityWrapper<FuwudingdanEntity>();
        if(fuwufeiyongstart!=null) ew.ge("fuwufeiyong", fuwufeiyongstart);
        if(fuwufeiyongend!=null) ew.le("fuwufeiyong", fuwufeiyongend);
        if(jiedanshijianstart!=null) ew.ge("jiedanshijian", jiedanshijianstart);
        if(jiedanshijianend!=null) ew.le("jiedanshijian", jiedanshijianend);
        if(yujidaodashijianstart!=null) ew.ge("yujidaodashijian", yujidaodashijianstart);
        if(yujidaodashijianend!=null) ew.le("yujidaodashijian", yujidaodashijianend);

        //查询结果
		PageUtils page = fuwudingdanService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fuwudingdan), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( FuwudingdanEntity fuwudingdan){
       	EntityWrapper<FuwudingdanEntity> ew = new EntityWrapper<FuwudingdanEntity>();
      	ew.allEq(MPUtil.allEQMapPre( fuwudingdan, "fuwudingdan")); 
        return R.ok().put("data", fuwudingdanService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(FuwudingdanEntity fuwudingdan){
        EntityWrapper< FuwudingdanEntity> ew = new EntityWrapper< FuwudingdanEntity>();
 		ew.allEq(MPUtil.allEQMapPre( fuwudingdan, "fuwudingdan")); 
		FuwudingdanView fuwudingdanView =  fuwudingdanService.selectView(ew);
		return R.ok("查询服务订单成功").put("data", fuwudingdanView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        FuwudingdanEntity fuwudingdan = fuwudingdanService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(fuwudingdan,deSens);
        return R.ok().put("data", fuwudingdan);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        FuwudingdanEntity fuwudingdan = fuwudingdanService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(fuwudingdan,deSens);
        return R.ok().put("data", fuwudingdan);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增服务订单") 
    public R save(@RequestBody FuwudingdanEntity fuwudingdan, HttpServletRequest request){
        //ValidatorUtils.validateEntity(fuwudingdan);
        fuwudingdanService.insert(fuwudingdan);
        return R.ok().put("data",fuwudingdan.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增服务订单")
    @RequestMapping("/add")
    public R add(@RequestBody FuwudingdanEntity fuwudingdan, HttpServletRequest request){
        //ValidatorUtils.validateEntity(fuwudingdan);
        fuwudingdanService.insert(fuwudingdan);
        return R.ok().put("data",fuwudingdan.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改服务订单")
    public R update(@RequestBody FuwudingdanEntity fuwudingdan, HttpServletRequest request){
        //ValidatorUtils.validateEntity(fuwudingdan);
        //全部更新
        fuwudingdanService.updateById(fuwudingdan);
        return R.ok();
    }

    /**
     * 审核
     */
    @RequestMapping("/shBatch")
    @Transactional
    @SysLog("审核服务订单")
    public R update(@RequestBody Long[] ids, @RequestParam String sfsh, @RequestParam String shhf){
        List<FuwudingdanEntity> list = new ArrayList<FuwudingdanEntity>();
        for(Long id : ids) {
            FuwudingdanEntity fuwudingdan = fuwudingdanService.selectById(id);
            fuwudingdan.setSfsh(sfsh);
            fuwudingdan.setShhf(shhf);
            list.add(fuwudingdan);
        }
        fuwudingdanService.updateBatchById(list);
        return R.ok();
    }


    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除服务订单")
    public R delete(@RequestBody Long[] ids){
        fuwudingdanService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
    public R remindCount(@PathVariable("columnName") String columnName, @PathVariable("type") String type, HttpServletRequest request) {

        Wrapper<FuwudingdanEntity> wrapper = new EntityWrapper<FuwudingdanEntity>();

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
		if(tableName.equals("yonghu")) {
			wrapper.andNew().eq("yonghuzhanghao", (String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("fuwurenyuan")) {
			wrapper.andNew().eq("yuangongzhanghao", (String)request.getSession().getAttribute("username"));
		}
        //根据条件，查询结果
        List<FuwudingdanEntity> list = fuwudingdanService.selectList(wrapper);
        Map<String,Object> res= new HashMap<>();
        res.put("count", list.size());
        res.put("data",list.stream().map(FuwudingdanEntity::getXiangmubianhao).collect(Collectors.toList()));
        return R.ok(res);
	}






}
