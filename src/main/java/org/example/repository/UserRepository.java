package org.example.repository;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.model.po.UserPO;
import org.springframework.stereotype.Repository;

/**
 * @author xuyachang
 * @date 2025/12/2
 */
@Slf4j
@Repository
public class UserRepository {

    public void saveUser(UserPO userPO){
        log.info("保存用户至数据库，user={}", JSON.toJSONString(userPO));
    }
}
