package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.converter.UserConverter;
import org.example.model.po.UserPO;
import org.example.model.to.UserTO;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author xuyachang
 * @date 2025/12/2
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getName()+"执行初始化方法");
    }

    private final TransactionTemplate transactionTemplate;


    private final UserRepository userRepository;

    private final UserConverter userConverter;

    @Transactional
    public void saveUser(UserTO userTO){
        UserPO userPO = userConverter.convertP(userTO);
        userRepository.saveUser(userPO);
    }

    public void updateUser(UserTO userTO){
        UserPO userPO = userConverter.convertP(userTO);
        transactionTemplate.<Boolean>execute(o->{
            try{
                userRepository.saveUser(userPO);
            }catch (Exception e){
                o.setRollbackOnly();
                log.error("更新用户失败");
            }
           return Boolean.TRUE;
        });
    }


    @PreDestroy
    public void preDestroy(){
        System.out.println(this.getClass().getName()+"销毁bean");
    }
}
