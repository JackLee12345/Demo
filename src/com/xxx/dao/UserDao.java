package com.xxx.dao;

import com.xxx.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    public User getUser(){
        User user = new User();
        user.setName("zhangsan");
        return user;
    }
}
