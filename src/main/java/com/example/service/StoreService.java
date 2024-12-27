package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Store;

import java.util.HashSet;
import java.util.List;

public interface StoreService extends IService<Store> {
    HashSet<Integer> storeService();

    List<Store> StorePage(Integer pageNum, Integer sampleId);

    int StorePageCount(Integer sampleId);

    Store GetStoreInfoById(Integer storeId);

    void deleteStoreById(Integer storeId);

    void changeStore(Store store);
}
