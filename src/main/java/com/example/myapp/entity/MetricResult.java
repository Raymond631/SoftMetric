package com.example.myapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricResult {
    private String className;
    private String type;
    private CKResult ck;
    private LKResult lk;
    private TraditionResult tradition;
}
