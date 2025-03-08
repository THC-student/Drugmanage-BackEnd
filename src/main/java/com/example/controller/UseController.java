package com.example.controller;

import com.example.pojo.*;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RequestMapping("/useController")
@RestController
public class UseController {

    @Autowired
    private UserService userService;

    @PostMapping("/test1")
    public Result list(@RequestBody User user){
        System.out.println("接收到请求");
        System.out.println(user);
        return Result.success(user);
    }

    @GetMapping("/test")
    private Result test( ){
        return Result.success(1);
    }

    @PostMapping("/login")
    public Result Login(@RequestBody User user){
        System.out.println(user);
        LoginBack loginBack= userService.Login(user);
        if(loginBack==null){
            return Result.error("账号或密码错误");
        }
        return Result.success(loginBack);
    }

    @PostMapping("/registered")
    public  Result Registerd(@RequestBody User user){
        System.out.println(user);
       Boolean judge= userService.Registerd(user);
       if(judge==true){
           return Result.success("注册成功");
       }else{
           return Result.error("注册失败");
       }
    }

    @PostMapping("/getUserInfo")
    public Result getUserInfo(@RequestBody Map<String,Object> token){
        String UserToken=(String) token.get("token");
        User user=userService.getUserInfo(UserToken);
        if(user==null){
            return Result.error("获取用户数据失败");
        }
        return Result.success(user);
    }

    @GetMapping("/getUserInfoByUserId")
    private Result getUserInfoByUserId(@RequestParam Integer userId){
        User user= userService.getUserInfoByUserId(userId);
        return Result.success(user);
    }

    @PostMapping("/changeUserInfo")
    private Result changeUserInfo(@RequestBody User user){
        System.out.println(user);
        userService.changeUserInfo(user);
        return Result.success("修改成功");
    }

    @GetMapping("/getAllUserList")
    private  Result getAllUserList(){
        List<User> userList= userService.getAllUserList();
        return Result.success(userList);
    }

    @GetMapping("/getUserList")
    private Result getUserList(@RequestParam Integer pageNum){
        List <User> userList=userService.getUserList(pageNum);
        return Result.success(userList);
    }
    @GetMapping("/getUserPageCount")
    private Result getUserPageCount(){
       Long pageCount=  userService.getUserPageCount();
        return Result.success(pageCount);
    }

    @GetMapping("/changeUserJudgeById")
    private Result changeUserJudgeById(@RequestParam Integer userId){
        System.out.println(userId);
        userService.changeUserJudgeById(userId);
        return Result.success("修改成功");
    }

    @GetMapping("/deleteUserByUserId")
    private Result deleteUserByUserId(@RequestParam Integer userId){
        userService.deleteUserByUserId(userId);
        return Result.success("删除成功");
    }
    @GetMapping("/getUserPageSum")
    private Result getUserPageSum(){
      int pageSum= userService.getUserPageSum();
        return Result.success(pageSum);
    }
    @PostMapping("/useSetSuggest")
    private Result useSetSuggest(@RequestBody SuggestInfo suggestInfo){
        SuggestBack suggestBack=  userService.useSetSuggest(suggestInfo);
        return Result.success(suggestBack);
    }

    @GetMapping("/useGetSuggest")
    private Result useGetSuggest(){
        List<SuggestBack>list= userService.useGetSuggest();
        return Result.success(list);
    }
    @PostMapping("/reloadSuggest")
    private Result reloadSuggest(@RequestBody List<SuggestBack> suggestBacks){
        userService.reloadSuggest(suggestBacks);
        return Result.success("回填成功");
    }

    @PostMapping("/newSuggest")
    private Result newSuggest(@RequestBody SuggestBack newSuggest){
        userService.newSuggest(newSuggest);
        return Result.success("新增成功");
    }
    @GetMapping("/deleteSuggestById")
    private Result deleteSuggestById(@RequestParam String msgId){
        userService.deleteSuggestById(msgId);
        return Result.success("删除成功");
    }
}
