package org.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.model.po.UserPO;

/**
 * @author xuyachang
 * @date 2026/2/10
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {

}
