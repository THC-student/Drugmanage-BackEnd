package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.StoreMapper;
import com.example.pojo.Drug;
import com.example.pojo.Store;
import com.example.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper,Store> implements StoreService {
    @Autowired
    private  StoreMapper storeMapper;
    @Override
    public HashSet<Integer>storeService() {
        List<Store> stores = storeMapper.selectList(null);
        HashSet<Integer> integers = new HashSet<>();
        for (Store store : stores) {
            integers.add(store.getSampleId());
        }
        return integers;
    }

    @Override
    public List<Store> StorePage(Integer pageNum, Integer sampleId) {
        Integer pageSize=8;
        Page<Store> page = Page.of(pageNum, pageSize);
        QueryWrapper<Store> storeQueryWrapper = new QueryWrapper<>();
        storeQueryWrapper.eq("sample_id",sampleId);
        Page<Store> storePage = storeMapper.selectPage(page, storeQueryWrapper);
        List<Store> records = storePage.getRecords();
        return records;
    }

    @Override
    public int StorePageCount(Integer sampleId) {
        QueryWrapper<Store> storeQueryWrapper = new QueryWrapper<>();
        storeQueryWrapper.eq("sample_id",sampleId);
        List<Store> stores = storeMapper.selectList(storeQueryWrapper);
        return stores.size();
    }

    @Override
    public Store GetStoreInfoById(Integer storeId) {
        Store store = storeMapper.selectById(storeId);
        return store;
    }

    @Override
    public void deleteStoreById(Integer storeId) {
        storeMapper.deleteById(storeId);
        return ;
    }

    @Override
    public void changeStore(Store store) {
        storeMapper.updateById(store);
        return ;
    }
}
