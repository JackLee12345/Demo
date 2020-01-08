package com.communication;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;

public class Server {

    private int port;

    private EventLoopGroup work;

    private EventLoopGroup boss;


    public Server(){

    }

    public void start(){
        ServerBootstrap bootstrap = new ServerBootstrap();

    }
}
