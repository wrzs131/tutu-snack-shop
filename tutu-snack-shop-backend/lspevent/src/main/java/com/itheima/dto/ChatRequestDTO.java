package com.itheima.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatRequestDTO {
    private List<Message> messages;
}
