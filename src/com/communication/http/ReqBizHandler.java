package communication.http;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author user
 */
public class ReqBizHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(ReqBizHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;
            try {
                String uri = req.uri();
                ByteBuf buf = req.content();
                String content = buf.toString(CharsetUtil.UTF_8);
                HttpMethod method = req.method();
                if (method.equals(HttpMethod.POST)) {
                    Content c = new Content();
                    c.setContent(content);
                    c.setUri(uri);
                    response(ctx, c);
                }
            } finally {
//                req.release();
            }
        }
    }

    private void response(ChannelHandlerContext ctx, Content c) {
        logger.info("send data 。。。");
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(JSONObject.toJSONString(c), CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset = UTF-8");
        response.headers().set(HttpHeaderNames.KEEP_ALIVE, "Connection: keep-alive");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("连接成功 ...");
//        super.channelActive(ctx);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("register successful。。。");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       logger.info("read complete。。。");
        super.channelReadComplete(ctx);
    }

    class Content {
        String uri;
        String content;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
