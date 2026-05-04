package com.utils;

import java.lang.reflect.Method;
import java.util.*;
import java.io.*;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dypnsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dypnsapi20170525.models.SendSmsVerifyCodeRequest;
import com.aliyun.sdk.service.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import darabonba.core.client.ClientOverrideConfiguration;
import java.util.concurrent.CompletableFuture;
import com.google.gson.Gson;

@Component
public class CommonUtil {
	/**
     * 获取随机字符串
     *
     * @param num
     * @return
     */
    public static String getRandomString(Integer num) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

	/**
	 * 获取随机验证码
	 *
	 * @param num
	 * @return
	 */
	public static String getRandomNumber(Integer num) {
	    String base = "0123456789";
	    Random random = new Random();
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < num; i++) {
	        int number = random.nextInt(base.length());
	        sb.append(base.charAt(number));
	    }
	    return sb.toString();
	}

    /**
    * 极简导入：自动映射为 Bean 列表
    *
    * @param file      上传的 Excel 文件
    * @param beanClass 目标实体类
    * @param <T>       泛型类型
    * @return 实体对象列表
    * @throws IOException 文件读取异常
    */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> beanClass) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             ExcelReader reader = ExcelUtil.getReader(inputStream)) {

            // 一行代码完成读取和转换，自动处理单元格类型
            return reader.readAll(beanClass);
        }
    }

    /**
     * 带表头别名配置的导入（当 Excel 表头与字段名不一致时使用）
     */
    public static <T> List<T> importExcelWithAlias(MultipartFile file, Class<T> beanClass,
                                                   Map<String, String> headerAlias) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             ExcelReader reader = ExcelUtil.getReader(inputStream)) {

            // 配置表头别名
            headerAlias.forEach(reader::addHeaderAlias);

            return reader.readAll(beanClass);
        }
    }

    /**
     * 读取为 Map 列表（保留原始表头作为 key）
     */
    public static List<Map<String, Object>> importExcelAsMap(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             ExcelReader reader = ExcelUtil.getReader(inputStream)) {

            return reader.readAll();
        }
    }

    /**
     * 检查实体中是否存在数组中的属性字段，并返回第一个匹配的属性的get方法的结果
     * @param entity 实体对象
     * @param fieldNames 属性字段名称数组
     * @return 第一个匹配的属性的get方法的结果，如果没有匹配则返回null
     */
    public static Object getPropertyByFieldNames(Object entity, String... fieldNames) {
        if (entity == null || fieldNames == null) {
            return null;
        }
        Class<?> clazz = entity.getClass();
        for (String fieldName : fieldNames) {
            try {
                // 构造get方法名
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method method = clazz.getMethod(methodName);
                // 调用get方法并返回结果
                return method.invoke(entity);
            } catch (NoSuchMethodException e) {
                // get方法不存在，继续下一个字段
                continue;
            } catch (Exception e) {
                // 其他异常，打印堆栈信息
                e.printStackTrace();
                return null;
            }
        }
        // 没有找到匹配的字段
        return null;
    }

    /**
     * 解析请求参数
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Map<String, Object> parseRequest(HttpServletRequest request){
        Map<String, Object> paramMap = new HashMap<>();

        // 先尝试从参数映射中获取（适用于 GET 请求和已解析的 POST 请求）
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().length > 0) {
                paramMap.put(entry.getKey(), entry.getValue()[0]);
            }
        }

        // 如果参数映射为空且是 POST 请求，尝试从请求体读取
        if (paramMap.isEmpty() && "POST".equalsIgnoreCase(request.getMethod())) {
            try {
                BufferedReader reader = request.getReader();
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                if (sb.length() > 0) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> jsonMap = (new ObjectMapper()).readValue(sb.toString(), Map.class);
                    paramMap.putAll(jsonMap);
                }
            } catch (Exception e) {
                // 如果输入流已被读取，则使用已有的参数映射
                // 这种情况通常发生在过滤器已经读取过请求体时
            }
        }

        return paramMap;
    }


    public static boolean sendSMS(String phone,String key){
        // 请使用自己的accessKeyId和accessKeySecret，建议在config表中修改。
        String accessKeyId = ConfigUtil.getConfigValue("aliyun", "accessKeyId");
        System.out.println(accessKeyId);
        String accessKeySecret = ConfigUtil.getConfigValue("aliyun", "accessKeySecret");
        System.out.println(accessKeySecret);
        if(StringUtils.isBlank(accessKeyId) || StringUtils.isBlank(accessKeySecret)) {
            System.out.println("短信未真实发送：未配置阿里云accessKeyId/accessKeySecret，本地验证码为 " + key);
            return false;
        }
        AsyncClient client = null;
        try {
            StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder().accessKeyId(accessKeyId).accessKeySecret(accessKeySecret).build());

            client = AsyncClient.builder().region("cn-shenzhen").credentialsProvider(provider).overrideConfiguration(ClientOverrideConfiguration.create().setEndpointOverride("dypnsapi.aliyuncs.com")).build();
            // 短信签名,如果是使用阿里云的测试模板
            String signName = "速通互联验证码";
            //模板Code,如果是使用阿里云的测试模板
            String templateCode = "100001";
            String templateParam = "{\"code\":\"" + key + "\",\"min\":\"5\"}";
            SendSmsVerifyCodeRequest sendSmsVerifyCodeRequest = SendSmsVerifyCodeRequest.builder().signName(signName).templateCode(templateCode).phoneNumber(phone).returnVerifyCode(true).templateParam(templateParam).build();

            CompletableFuture<SendSmsVerifyCodeResponse> response = client.sendSmsVerifyCode(sendSmsVerifyCodeRequest);

            SendSmsVerifyCodeResponse resp = response.get();
            System.out.println(new Gson().toJson(resp.getBody()));
            return true;
        } catch (Exception e) {
            System.err.println("短信发送失败：" + e.getMessage());
            return false;
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }



}
