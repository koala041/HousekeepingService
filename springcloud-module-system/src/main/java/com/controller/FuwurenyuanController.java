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

import com.entity.TokenEntity;
import com.service.TokenService;
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

import com.entity.FuwurenyuanEntity;
import com.entity.view.FuwurenyuanView;

import com.service.FuwurenyuanService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.SmsregistercodeService;
import com.entity.SmsregistercodeEntity;
import com.utils.RedisUtils;
/**
 * 服务人员
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-28 09:32:57
 */
@RestController
@RequestMapping("/fuwurenyuan")
public class FuwurenyuanController {
    @Autowired
    private FuwurenyuanService fuwurenyuanService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SmsregistercodeService smsregistercodeService;




    
	@Autowired
	private TokenService tokenService;

	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
        // 根据登录查询用户信息
        FuwurenyuanEntity u = fuwurenyuanService.selectOne(new EntityWrapper<FuwurenyuanEntity>().eq("yuangongzhanghao", username));
        // 判断用户锁定状态
        if(u!=null && u.getStatus().intValue()==1) {
            //返回已锁定提示
            return R.error("账号已锁定，请联系管理员。");
        }
        if(u==null || !u.getMima().equals(password)) {
            //账号或密码不正确提示
			return R.error("账号或密码不正确");
		}
        // 获取登录token
		String token = tokenService.generateToken(u.getId(), username,"fuwurenyuan",  "服务人员" );
        // 将token缓存到redis
        redisUtils.set("fuwurenyuan"+username, token, 3600);
        //返回token
		return R.ok().put("token", token);
	}


	
	/**
     * 注册
     */
	@IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody FuwurenyuanEntity fuwurenyuan, @RequestParam(required = false) String smscode){
    	//ValidatorUtils.validateEntity(fuwurenyuan);
        //根据登录账号获取用户信息判断是否存在该用户，否则返回错误信息
    	FuwurenyuanEntity u = fuwurenyuanService.selectOne(new EntityWrapper<FuwurenyuanEntity>().eq("yuangongzhanghao", fuwurenyuan.getYuangongzhanghao()));
        if(u!=null) {
			return R.error("注册用户已存在");
		}
        //判断是否存在相同员工账号，否则返回错误信息
        if(fuwurenyuanService.selectCount(new EntityWrapper<FuwurenyuanEntity>().eq("yuangongzhanghao", fuwurenyuan.getYuangongzhanghao()))>0) {
            return R.error("员工账号已存在");
        }
        //判断是否存在相同手机号，否则返回错误信息
        u =fuwurenyuanService.selectOne(new EntityWrapper<FuwurenyuanEntity>().eq("mobile", fuwurenyuan.getMobile()));
        if(u!=null) {
            return R.error("手机已被注册");
        }
        //判断验证码是否正确，否则返回错误信息
        List<SmsregistercodeEntity> smsregistercodeList = smsregistercodeService.selectList(new EntityWrapper<SmsregistercodeEntity>().eq("role", "服务人员").eq("mobile", fuwurenyuan.getMobile()).orderBy("addtime", false));
        boolean smsValidate = false;
        if(smsregistercodeList!=null && smsregistercodeList.size()>0) {
            if(smsregistercodeList.get(0).getCode().equals(smscode)) {
                smsValidate = true;
            }
        }
        if(!smsValidate) return R.error("短信验证码不正确");
		Long uId = new Date().getTime();
		fuwurenyuan.setId(uId);
        //保存用户
        fuwurenyuanService.insert(fuwurenyuan);
        return R.ok();
    }


    /**
     * 发送短信
     */
    @IgnoreAuth
    @RequestMapping("/sendsms")
    public R sendsms(@RequestParam String mobile){
        String code = CommonUtil.getRandomNumber(6);
        SmsregistercodeEntity smsregistercode = new SmsregistercodeEntity();
        smsregistercode.setCode(code);
        smsregistercode.setRole("服务人员");
        smsregistercode.setMobile(mobile);
        smsregistercodeService.insert(smsregistercode);
        CommonUtil.sendSMS(mobile, code);
        return R.ok().put("data", code);
    }

    /**
     * 发送登录短信
     */
    @IgnoreAuth
    @RequestMapping("/sendsms/login")
    public R sendsmsForLogin(@RequestParam String mobile){
        FuwurenyuanEntity u =fuwurenyuanService.selectOne(new EntityWrapper<FuwurenyuanEntity>().eq("mobile", mobile));
        if(u==null) return R.error("用户不存在");
        String code = CommonUtil.getRandomNumber(6);
        SmsregistercodeEntity smsregistercode = new SmsregistercodeEntity();
        smsregistercode.setCode(code);
        smsregistercode.setRole("服务人员");
        smsregistercode.setMobile(mobile);
        smsregistercodeService.insert(smsregistercode);
        CommonUtil.sendSMS(mobile, code);
        return R.ok().put("data", code);
    }

    /**
     * 手机号登录
     */
    @IgnoreAuth
    @RequestMapping("/sms/login")
    public R emailLogin(@RequestParam String mobile,@RequestParam(required = false) String smscode){
        FuwurenyuanEntity u =fuwurenyuanService.selectOne(new EntityWrapper<FuwurenyuanEntity>().eq("mobile", mobile));
        if(u==null) return R.error("用户不存在");
        //判断验证码是否正确，否则返回错误信息
        List<SmsregistercodeEntity> smsregistercodeList = smsregistercodeService.selectList(new EntityWrapper<SmsregistercodeEntity>().eq("role", "服务人员").eq("mobile", mobile).orderBy("addtime", false));
        boolean smsValidate = false;
        if(smsregistercodeList!=null && smsregistercodeList.size()>0) {
            if(smsregistercodeList.get(0).getCode().equals(smscode)) {
                smsValidate = true;
            }
        }
        if(!smsValidate) return R.error("短信验证码不正确");
        // 判断用户锁定状态
        if(u!=null && u.getStatus().intValue()==1) {
            //返回已锁定提示
            return R.error("账号已锁定，请联系管理员。").put("username", u.getYuangongzhanghao());
        }
        // 获取登录token
        String token = tokenService.generateToken(u.getId(), mobile,"fuwurenyuan",  "服务人员" );
        // 将token缓存到redis
        redisUtils.set("fuwurenyuan"+mobile, token, 3600);
        //返回token
        return R.ok().put("token", token).put("username", u.getYuangongzhanghao());
    }
	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public R logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return R.ok("退出成功");
	}
	
	/**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        FuwurenyuanEntity u = fuwurenyuanService.selectById(id);
        return R.ok().put("data", u);
    }
    
    /**
     * 密码重置
     */
    @IgnoreAuth
	@RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
        //根据登录账号判断是否存在用户信息，否则返回错误信息
        FuwurenyuanEntity u = fuwurenyuanService.selectOne(new EntityWrapper<FuwurenyuanEntity>().eq("yuangongzhanghao", username));
    	if(u==null) {
    		return R.error("账号不存在");
    	}
        //重置密码为123456
        u.setMima("123456");
        fuwurenyuanService.updateById(u);
        return R.ok("密码已重置为：123456");
    }

    /**
     * 获取账号列表
     */
    @RequestMapping("/accountList")
    public R getAccountList(@RequestParam Map<String, Object> params,FuwurenyuanEntity fuwurenyuan){
        EntityWrapper<FuwurenyuanEntity> ew = new EntityWrapper<FuwurenyuanEntity>();
        Wrapper<FuwurenyuanEntity> wrapper =MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fuwurenyuan), params), params);
        List<Map> list = fuwurenyuanService.selectList(wrapper).stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", v.getId());
            map.put("account", v.getYuangongzhanghao());
            return map;
        }).collect(Collectors.toList());
        return R.ok().put("data", list);
    }



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,FuwurenyuanEntity fuwurenyuan,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<FuwurenyuanEntity> ew = new EntityWrapper<FuwurenyuanEntity>();


        //查询结果
		PageUtils page = fuwurenyuanService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fuwurenyuan), params), params));
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
    public R list(@RequestParam Map<String, Object> params,FuwurenyuanEntity fuwurenyuan, 
                @RequestParam(required = false) Double shouyistart,
                @RequestParam(required = false) Double shouyiend,
                @RequestParam(required = false) Double statusstart,
                @RequestParam(required = false) Double statusend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<FuwurenyuanEntity> ew = new EntityWrapper<FuwurenyuanEntity>();
        if(shouyistart!=null) ew.ge("shouyi", shouyistart);
        if(shouyiend!=null) ew.le("shouyi", shouyiend);
        if(statusstart!=null) ew.ge("status", statusstart);
        if(statusend!=null) ew.le("status", statusend);

        //查询结果
		PageUtils page = fuwurenyuanService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fuwurenyuan), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( FuwurenyuanEntity fuwurenyuan){
       	EntityWrapper<FuwurenyuanEntity> ew = new EntityWrapper<FuwurenyuanEntity>();
      	ew.allEq(MPUtil.allEQMapPre( fuwurenyuan, "fuwurenyuan")); 
        return R.ok().put("data", fuwurenyuanService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(FuwurenyuanEntity fuwurenyuan){
        EntityWrapper< FuwurenyuanEntity> ew = new EntityWrapper< FuwurenyuanEntity>();
 		ew.allEq(MPUtil.allEQMapPre( fuwurenyuan, "fuwurenyuan")); 
		FuwurenyuanView fuwurenyuanView =  fuwurenyuanService.selectView(ew);
		return R.ok("查询服务人员成功").put("data", fuwurenyuanView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        FuwurenyuanEntity fuwurenyuan = fuwurenyuanService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(fuwurenyuan,deSens);
        return R.ok().put("data", fuwurenyuan);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        FuwurenyuanEntity fuwurenyuan = fuwurenyuanService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(fuwurenyuan,deSens);
        return R.ok().put("data", fuwurenyuan);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增服务人员") 
    public R save(@RequestBody FuwurenyuanEntity fuwurenyuan, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(fuwurenyuanService.selectCount(new EntityWrapper<FuwurenyuanEntity>().eq("yuangongzhanghao", fuwurenyuan.getYuangongzhanghao()))>0) {
            return R.error("员工账号已存在");
        }
        //ValidatorUtils.validateEntity(fuwurenyuan);
        //验证账号唯一性，否则返回错误信息
        FuwurenyuanEntity u = fuwurenyuanService.selectOne(new EntityWrapper<FuwurenyuanEntity>().eq("yuangongzhanghao", fuwurenyuan.getYuangongzhanghao()));
        if(u!=null) {
            return R.error("用户已存在");
        }
    	fuwurenyuan.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
		fuwurenyuan.setId(new Date().getTime());
        fuwurenyuanService.insert(fuwurenyuan);
        return R.ok().put("data",fuwurenyuan.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增服务人员")
    @RequestMapping("/add")
    public R add(@RequestBody FuwurenyuanEntity fuwurenyuan, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(fuwurenyuanService.selectCount(new EntityWrapper<FuwurenyuanEntity>().eq("yuangongzhanghao", fuwurenyuan.getYuangongzhanghao()))>0) {
            return R.error("员工账号已存在");
        }
        //ValidatorUtils.validateEntity(fuwurenyuan);
        //验证账号唯一性，否则返回错误信息
        FuwurenyuanEntity u = fuwurenyuanService.selectOne(new EntityWrapper<FuwurenyuanEntity>().eq("yuangongzhanghao", fuwurenyuan.getYuangongzhanghao()));
        if(u!=null) {
            return R.error("用户已存在");
        }
    	fuwurenyuan.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
		fuwurenyuan.setId(new Date().getTime());
        fuwurenyuanService.insert(fuwurenyuan);
        return R.ok().put("data",fuwurenyuan.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改服务人员")
    public R update(@RequestBody FuwurenyuanEntity fuwurenyuan, HttpServletRequest request){
        //ValidatorUtils.validateEntity(fuwurenyuan);
        //验证字段唯一性，否则返回错误信息
        if(fuwurenyuanService.selectCount(new EntityWrapper<FuwurenyuanEntity>().ne("id", fuwurenyuan.getId()).eq("yuangongzhanghao", fuwurenyuan.getYuangongzhanghao()))>0) {
            return R.error("员工账号已存在");
        }
        //全部更新
        fuwurenyuanService.updateById(fuwurenyuan);
        if(null!=fuwurenyuan.getYuangongzhanghao()){
            // 修改token
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setUsername(fuwurenyuan.getYuangongzhanghao());
            tokenService.update(tokenEntity, new EntityWrapper<TokenEntity>().eq("userid", fuwurenyuan.getId()));
        }
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除服务人员")
    public R delete(@RequestBody Long[] ids){
        fuwurenyuanService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    






}
