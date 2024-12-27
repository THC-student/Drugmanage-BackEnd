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
@TableName(value = "drug",autoResultMap = true)
public class Drug {
    @TableId(type = IdType.AUTO)
    @JsonProperty("drugId")
    private Integer drugId;

    @TableField("drug_name")
    @JsonProperty("drugName")
    private String drugName;

    @TableField("drug_picture")
    @JsonProperty("drugPicture")
    private String drugPicture;

    @TableField("drug_number")
    @JsonProperty("drugNumber")
    private Integer drugNumber;

    @TableField("drug_type")
    @JsonProperty("drugType")
    private Integer drugType;

    @TableField("drug_mode")
    @JsonProperty("drugMode")
    private Integer drugMode;

    @TableField("drug_components")
    @JsonProperty("drugComponents")
    private String drugComponents;

}
