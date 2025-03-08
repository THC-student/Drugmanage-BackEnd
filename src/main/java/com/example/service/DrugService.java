package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Drug;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface DrugService extends IService<Drug> {
    List<Drug> getDrugList(Integer pageNum);

    Long getPageCount();

    void insertNewDrug1(Drug drug);


    String newPicture(MultipartFile drugPicture1);

    void deleteDrugById(Integer drugId);

    Drug getDrugInfoByDrugId(Integer drugId);

    void changeDrugInfo(Drug drug);

    int getDrugPageSum();

    Map<Integer, Integer> getTypeofDrug();

    Map<String, Integer> getNumberOfDrug();

    List<Drug> getDrugByName(String drugName);
}
