package com.example.controller;

import com.example.pojo.Drug;
import com.example.pojo.Result;
import com.example.service.DrugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/drugController")
@RestController
public class DrugController {
    @Autowired
    private DrugService drugService;

    @GetMapping("/test")
    private Result test( ){

        return Result.success(1);
    }

    @GetMapping("/getDrugList")
    private Result getDrugList(@RequestParam Integer pageNum){
        List<Drug> drugList=drugService.getDrugList(pageNum);
        return Result.success(drugList);
    }

    @GetMapping("/getPageCount")
    private Result getPageCount(){
        Long pageCount= drugService.getPageCount();
        return Result.success(pageCount);
    }

    @PostMapping("/insertNewDrug")
    private Result insertNewDrug1(@RequestBody Drug drug){
        drugService.insertNewDrug1(drug);
        return Result.success("添加成功");
    }
    @PostMapping("/newPicture")
    private Result newPicture(@RequestParam MultipartFile[] DrugPicture1){
        MultipartFile use=DrugPicture1[0];
        String DrugPicture= drugService.newPicture(use);
        return  Result.success(DrugPicture);
    }

    @GetMapping("/deleteDrugById")
    private  Result deleteDrugById(@RequestParam Integer drugId){
        drugService.deleteDrugById(drugId);
        return Result.success("删除成功");
    }

    @GetMapping("/getDrugInfoByDrugId")
    private Result getDrugInfoByDrugId(@RequestParam Integer drugId){
        Drug drug=drugService.getDrugInfoByDrugId(drugId);
        return Result.success(drug);
    }

    @PostMapping("/changeDrugInfo")
    private Result changeDrugInfo(@RequestBody Drug drug){
        drugService.changeDrugInfo(drug);
        return Result.success();
    }
    @GetMapping("/getDrugPageSum")
    private Result getDrugPageSum(){
        int pageSum= drugService.getDrugPageSum();
        return Result.success(pageSum);
    }

    @GetMapping("/getTypeofDrug")
    private Result getTypeofDrug(){
        Map<Integer,Integer> back= drugService.getTypeofDrug();
        return Result.success(back);
    }
    @GetMapping("/getNumberOfDrug")
    private Result getNumberOfDrug(){
        Map<String,Integer> back= drugService.getNumberOfDrug();
        return Result.success(back);
    }
}

