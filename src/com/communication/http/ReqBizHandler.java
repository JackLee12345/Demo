package communication.http;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
                req.release();
            }
        }
    }

    private void response(ChannelHandlerContext ctx, Content c) {
        logger.info("send data 。。。");
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(JSONObject.toJSONString(c), CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset = UTF-8");
        ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    logger.info("successful...11111111111");
                }else {
                    logger.error("error...");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }

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
}
