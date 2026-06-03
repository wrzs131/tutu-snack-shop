package com.itheima.service.impl;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.model.chat.ChatResponse;
import com.itheima.dto.ChatRequestDTO;
import com.itheima.dto.Message;
import com.itheima.properties.QianfanProperties;
import com.itheima.service.QianfanService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QianfanServiceImpl implements QianfanService {

    @Autowired
    private QianfanProperties qianfanProperties;

    private Qianfan qianfan;

    @PostConstruct
    public void init() {
        log.info("初始化百度千帆SDK...");
        log.info("AccessKey: {}", qianfanProperties.getAccessKey());
        try {
            qianfan = new Qianfan(qianfanProperties.getAccessKey(), qianfanProperties.getSecretKey());
            log.info("百度千帆SDK初始化完成");
        } catch (Exception e) {
            log.error("百度千帆SDK初始化失败", e);
        }
    }

    @Override
    public String chat(ChatRequestDTO chatRequest) {
        try {
            log.info("收到聊天请求: {}", chatRequest);

            if (qianfan == null) {
                throw new RuntimeException("百度千帆SDK未初始化");
            }

            var chatBuilder = qianfan.chatCompletion()
                    .model("ERNIE-3.5-8K");

            for (Message msg : chatRequest.getMessages()) {
                chatBuilder.addMessage(msg.getRole(), msg.getContent());
            }

            ChatResponse response = chatBuilder.execute();
            String content = response.getResult();
            log.info("AI回复: {}", content);
            return content;

        } catch (Exception e) {
            log.error("调用百度千帆AI失败", e);
            return "抱歉，AI服务暂时不可用，请稍后再试。错误: " + e.getMessage();
        }
    }
}
