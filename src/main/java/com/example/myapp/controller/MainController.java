package com.example.myapp.controller;

import com.example.myapp.common.http.CommonResponse;
import com.example.myapp.entity.CKResult;
import com.example.myapp.entity.LKResult;
import com.example.myapp.entity.TraditionResult;
import com.example.myapp.service.MainService;
import com.github.mauricioaniche.ck.CKClassResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {
    @Autowired
    private MainService mainService;

    @GetMapping("metric")
    public CommonResponse metric() {
        Map<String, CKClassResult> ckClassResultMap = mainService.ck();
        List<CKResult> ckResults = new ArrayList<>();
        for (CKClassResult r : ckClassResultMap.values()) {
            CKResult ckResult = new CKResult(
                    r.getClassName(),
                    r.getType(),
                    r.getWmc(),
                    r.getRfc(),
                    r.getDit(),
                    r.getNoc(),
                    r.getCbo(),
                    r.getLcom()
            );
            ckResults.add(ckResult);
        }

        List<LKResult> lkResults = mainService.lk(ckClassResultMap);
        List<TraditionResult> traditionResults = mainService.tradition(ckClassResultMap);

        Map<String, List> result = new HashMap<>();
        result.put("ck", ckResults);
        result.put("lk", lkResults);
        result.put("tradition", traditionResults);
        return CommonResponse.success(result);
    }
}
