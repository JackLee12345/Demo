package communication;

import communication.http.HTTPChannelInitializer;
import communication.http.Server;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author user
 */
public class Test {

    public static void main(String[] args) {
        int port = 8081;
        HTTPChannelInitializer tcpChannelInitializer = new HTTPChannelInitializer();
        EventLoopGroup boss = new NioEventLoopGroup();
        // work 一般为 Runtime.getRuntime().availableProcessors()
        EventLoopGroup work = new NioEventLoopGroup();
        Server server = new Server(port,tcpChannelInitializer,boss,work);
        server.start();
    }
}
