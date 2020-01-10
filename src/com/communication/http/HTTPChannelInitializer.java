package communication.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author user
 */
public class HTTPChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        // 可以对入站\出站事件进行日志记录，从而方便我们进行问题排查。
        socketChannel.pipeline().addLast("logging",new LoggingHandler(LogLevel.INFO));
        // 请求解码器
        socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
        // 将HTTP消息的多个部分合成一条完整的HTTP消息
        socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));
        // 响应转码器
        socketChannel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
        // 解决大码流的问题，ChunkedWriteHandler：向客户端发送HTML5文件
        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        socketChannel.pipeline().addLast(new IdleStateHandler(2,2,2, TimeUnit.SECONDS));
        socketChannel.pipeline().addLast(new HeartBeatsHandler());
        // 业务处理
        socketChannel.pipeline().addLast(new ReqBizHandler());
    }
}
