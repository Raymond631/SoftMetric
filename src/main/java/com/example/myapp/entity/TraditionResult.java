package com.example.myapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraditionResult {
    private int loc;
    private String cp; // 保留2位小数
    private int cc;
}
