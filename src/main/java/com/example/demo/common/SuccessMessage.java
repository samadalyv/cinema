package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SuccessMessage {
    private int status;
    private String message;
}
