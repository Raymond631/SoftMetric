package com.example.myapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricResult {
    private List<CKResult> ck;
    private List<LKResult> lk;
    private List<TraditionResult> tradition;
}
