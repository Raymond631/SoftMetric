package com.example.myapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CKResult {
    private String className;
    private String type;
    private int wmc;
    private int rfc;
    private int dit;
    private int noc = -1;
    private int cbo;
    private int lcom;
}
