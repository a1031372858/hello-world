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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final UserConverter userConverter;

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
}
