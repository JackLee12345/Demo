package com.tak;

import org.springframework.stereotype.Service;
import java.util.Date;


@Service
public class InitDataJob {

    public void execute(){
        System.out.println("现在时间 :"+new Date());
    }

    public void init(){
        System.out.println("初始化开始！！！！");
        execute();
    }
}
