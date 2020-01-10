package communication.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    Logger logger = LoggerFactory.getLogger(Server.class);

    private int port;

    private EventLoopGroup work = null;

    private EventLoopGroup boss = null;

    private ChannelInitializer<SocketChannel> channelInitializer;

    public Server(int port, ChannelInitializer channelInitializer, EventLoopGroup work, EventLoopGroup boss) {
        this.port = port;
        this.channelInitializer = channelInitializer;
        this.work = work;
        this.boss = boss;
    }

    public void start() {
        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(channelInitializer)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_BACKLOG, 1024)
                    // 阻塞close()的调用时间，直到数据完全发送
                    .childOption(ChannelOption.SO_LINGER, -1)
                    .childOption(ChannelOption.TCP_NODELAY, true);
            // 绑定端口
            ChannelFuture channelFuture = b.bind(port).sync();
            logger.info("Server start ...");
            // 等待服务端关闭 阻塞
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
