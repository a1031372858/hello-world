package org.example.model.to;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author xuyachang
 * @date 2025/12/2
 */
public class UserTO {

    public UserTO(){
        System.out.println(this.getClass().getName()+"实例化");
    }

    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getName()+"执行初始化方法");
    }


    @PreDestroy
    public void preDestroy(){
        System.out.println(this.getClass().getName()+"销毁bean");
    }
}
