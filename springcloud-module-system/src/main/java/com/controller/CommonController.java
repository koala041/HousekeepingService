package com.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import com.hankcs.hanlp.HanLP;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.JSONArray;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.IgnoreAuth;
import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.baidu.aip.util.Base64Util;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.ConfigEntity;
import com.service.CommonService;
import com.service.ConfigService;
import com.utils.*;
import org.apache.commons.io.IOUtils;
import com.llm.MultiModelAIClient;
import org.springframework.http.MediaType;
import com.service.ChatService;
import com.entity.ChatEntity;

/**
 * 通用接口
 * @author GG Bond
 */
@RestController
public class CommonController{
	@Autowired
	private CommonService commonService;

    private static AipFace client = null;
    
    @Autowired
    private ConfigService configService;

	@Autowired
	private ChatService chatService;
	/**
	 * 获取table表中的column列表(联动接口)
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping("/option/{tableName}/{columnName}")
	public R getOption(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName,@RequestParam(required = false) String conditionColumn,@RequestParam(required = false) String conditionValue,String level,String parent, HttpServletRequest request)throws IOException  {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table", tableName);
		params.put("column", columnName);
		if(StringUtils.isNotBlank(level)) {
			params.put("level", level);
		}
		if(StringUtils.isNotBlank(parent)) {
			params.put("parent", parent);
		}
        if(StringUtils.isNotBlank(conditionColumn)) {
            params.put("conditionColumn", conditionColumn);
        }
        if(StringUtils.isNotBlank(conditionValue)) {
            params.put("conditionValue", conditionValue);
        }
		params.putAll(CommonUtil.parseRequest(request));
		List<String> data = commonService.getOption(params);
		return R.ok().put("data", data);
	}
	
	/**
	 * 根据table中的column获取单条记录
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping("/follow/{tableName}/{columnName}")
	public R getFollowByOption(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName, @RequestParam String columnValue) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table", tableName);
		params.put("column", columnName);
		params.put("columnValue", columnValue);
		Map<String, Object> result = commonService.getFollowByOption(params);
        Object o = null;
        try {
            Class<?> c1 = Class.forName("com.entity."+tableName.substring(0, 1).toUpperCase()+tableName.substring(1)+"Entity");
            o = MapUtils.mapToObject(result, c1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok().put("data", o);
	}
	
	/**
	 * 修改table表的sfsh状态
	 * @param tableName
	 * @param map
	 * @return
	 */
	@RequestMapping("/sh/{tableName}")
	public R sh(@PathVariable("tableName") String tableName, @RequestBody Map<String, Object> map) {
		map.put("table", tableName);
		commonService.sh(map);
		return R.ok();
	}
	
	/**
	 * 获取需要提醒的记录数
	 * @param tableName
	 * @param columnName
	 * @param type 1:数字 2:日期
	 * @param map
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping("/remind/{tableName}/{columnName}/{type}")
	public R remindCount(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("table", tableName);
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		int count = commonService.remindCount(map);
		return R.ok().put("count", count);
	}
	
	/**
	 * 单列求和
	 */
	@IgnoreAuth
	@RequestMapping("/cal/{tableName}/{columnName}")
	public R cal(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table", tableName);
		params.put("column", columnName);
		Map<String, Object> result = commonService.selectCal(params);
		return R.ok().put("data", result);
	}
	
	/**
	 * 分组统计
	 */
	@IgnoreAuth
	@RequestMapping("/group/{tableName}/{columnName}")
	public R group(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table", tableName);
		params.put("column", columnName);
		List<Map<String, Object>> result = commonService.selectGroup(params);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(Map<String, Object> m : result) {
			for(String k : m.keySet()) {
				if(m.get(k) instanceof Date) {
					m.put(k, sdf.format((Date)m.get(k)));
				}
			}
		}
		return R.ok().put("data", result);
	}

	/**
	 * 词云数据
	 * @param tableName
	 * @param columnName
	 */
	@IgnoreAuth
	@RequestMapping("/wordCloud/{tableName}/{columnName}")
	public R wordCloud(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName) {
		if (tableName == null || tableName.isEmpty() || columnName == null || columnName.isEmpty()) {
			return R.error("Table name and column name cannot be empty");
		}

		Map<String, Object> params = new HashMap<>();
		params.put("table", tableName);
		params.put("column", columnName);
		List<Map<String, Object>> result = new ArrayList<>();
		List<String> listText = commonService.selectGroup(params).stream().map(m -> m.get(columnName).toString()).collect(Collectors.toList());

		Map<String, Integer> keywordCounts = new HashMap<>();
		for (String text : listText) {
			List<String> phrases = HanLP.extractPhrase(text, 3);
			List<String> keywords = HanLP.extractKeyword(text, 3);

			// 合并两种结果
			Set<String> uniqueTerms = new HashSet<>();
			uniqueTerms.addAll(phrases);
			uniqueTerms.addAll(keywords);

			for (String term : uniqueTerms) {
				if (term.length() > 1) {
					keywordCounts.put(term, keywordCounts.getOrDefault(term, 0) + 1);
				}
			}
		}

		keywordCounts.forEach((key, value) -> {
			Map<String, Object> item = new HashMap<>();
			item.put(columnName, key);
			item.put("total", value);
			result.add(item);
		});

		return R.ok().put("data", result);
	}

