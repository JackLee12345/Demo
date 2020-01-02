package com.xxx;

import com.xxx.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        UserService userService = (UserService) context.getBean("userService");
        System.out.println(userService.getUser().getName());
        // https://blog.csdn.net/yyjava/article/details/82149891
    }
}

