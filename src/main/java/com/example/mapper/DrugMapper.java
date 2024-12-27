package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.Drug;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DrugMapper  extends BaseMapper<Drug> {
}
