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

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class MainController {
    @Value("${res.upload}")
    private String uploadDir;
    @Autowired
    private MainService mainService;

    @GetMapping("/metric")
    public CommonResponse metric(@RequestParam("uuid") String uuid) {
        String path = String.format("%s/%s/", uploadDir, uuid);
        if (!cn.hutool.core.io.FileUtil.exist(path)) {
            return CommonResponse.error("目录不存在，请重新上传");
        }

        Map<String, CKClassResult> ckClassResultMap = mainService.ck(path);
        List<LKResult> lkResults = mainService.lk(ckClassResultMap);
        List<TraditionResult> traditionResults = mainService.tradition(ckClassResultMap);

        List<CKResult> ckResults = ckClassResultMap.values().stream()
                .map(r -> new CKResult(r.getClassName(), r.getType(), r.getWmc(), r.getRfc(), r.getDit(), r.getNoc(), r.getCbo(), r.getLcom()))
                .collect(Collectors.toList());

        MetricResult metricResult = new MetricResult(ckResults, lkResults, traditionResults);
        return CommonResponse.success(metricResult);
    }

    @PostMapping("/uploadFiles")
    public CommonResponse uploadFile(@RequestParam("files") MultipartFile[] folder) {
        String uuid = UUID.randomUUID().toString();
        FileUtil.saveMultiFile(String.format("%s/%s/", uploadDir, uuid), folder);
        return CommonResponse.success("上传成功", uuid);
    }
}
