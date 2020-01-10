package ioc.service;

import ioc.dao.UserDao;
import ioc.entity.User;
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
