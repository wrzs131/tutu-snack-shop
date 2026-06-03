package com.itheima.controller;

import com.itheima.dto.ChatRequestDTO;
import com.itheima.pojo.Result;
import com.itheima.service.QianfanService;
import com.itheima.vo.ChatResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/qianfan")
@Tag(name = "百度千帆AI接口")
public class QianfanController {

    @Autowired
    private QianfanService qianfanService;

    @Operation(summary = "与AI对话")
    @PostMapping("/chat")
    public Result<ChatResponseVO> chat(@RequestBody ChatRequestDTO chatRequest) {
        log.info("收到聊天请求: {}", chatRequest);
        String response = qianfanService.chat(chatRequest);
        ChatResponseVO chatResponseVO = ChatResponseVO.builder()
                .content(response)
                .build();
        return Result.success(chatResponseVO);
    }
}