	/**
	 * 更新列数据
	 *
	 * @param tableName 表名
	 * @param type      1 字符串，直接替换。 2 数字运算，+ - * /。
	 */
	@RequestMapping("/updateColumn/{tableName}/{type}")
	public R updateColumn(@PathVariable("tableName") String tableName, @PathVariable("type") int type, HttpServletRequest request) throws IOException {
		Map<String, Object> params = new HashMap<>();
		params.put("tableName", tableName);
		params.put("type", type);
		params.putAll(CommonUtil.parseRequest(request));
		if (type == 1) {
			Map<String, Object> params1 = new HashMap<>();
			params1.put("table", tableName);
			params1.put("column", params.get("csuConditionColumn"));
			params1.put("columnValue", params.get("csuConditionColumnValue"));
			Map<String, Object> res = commonService.getFollowByOption(params1);
			if (res != null && (res.get(params.get("csuUpdateColumn").toString())+"").equals(params.get("csuUpdateColumnValue").toString())) {
				return R.error("更新失败");
			}
		}
		if (commonService.updateColumn(params)) {
			return R.ok("更新成功");
		}
		return R.error("库存不足");
	}

	/**
     * 删除列数据
     *
     * @param tableName 表名
     */
	@RequestMapping("/deleteColumn/{tableName}")
	public R deleteColumn(@PathVariable("tableName") String tableName, HttpServletRequest request) throws IOException {
		Map<String, Object> params = new HashMap<>();
		params.put("tableName", tableName);
		params.putAll(CommonUtil.parseRequest(request));
		if (commonService.deleteColumn(params)) {
			return R.ok("删除成功");
		}
		return R.error("删除失败");
	}

	/**
	 * 评论列表
	 */
	@RequestMapping("/comment/list")
	public R comment(HttpServletRequest request) throws Exception {
		List result = new ArrayList<>();
		List<String> tableNames = new ArrayList<>();
		tableNames.add("discussfuwuxinxi");
		for (String tableName : tableNames) {
			EntityWrapper wrapper = new EntityWrapper();
			wrapper.setSqlSelect("*,'"+tableName.replace("discuss","")+"' AS tablename");
			if (!request.getSession().getAttribute("role").toString().equals("管理员")) {
				wrapper.eq("userid", request.getSession().getAttribute("userId"));
			}
			List list = (List) SpringContextUtils.getBeanMethod(tableName + "Service", "selectMaps", new Class[]{Wrapper.class}, new Object[]{wrapper});
			if (!list.isEmpty()) {
				result.addAll( list);
			}
		}
		return R.ok().put("data", result);
	}
	
	/**
	 * （按值统计）
	 */
	@IgnoreAuth
	@RequestMapping("/value/{tableName}/{xColumnName}/{yColumnName}")
	public R value(@PathVariable("tableName") String tableName, @PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table", tableName);
		params.put("xColumn", xColumnName);
		params.put("yColumn", yColumnName);
		List<Map<String, Object>> result = commonService.selectValue(params);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(Map<String, Object> m : result) {
			for(String k : m.keySet()) {
				if(m.get(k) instanceof Date) {
					m.put(k, sdf.format((Date)m.get(k)));
				}
			}
		}
		return R.ok().put("data", result);
	}

	/**
 	 * （按值统计）时间统计类型
	 */
	@IgnoreAuth
	@RequestMapping("/value/{tableName}/{xColumnName}/{yColumnName}/{timeStatType}")
	public R valueDay(@PathVariable("tableName") String tableName, @PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table", tableName);
		params.put("xColumn", xColumnName);
		params.put("yColumn", yColumnName);
		params.put("timeStatType", timeStatType);
		List<Map<String, Object>> result = commonService.selectTimeStatValue(params);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(Map<String, Object> m : result) {
			for(String k : m.keySet()) {
				if(m.get(k) instanceof Date) {
					m.put(k, sdf.format((Date)m.get(k)));
				}
			}
		}
		return R.ok().put("data", result);
	}
	


