package com.ioc.service;

import com.ioc.dao.UserDao;
import com.ioc.entity.User;
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
