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

import com.entity.UsersEntity;
import com.entity.view.UsersView;

import com.service.UsersService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
/**
 * 管理员
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-28 09:32:59
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;




    
	@Autowired
	private TokenService tokenService;

	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
        System.out.println("管理员信息：" + username);
        System.out.println("管理员信息：" + password);
        // 根据登录查询用户信息
        UsersEntity u = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", username));
        if(u==null || !u.getPassword().equals(password)) {
            //账号或密码不正确提示
			return R.error("账号或密码不正确");
		}
        // 获取登录token
		String token = tokenService.generateToken(u.getId(), username,"users",  "管理员" );
        //返回token
		return R.ok().put("token", token);
	}


	
	/**
     * 注册
     */
	@IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody UsersEntity users){
    	//ValidatorUtils.validateEntity(users);
        //根据登录账号获取用户信息判断是否存在该用户，否则返回错误信息
    	UsersEntity u = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", users.getUsername()));
        if(u!=null) {
			return R.error("注册用户已存在");
		}
		Long uId = System.currentTimeMillis();
		users.setId(uId);
        users.setPassword(users.getPassword());
        //保存用户
        usersService.insert(users);
        return R.ok();
    }

    /**
    * 注销账户
    */
    @RequestMapping("/logoff")
    public R logOff(HttpServletRequest request){
        Long id = (Long)request.getSession().getAttribute("userId");
        UsersEntity u = usersService.selectById(id);
        if(u==null) {
            return R.error("账号不存在");
        }
        usersService.updateById(u);
        return R.ok("注销成功");
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
        UsersEntity u = usersService.selectById(id);
        return R.ok().put("data", u);
    }
    
    /**
     * 密码重置
     */
    @IgnoreAuth
	@RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
        //根据登录账号判断是否存在用户信息，否则返回错误信息
        UsersEntity u = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", username));
    	if(u==null) {
    		return R.error("账号不存在");
    	}
        u.setPassword("123456");
        usersService.updateById(u);
        return R.ok("密码已重置为：123456");
    }

    /**
     * 获取账号列表
     */
    @RequestMapping("/accountList")
    public R getAccountList(@RequestParam Map<String, Object> params,UsersEntity users){
        EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();
        Wrapper<UsersEntity> wrapper =MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, users), params), params);
        List<Map> list = usersService.selectList(wrapper).stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", v.getId());
            map.put("account", v.getUsername());
            return map;
        }).collect(Collectors.toList());
        return R.ok().put("data", list);
    }



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,UsersEntity users,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();


        //查询结果
		PageUtils page = usersService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, users), params), params));
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
    public R list(@RequestParam Map<String, Object> params,UsersEntity users, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();

        //查询结果
		PageUtils page = usersService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, users), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( UsersEntity users){
       	EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();
      	ew.allEq(MPUtil.allEQMapPre( users, "users")); 
        return R.ok().put("data", usersService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(UsersEntity users){
        EntityWrapper< UsersEntity> ew = new EntityWrapper< UsersEntity>();
 		ew.allEq(MPUtil.allEQMapPre( users, "users")); 
		UsersView usersView =  usersService.selectView(ew);
		return R.ok("查询管理员成功").put("data", usersView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        UsersEntity users = usersService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(users,deSens);
        return R.ok().put("data", users);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        UsersEntity users = usersService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(users,deSens);
        return R.ok().put("data", users);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增管理员") 
    public R save(@RequestBody UsersEntity users, HttpServletRequest request){
        //ValidatorUtils.validateEntity(users);
        //验证账号唯一性，否则返回错误信息
        UsersEntity u = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", users.getUsername()));
        if(u!=null) {
            return R.error("用户已存在");
        }
		users.setId(System.currentTimeMillis());
        usersService.insert(users);
        return R.ok().put("data",users.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增管理员")
    @RequestMapping("/add")
    public R add(@RequestBody UsersEntity users, HttpServletRequest request){
        //ValidatorUtils.validateEntity(users);
        //验证账号唯一性，否则返回错误信息
        UsersEntity u = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", users.getUsername()));
        if(u!=null) {
            return R.error("用户已存在");
        }
		users.setId(new Date().getTime());
        usersService.insert(users);
        return R.ok().put("data",users.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        UsersEntity users = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", username));
        return R.ok().put("data", users);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody UsersEntity users, HttpServletRequest request){
        //ValidatorUtils.validateEntity(users);
        //全部更新
        usersService.updateById(users);
        if(null!=users.getUsername()){
            // 修改token
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setUsername(users.getUsername());
            tokenService.update(tokenEntity, new EntityWrapper<TokenEntity>().eq("userid", users.getId()));
        }
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除管理员")
    public R delete(@RequestBody Long[] ids){
        usersService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,UsersEntity users, HttpServletRequest request,String pre){
        EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();
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
		PageUtils page = usersService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, users), params), params));
        return R.ok().put("data", page);
    }






}
