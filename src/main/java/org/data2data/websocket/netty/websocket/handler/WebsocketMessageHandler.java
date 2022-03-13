package org.data2data.websocket.netty.websocket.handler;

import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import org.data2data.websocket.netty.constant.Constant;
import org.data2data.websocket.netty.service.BinMessageService;
import lombok.extern.slf4j.Slf4j;
import org.data2data.websocket.netty.service.TextMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;


@Sharable
@Component
@Slf4j
public class WebsocketMessageHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Autowired
    TextMessageService textMessageService;
    @Autowired
    BinMessageService binMessageService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
            // 业务层处理数据
            this.textMessageService.handle(textWebSocketFrame.text());
            // 响应客户端
            ctx.channel().writeAndFlush(new TextWebSocketFrame("我收到了你的消息：" + System.currentTimeMillis()));
        } else if (msg instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame textWebSocketFrame = (BinaryWebSocketFrame) msg;
            // 业务层处理数据
            this.binMessageService.handle(textWebSocketFrame.content());
            // 响应客户端
            ctx.channel().writeAndFlush(new TextWebSocketFrame(Constant.PROC_ASR_RESULT));
        } else {
            // 不接受文本以外的数据帧类型
            ctx.channel().writeAndFlush(WebSocketCloseStatus.INVALID_MESSAGE_TYPE).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.info("链接断开：{}", ctx.channel().remoteAddress());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("链接创建：{}", ctx.channel().remoteAddress());
    }
}
