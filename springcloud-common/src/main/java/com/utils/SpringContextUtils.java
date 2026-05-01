
package com.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * Spring Context 工具类
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
	public static ApplicationContext applicationContext; 
	 
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}

	public static Class<? extends Object> getType(String name) {
		return applicationContext.getType(name);
	}

	public static Object getBeanMethod(String beanName, String methodName, Class<?>[] parameterTypes, Object[] args) throws Exception {
		return applicationContext.getBean(beanName).getClass().getMethod(methodName,parameterTypes).invoke(applicationContext.getBean(beanName), args);
	}

	public static boolean hasProperty(String className, String propertyName) {
		try {
			// 尝试直接通过类名加载类
			Class<?> clazz = Class.forName(className);
			clazz.getDeclaredField(propertyName);
			return true;
		} catch (ClassNotFoundException e) {
			// 如果类不存在，尝试通过Spring上下文获取Bean
			try {
				Object bean = applicationContext.getBean(className);
				if (bean != null) {
					Class<?> clazzFromBean = bean.getClass();
					clazzFromBean.getDeclaredField(propertyName);
					return true;
				}
			} catch (Exception ex) {
				// 忽略异常，返回false
			}
			return false;
		} catch (NoSuchFieldException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

}