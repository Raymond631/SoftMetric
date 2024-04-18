package com.example.myapp.controller;

import com.example.myapp.common.http.CommonResponse;
import com.example.myapp.entity.CKResult;
import com.example.myapp.entity.LKResult;
import com.example.myapp.entity.MetricResult;
import com.example.myapp.entity.TraditionResult;
import com.example.myapp.service.MainService;
import com.github.mauricioaniche.ck.CKClassResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {
    @Autowired
    private MainService mainService;

    @GetMapping("metric")
    public CommonResponse metric() {
        Map<String, CKClassResult> ckClassResultMap = mainService.ck();
        Map<String, LKResult> lkResults = mainService.lk(ckClassResultMap);
        Map<String, TraditionResult> traditionResults = mainService.tradition(ckClassResultMap);

        List<MetricResult> metricResults = new ArrayList<>();
        for (CKClassResult r : ckClassResultMap.values()) {
            CKResult ckResult = new CKResult(
                    r.getWmc(),
                    r.getRfc(),
                    r.getDit(),
                    r.getNoc(),
                    r.getCbo(),
                    r.getLcom()
            );
            metricResults.add(new MetricResult(
                    r.getClassName(),
                    r.getType(),
                    ckResult,
                    lkResults.get(r.getClassName()),
                    traditionResults.get(r.getClassName())
            ));
        }


        return CommonResponse.success(metricResults);
    }
}
