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
@TableName(value = "store",autoResultMap = true)
public class Store {
    @TableId(type = IdType.AUTO)
    @JsonProperty("storeId")
    private Integer storeId;

    @TableField("sample_id")
    @JsonProperty("sampleId")
    private Integer SampleId;

    @TableField("store_name")
    @JsonProperty("storeName")
    private String storeName;

    @TableField("store_picture")
    @JsonProperty("storePicture")
    private String storePicture;

    @TableField("store_number")
    @JsonProperty("storeNumber")
    private Integer storeNumber;

    @TableField("store_address")
    @JsonProperty("storeAddress")
    private String storeAddress;

    @TableField("drug_id")
    @JsonProperty("drugId")
    private Integer drugId;

    @TableField("drug_name")
    @JsonProperty("drugName")
    private String drugName;
}
