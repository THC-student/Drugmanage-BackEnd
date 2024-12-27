package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pojo.Drug;
import com.example.pojo.Result;
import com.example.pojo.Store;
import com.example.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@Slf4j
@RequestMapping("/storeController")
@RestController
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping("/getStoreList")
    private Result getDrugList(@RequestParam Integer pageNum){
        return Result.success(null);
    }
    @GetMapping("/getPageCount")
    private Result getPageCount(){
        return Result.success(null);
    }
    @PostMapping("/insertNewSTore")
    private Result insertNewDrug1(@RequestBody Store store){
        return Result.success("添加成功");
    }

    @GetMapping("/getAllSampleId")
    private Result getAllSampleId(){
        HashSet<Integer> integers = storeService.storeService();
        Object[] array = integers.toArray();
        return Result.success(array);
    }

    @GetMapping("/StorePage")
    private  Result StorePage(@RequestParam Integer pageNum,@RequestParam Integer sampleId){
        List < Store> back=storeService.StorePage(pageNum,sampleId);
        return Result.success(back);
    }

    @GetMapping("/StorePageCount")
    private Result StorePageCount(@RequestParam Integer sampleId){
        int size=storeService.StorePageCount(sampleId);
        return Result.success(size);
    }

    @GetMapping("/GetStoreInfoById")
    private Result GetStoreInfoById(@RequestParam Integer storeId){
        Store store=storeService.GetStoreInfoById(storeId);
        return Result.success(store);
    }

    @GetMapping("/deleteStoreById")
    private Result deleteStoreById(@RequestParam Integer storeId){
        storeService.deleteStoreById(storeId);
        return Result.success();
    }
    @PostMapping("/changeStore")
    private Result changeStore(@RequestBody Store store){
        storeService.changeStore(store);
        return Result.success("修改成功");
    }
}
