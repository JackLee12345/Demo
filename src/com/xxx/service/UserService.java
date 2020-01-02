package com.xxx.service;

import com.xxx.dao.UserDao;
import com.xxx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUser(){
        User user = userDao.getUser();
        return user;
    }
}
