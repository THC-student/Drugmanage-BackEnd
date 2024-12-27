package com.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.DrugMapper;
import com.example.pojo.Drug;
import com.example.service.DrugService;
import com.example.utils.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrugServiceImpl extends ServiceImpl<DrugMapper, Drug> implements DrugService {
    @Autowired
    private DrugMapper drugMapper;
    @Autowired
    private AliOSSUtils aliOSSUtils;
    @Override
    public List<Drug> getDrugList(Integer pageNum) {
        int pageSize=8;
        Page<Drug> page = Page.of(pageNum, pageSize);
        Page<Drug> drugPage = drugMapper.selectPage(page, null);
        List<Drug> records = drugPage.getRecords();
        return records;
    }

    @Override
    public Long getPageCount() {
        Long pageSize=8L;
        Long back=0L;
        Long aLong = drugMapper.selectCount(null);
        if(aLong%pageSize==0){
            back=aLong/pageSize;
        }else{
            back= aLong/pageSize+1;
        }

        return back;
    }

    @Override
    public void insertNewDrug1(Drug drug) {
        drugMapper.insert(drug);
        return ;
    }

    @Override
    public String newPicture(MultipartFile drugPicture1) {
        String s="";
        try {
         s=   aliOSSUtils.upload(drugPicture1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return s;
    }

    @Override
    public void deleteDrugById(Integer drugId) {
        drugMapper.deleteById(drugId);
        return;
    }

    @Override
    public Drug getDrugInfoByDrugId(Integer drugId) {
        Drug drug = drugMapper.selectById(drugId);
        return drug;
    }

    @Override
    public void changeDrugInfo(Drug drug) {
        drugMapper.updateById(drug);
        return ;
    }

    @Override
    public int getDrugPageSum() {
        List<Drug> drugList = drugMapper.selectList(null);
        return drugList.size();
    }

    @Override
    public Map<Integer, Integer> getTypeofDrug() {
        List<Drug> drugList = drugMapper.selectList(null);
        Map<Integer,Integer>back=new HashMap<>();
        for (int i = 0; i < drugList.size(); i++) {
            Drug drug = drugList.get(i);
            back.merge(drug.getDrugType(), drug.getDrugNumber(), Integer::sum);
        }
        return back;
    }

    @Override
    public Map<String, Integer> getNumberOfDrug() {
        List<Drug> drugList = drugMapper.selectList(null);
        Map<String,Integer>back=new HashMap<>();
        for (int i = 0; i < drugList.size(); i++) {
            Drug drug = drugList.get(i);
            back.merge(drug.getDrugName(), drug.getDrugNumber(), Integer::sum);
        }
        return back;
    }
}