    /**
    * MySQL数据库导出
    */
	@RequestMapping("/mysqldump")
	public void exportDatabaseTool(HttpServletResponse response) {
		try {
			// 直接备份整个 MySQL，不需要知道库名
			String cmd = "\"D:\\MySQL Server 8.0\\bin\\mysqldump\" -h127.0.0.1 -P3306 -uroot -plyk23430852 --all-databases";

			Process process = Runtime.getRuntime().exec(cmd);

			// 处理错误流，防止卡死
			new Thread(() -> {
				try (InputStream errorStream = process.getErrorStream()) {
					byte[] buf = new byte[1024];
					while (errorStream.read(buf) != -1) {}
				} catch (IOException e) {}
			}).start();

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try (InputStream inputStream = process.getInputStream()) {
				byte[] buffer = new byte[1024 * 4];
				int n;
				while ((n = inputStream.read(buffer)) != -1) {
					output.write(buffer, 0, n);
				}
			}

			process.waitFor();

			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=all_database_backup.sql");
			response.setContentType("application/octet-stream; charset=UTF-8");
			IOUtils.write(output.toByteArray(), response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@IgnoreAuth
	@RequestMapping(value = "/{model}/askai/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public void streamChat(@PathVariable("model") String model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/event-stream;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");

		MultiModelAIClient.ChatRequest chatRequest = new MultiModelAIClient.ChatRequest();
		Map<String, Object> params = CommonUtil.parseRequest(request);
		// 创建历史消息列表
		List<MultiModelAIClient.Message> history = new ArrayList<>();
		Object userId = request.getSession().getAttribute("userId");
		if (null != userId && userId.toString().length() > 0) {
			List<ChatEntity> chatList = chatService.selectList(new EntityWrapper<ChatEntity>().eq("userid", userId).orderBy("addtime", true));
			chatList.stream().forEach(chat -> {
				if (StringUtils.isNotBlank(chat.getAsk())) {
					history.add(new MultiModelAIClient.Message("user", chat.getAsk()));
				}
				if (StringUtils.isNotBlank(chat.getReply())) {
					history.add(new MultiModelAIClient.Message("assistant", chat.getReply()));
				}
			});
			if (!history.isEmpty()) {
				chatRequest.setHistory(history);
			}
		}
		chatRequest.setModel(model);
		if (params.containsKey("ask") && params.get("ask").toString().length() > 0) {
			chatRequest.setMessage(params.get("ask").toString());
		}
		if (params.containsKey("fileNames") && params.get("fileNames").toString().length() > 0) {
			chatRequest.setFileNames(params.get("fileNames").toString().replaceAll("upload/", ""));
		}
		if (params.containsKey("systemPrompt") && params.get("systemPrompt").toString().length() > 0) {
			chatRequest.setSystemPrompt(params.get("systemPrompt").toString());
		}
		// 直接使用 HttpServletResponse 发送 SSE 数据
		MultiModelAIClient.streamChatDirect(chatRequest, response);
	}

	@IgnoreAuth
	@RequestMapping(value = "/{model}/askai")
	public Object chat(@PathVariable("model") String model, HttpServletRequest request) throws Exception {
		MultiModelAIClient.ChatRequest chatRequest = new MultiModelAIClient.ChatRequest();
		Map<String, Object> params = CommonUtil.parseRequest(request);
		// 创建历史消息列表
		List<MultiModelAIClient.Message> history = new ArrayList<>();
		Object userId = request.getSession().getAttribute("userId");
		if (null != userId && userId.toString().length() > 0) {
			List<ChatEntity> chatList = chatService.selectList(new EntityWrapper<ChatEntity>().eq("userid", userId).orderBy("addtime", true));
			chatList.stream().forEach(chat -> {
				if (StringUtils.isNotBlank(chat.getAsk())) {
					history.add(new MultiModelAIClient.Message("user", chat.getAsk()));
				}
				if (StringUtils.isNotBlank(chat.getReply())) {
					history.add(new MultiModelAIClient.Message("assistant", chat.getReply()));
				}
			});
			if (!history.isEmpty()) {
				chatRequest.setHistory(history);
			}
		}
		chatRequest.setModel(model);
		if (params.containsKey("ask") && params.get("ask").toString().length() > 0) {
			chatRequest.setMessage(params.get("ask").toString());
		}
		if (params.containsKey("fileNames") && params.get("fileNames").toString().length() > 0) {
			chatRequest.setFileNames(params.get("fileNames").toString().replaceAll("upload/", ""));
		}
		if (params.containsKey("systemPrompt") && params.get("systemPrompt").toString().length() > 0) {
			chatRequest.setSystemPrompt(params.get("systemPrompt").toString());
		}
		return R.ok().put("data", MultiModelAIClient.chat(chatRequest));
	}

	/*
	 * 百度百科
	 */
	@RequestMapping("/baike/{name}")
	@IgnoreAuth
	public R baike(@PathVariable("name") String name) {
		String url = "https://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_key=%s&bk_length=800";
		String response = HttpClientUtils.doGet(String.format(url, name));

		String content = new JSONObject(response).getString("abstract");
		return R.ok().put("data", content);
	}
}