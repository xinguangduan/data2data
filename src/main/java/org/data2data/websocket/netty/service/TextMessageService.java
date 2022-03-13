package org.data2data.websocket.netty.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TextMessageService {

    public void handle(String message) {
        log.info("String消息:{}", message);
    }
}
