package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UserMapper;
import com.example.pojo.*;
import com.example.service.UserService;
import com.example.utils.JwtUtils;
import com.example.utils.SHA256Utils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private  UserMapper userMapper;
    @Autowired
    private SHA256Utils sha256Utils;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public LoginBack Login(User user) {
        LoginBack loginBack = new LoginBack();
        String userCount=user.getUserCount();
        String userPassword=sha256Utils.SHE256hash(user.getUserPassword());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_count",userCount);
        User userRight = userMapper.selectOne(queryWrapper);
        if(userRight==null){
            return null;
        }
        if(userRight.getUserCount().equals(userCount)&&userRight.getUserPassword().equals(userPassword)){
            String jwt = jwtUtils.generateJwt(userRight);
            loginBack.setToken(jwt);
            return loginBack;
        }
        return null;
    }

    @Override
    public Boolean Registerd(User user) {

        String userCount=user.getUserCount();
        String userPassword=sha256Utils.SHE256hash(user.getUserPassword());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_count",userCount);
        User userRight = userMapper.selectOne(queryWrapper);
        if(userRight!=null){
            return false;
        }
        user.setUserPassword(userPassword);
        user.setUserName("张三");
        user.setUserHeader("https://qhglhh.oss-cn-beijing.aliyuncs.com/05521404-406b-433f-b871-54350f9dd611.png");
        user.setUserJudge(0);
        userMapper.insert(user);
        return true;
    }

    @Override
    public User getUserInfo(String token) {
        User user = jwtUtils.parseJWT(token);
        return user;
    }

    @Override
    public User getUserInfoByUserId(Integer userId) {
        User user = userMapper.selectById(userId);
        return user;
    }

    @Override
    public void changeUserInfo(User user) {
        String userPassword=user.getUserPassword();
        userPassword=sha256Utils.SHE256hash(userPassword);
        user.setUserPassword(userPassword);
        userMapper.updateById(user);
    }

    @Override
    public List<User> getAllUserList() {
        List<User> userList = userMapper.selectList(null);
        return userList;
    }

    @Override
    public List<User> getUserList(Integer pageNum) {
        int pageSize=8;
        Page<User> page = Page.of(pageNum, pageSize);
        Page<User> userPage = userMapper.selectPage(page, null);
        List<User> records = userPage.getRecords();
        return records;
    }

    @Override
    public Long getUserPageCount() {
        Long pageSize=8L;
        Long aLong = userMapper.selectCount(null);
        Long back=0L;
        if(aLong%pageSize==0){
            back= aLong/pageSize;
        }else{
            back= aLong/pageSize+1;
        }
        return back;
    }

    @Override
    public void changeUserJudgeById(Integer userId) {
        User user = userMapper.selectById(userId);
        user.setUserJudge(1);
        userMapper.updateById(user);
        return;
    }

    @Override
    public void deleteUserByUserId(Integer userId) {
        userMapper.deleteById(userId);
        return;
    }

    @Override
    public int getUserPageSum() {
        List<User> userList = userMapper.selectList(null);
        return userList.size();
    }

    @Override
    public SuggestBack useSetSuggest(SuggestInfo suggestInfo) {
        Integer userId = suggestInfo.getUserId();
        User user = userMapper.selectById(userId);
        String userName=user.getUserName();
        String content=suggestInfo.getContent();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss ");
        Date date = new Date(System.currentTimeMillis());
        String time= formatter.format(date);
        Integer userJudge=user.getUserJudge();
        String userHeader=user.getUserHeader();

        SuggestBack suggestBack = new SuggestBack(userName,userHeader,userJudge,content,time);
        rabbitTemplate.convertAndSend("exceptionSuggestQueue", suggestBack);
        return suggestBack;
    }

    @Override
    public List<SuggestBack> useGetSuggest() {
        List<SuggestBack> list=new ArrayList<>();
        while(true){
            SuggestBack suggestBack = (SuggestBack) rabbitTemplate.receiveAndConvert("exceptionSuggestQueue");
            if(suggestBack==null){
                break;
            }
            list.add(suggestBack);
        }
        return list;
    }

    @Override
    public void reloadSuggest(List<SuggestBack> suggestBacks) {
        for (int i = 0; i < suggestBacks.size(); i++) {
            rabbitTemplate.convertAndSend("exceptionSuggestQueue", suggestBacks.get(i));
        }
        return;
    }
}
