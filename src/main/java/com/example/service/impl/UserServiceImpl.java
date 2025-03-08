package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UserMapper;
import com.example.pojo.*;
import com.example.service.UserService;
import com.example.utils.JwtUtils;
import com.example.utils.SHA256Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private  UserMapper userMapper;
    @Autowired
    private SHA256Utils sha256Utils;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


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
    public void newSuggest(SuggestBack newSuggest) {
        String redisKeyPrefix = "MSG_ID:";
        String msgId = newSuggest.getMsgId();
        String key = redisKeyPrefix + msgId;
        String value = jsonify(newSuggest);
        stringRedisTemplate.opsForValue().set(key, value);
        stringRedisTemplate.expire(key, 30, java.util.concurrent.TimeUnit.DAYS);
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

        String redisKeyPrefix = "MSG_ID:";
        String msgId = suggestInfo.getMsgId();
        String key = redisKeyPrefix + msgId;
        SuggestBack suggestBack = new SuggestBack(userName,userHeader,userJudge,content,time,msgId);
        rabbitTemplate.convertAndSend("exceptionSuggestQueue", suggestBack);
        String value = jsonify(suggestBack);
        stringRedisTemplate.opsForValue().set(key, value);
        stringRedisTemplate.expire(key, 30, java.util.concurrent.TimeUnit.DAYS);
        return suggestBack;
    }

    @Override
    public List<SuggestBack> useGetSuggest() {
        // 定义 Redis 中的键前缀
        String redisKeyPrefix = "MSG_ID:";

        // 获取所有以 MSG_ID: 开头的键
        Set<String> keys = stringRedisTemplate.keys(redisKeyPrefix + "*");

        List<SuggestBack> suggestBackList = new ArrayList<>();

        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                // 获取键对应的 JSON 字符串
                String value = stringRedisTemplate.opsForValue().get(key);
                if (value != null) {
                    // 将 JSON 字符串反序列化为 SuggestBack 对象
                    SuggestBack suggestBack = parseJson(value);
                    if (suggestBack != null) {
                        suggestBackList.add(suggestBack);
                    }
                }
            }
        }
        return suggestBackList;
    }

    @Override
    public void deleteSuggestById(String msgId) {
        if (msgId == null || msgId.isEmpty()) {
            System.out.println("msgId is invalid: " + msgId);
            return ;
        }

        String redisKeyPrefix = "MSG_ID:";
        String key = redisKeyPrefix + msgId;
        stringRedisTemplate.delete(key);
        return ;
    }

    public List<SuggestBack> getAllSuggests() {
        // 定义 Redis 中的键前缀
        String redisKeyPrefix = "MSG_ID:";

        // 获取所有以 MSG_ID: 开头的键
        Set<String> keys = stringRedisTemplate.keys(redisKeyPrefix + "*");

        List<SuggestBack> suggestBackList = new ArrayList<>();

        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                // 获取键对应的 JSON 字符串
                String value = stringRedisTemplate.opsForValue().get(key);
                if (value != null) {
                    // 将 JSON 字符串反序列化为 SuggestBack 对象
                    SuggestBack suggestBack = parseJson(value);
                    if (suggestBack != null) {
                        suggestBackList.add(suggestBack);
                    }
                }
            }
        }

        return suggestBackList;
    }



    @Override
    public void reloadSuggest(List<SuggestBack> suggestBacks) {
        for (int i = 0; i < suggestBacks.size(); i++) {
            rabbitTemplate.convertAndSend("exceptionSuggestQueue", suggestBacks.get(i));
        }
        return;
    }




    private String jsonify(SuggestBack suggestBack) {
        // 或者使用 Jackson 的 ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(suggestBack);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    private SuggestBack parseJson(String jsonString) {
        // 使用 JSON 工具将 JSON 字符串反序列化为 SuggestBack 对象
        // 这里假设你有一个工具类或方法来完成 JSON 反序列化

        // 示例：使用 Jackson 的 ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, SuggestBack.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
