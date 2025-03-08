package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.LoginBack;
import com.example.pojo.SuggestBack;
import com.example.pojo.SuggestInfo;
import com.example.pojo.User;

import java.util.List;

public interface UserService extends IService<User> {
    LoginBack Login(User user);

    Boolean Registerd(User user);

    User getUserInfo(String token);

    User getUserInfoByUserId(Integer userId);

    void changeUserInfo(User user);

    List<User> getAllUserList();

    List<User> getUserList(Integer pageNum);

    Long getUserPageCount();

    void changeUserJudgeById(Integer userId);

    void deleteUserByUserId(Integer userId);

    int getUserPageSum();

    SuggestBack useSetSuggest(SuggestInfo suggestInfo);

    List<SuggestBack> useGetSuggest();

    void reloadSuggest(List<SuggestBack> suggestBacks);

    void newSuggest(SuggestBack newSuggest);

    void deleteSuggestById(String msgId);
}
