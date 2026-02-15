package org.example.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
public class UserPO {

    @TableId
    private Long id;

    private String name;

    private String mobile;

    private Date birthday;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;
}
