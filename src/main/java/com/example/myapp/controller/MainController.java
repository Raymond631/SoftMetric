package com.example.myapp.controller;

import com.example.myapp.common.http.CommonResponse;
import com.example.myapp.common.utils.FileUtil;
import com.example.myapp.entity.CKResult;
import com.example.myapp.entity.LKResult;
import com.example.myapp.entity.MetricResult;
import com.example.myapp.entity.TraditionResult;
import com.example.myapp.service.MainService;
import com.github.mauricioaniche.ck.CKClassResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class MainController {
    @Value("${res.upload}")
    private String uploadDir;
    @Autowired
    private MainService mainService;

    @GetMapping("metric")
    public CommonResponse metric(@RequestParam("uuid") String uuid) {
        String path = String.format("%s/%s/", uploadDir, uuid);
        Map<String, CKClassResult> ckClassResultMap = mainService.ck(path);
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

    @PostMapping("uploadFiles")
    public CommonResponse uploadFile(@RequestParam("files") MultipartFile[] folder) {
        String uuid = UUID.randomUUID().toString();
        FileUtil.saveMultiFile(String.format("%s/%s/", uploadDir, uuid), folder);
        return CommonResponse.success("上传成功", uuid);
    }
}
