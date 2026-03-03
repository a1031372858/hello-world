package org.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.converter.UserConverter;
import org.example.mapper.UserMapper;
import org.example.model.request.IdRequest;
import org.example.model.po.UserPO;
import org.example.model.to.UserTO;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final UserConverter userConverter;

    private final DataSourceTransactionManager dataSourceTransactionManager;

    private final TransactionDefinition transactionDefinition;

    private final TransactionTemplate transactionTemplate;

    public UserTO userInfo(){
        UserPO userPO = new UserPO();
        userPO.setId(1L);
        userPO.setBirthday(new Date());
        userPO.setName("张三");
        userPO.setMobile("13000000000");
        UserTO userTO = userConverter.po2to(userPO);
        log.info(userTO.toString());
        return userTO;
    }

    public UserTO selectById(IdRequest request){
        UserPO userPO = userMapper.selectById(request.getUserId());
        return userConverter.po2to(userPO);
    }

    public Page<UserTO> selectPage(Page<UserPO> page){
        Page<UserTO> result = new Page<>();
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        Page<UserPO> userPOPage = userMapper.selectPage(page, queryWrapper);
        List<UserTO> userList = new ArrayList<>();
        for (UserPO item : userPOPage.getRecords()) {
            UserTO to = userConverter.po2to(item);
            userList.add(to);
        }
        result.setRecords(userList);
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        return result;
    }

    @Transactional
    public Boolean updateUser(UserPO user){
        UserPO updatePO = new UserPO();
        updatePO.setId(user.getId());
        if(Objects.nonNull(user.getName())){
            updatePO.setName(user.getName());
        }
        if(Objects.nonNull(user.getMobile())){
            updatePO.setMobile(user.getMobile());
        }
        if(Objects.nonNull(user.getBirthday())){
            updatePO.setBirthday(user.getBirthday());
        }
        int i = userMapper.updateById(updatePO);
        if(i>0){
            throw new RuntimeException("测试事务");
        }
        return true;
    }

    /**
     * 使用TransactionManager实现事务提交和回滚
     * @param user
     * @return
     */
    public Boolean updateUserByTransactionManager(UserPO user){
        UserPO updatePO = new UserPO();
        updatePO.setId(user.getId());
        if(Objects.nonNull(user.getName())){
            updatePO.setName(user.getName());
        }
        if(Objects.nonNull(user.getMobile())){
            updatePO.setMobile(user.getMobile());
        }
        if(Objects.nonNull(user.getBirthday())){
            updatePO.setBirthday(user.getBirthday());
        }
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        int i = userMapper.updateById(updatePO);
        if(i>0){
            dataSourceTransactionManager.rollback(transaction);
            throw new RuntimeException("测试事务");
        }
        dataSourceTransactionManager.commit(transaction);
        return true;
    }

    /**
     * 使用TransactionTemplate实现事务提交和回滚
     * @param user
     * @return
     */
    public Boolean updateUserByTransactionTemplate(UserPO user){
        UserPO updatePO = new UserPO();
        updatePO.setId(user.getId());
        if(Objects.nonNull(user.getName())){
            updatePO.setName(user.getName());
        }
        if(Objects.nonNull(user.getMobile())){
            updatePO.setMobile(user.getMobile());
        }
        if(Objects.nonNull(user.getBirthday())){
            updatePO.setBirthday(user.getBirthday());
        }
        transactionTemplate.execute(status->{
            int i = userMapper.updateById(updatePO);
            if(i>0){
                throw new RuntimeException("测试事务");
            }
            return null;
        });
        return true;
    }
}
