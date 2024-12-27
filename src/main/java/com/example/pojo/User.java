package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user",autoResultMap = true)
public class User {
    @TableId(type = IdType.AUTO)
    @JsonProperty("userId")
    private Integer userId;

    @TableField("user_name")
    @JsonProperty("userName")
    private String userName;

    @TableField("user_password")
    @JsonProperty("userPassword")
    private String userPassword;

    @TableField("user_header")
    @JsonProperty("userHeader")
    private String userHeader;

    @TableField("user_count")
    @JsonProperty("userCount")
    private String userCount;

    @TableField("user_judge")
    @JsonProperty("userJudge")
    private Integer userJudge;

}
