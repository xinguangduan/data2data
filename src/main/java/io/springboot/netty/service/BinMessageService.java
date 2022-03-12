package io.springboot.netty.service;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BinMessageService {
    public void handle (ByteBuf message) {
        log.info("binary消息:{}", message.readableBytes());
    }
}
