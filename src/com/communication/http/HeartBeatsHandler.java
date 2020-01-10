package communication.http;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author user
 */
public class HeartBeatsHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(HeartBeatsHandler.class);

    private final static String HEATRBEATS = "Are you OK";

    private int readIdleTimes = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            ByteBuf buf = request.content();
            String hbc = buf.toString(CharsetUtil.UTF_8);
            if (HEATRBEATS.equals(hbc)) {
                Content c = new Content();
                c.setContent("Copy that。。。\\r\\n");
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(JSONObject.toJSONString(c), CharsetUtil.UTF_8));
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset = UTF-8");
                ctx.writeAndFlush(response);
            } else {
                logger.info("do business 。。。");
                super.channelRead(ctx, msg);
            }
        } else {
            logger.error("数据解析有误！");
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        String eventType = null;
        switch (event.state()) {
            case READER_IDLE:
                eventType = "读空闲";
                readIdleTimes++;
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                break;
            case ALL_IDLE:
                eventType = "读写空闲";
                break;
        }
        logger.info(ctx.channel().remoteAddress() + "超时时间 = " + eventType);
        if (readIdleTimes > 3) {
            logger.info(" Server 都读空闲超过三次，连接关闭！");
            Content c = new Content();
            c.setContent("Server 都读空闲超过三次，连接关闭！");
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(JSONObject.toJSONString(c), CharsetUtil.UTF_8));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset = UTF-8");
            ctx.writeAndFlush(response);
            ctx.channel().close();
        }
    }
}
