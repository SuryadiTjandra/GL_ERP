package ags.goldenlionerp.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * A utility class to retrieve a spring-managed bean
 * @author user
 *
 */
@Component
public class BeanFinder implements ApplicationContextAware {

	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	/**
	 * Retrieves a spring-managed bean of a class. 
	 * Throws exceptions if there are more than one bean with the class
	 * @param beanClass - the class of a bean
	 * @return the only bean of that type
	 */
	public static <T> T findBean(Class<T> beanClass) {
		return (T) context.getBean(beanClass);
	}

}
