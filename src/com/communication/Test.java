package com.communication;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class Test {

    public static void main(String[] args) {
        int port = 8081;
        TCPChannelInitializer tcpChannelInitializer = new TCPChannelInitializer();
        EventLoopGroup boss = new NioEventLoopGroup();
        // work 一般为 Runtime.getRuntime().availableProcessors()
        EventLoopGroup work = new NioEventLoopGroup();
        Server server = new Server(port,tcpChannelInitializer,boss,work);
        server.start();
    }
}
