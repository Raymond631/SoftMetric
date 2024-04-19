package com.example.myapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LKResult {
    private String className;
    private String type;
    private int cs;
    private int noo;
    private int noa;
    private String si;  // 保留2位小数
}
