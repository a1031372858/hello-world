package org.example.model.to;

import org.example.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author xuyachang
 * @date 2025/5/8
 */


@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof UserService){
            System.out.println(bean.getClass().getName()+"执行了初始化的前置方法");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof UserService){
            System.out.println(bean.getClass().getName()+"执行了初始化的后置方法");
        }
        return bean;
    }
}
