package org.example.converter;

import org.example.model.po.UserPO;
import org.example.model.to.UserTO;
import org.mapstruct.Mapper;

/**
 * @author xuyachang
 * @date 2024/2/11
 */
@Mapper(componentModel = "spring")
public abstract class UserConverter extends BaseConverter<UserTO, UserPO> {
}
