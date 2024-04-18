package com.example.myapp.service;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.example.myapp.entity.LKResult;
import com.example.myapp.entity.TraditionResult;
import com.github.mauricioaniche.ck.CK;
import com.github.mauricioaniche.ck.CKClassResult;
import com.github.mauricioaniche.ck.CKNotifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {
    public Map<String, CKClassResult> ck() {
        String path = "/home/raymond/project/BlindHelmet";
        Map<String, CKClassResult> results = new HashMap<>();

        new CK(false, 0, false).calculate(path, new CKNotifier() {
            @Override
            public void notify(CKClassResult result) {
                results.put(result.getClassName(), result);
            }

            @Override
            public void notifyError(String sourceFilePath, Exception e) {
                System.err.println("Error in " + sourceFilePath);
                e.printStackTrace(System.err);
            }
        });
        return results;
    }

    public List<LKResult> lk(Map<String, CKClassResult> ckClassResultMap) {
        List<LKResult> lkResults = new ArrayList<>();
        for (CKClassResult ckClassResult : ckClassResultMap.values()) {
            String className = ckClassResult.getClassName();
            String type = ckClassResult.getType();
            int cs = ckClassResult.getNumberOfFields() + ckClassResult.getNumberOfMethods();
            // TODO: 还要计算noo、noa
            int noo = 0;
            int noa = 0;

            double si = noo * ckClassResult.getDit() / ckClassResult.getNumberOfMethods();
            String siStr = NumberUtil.roundStr(si, 2);

            lkResults.add(new LKResult(
                    className,
                    type,
                    cs,
                    noo,
                    noa,
                    siStr
            ));
        }
        return lkResults;
    }


    public List<TraditionResult> tradition(Map<String, CKClassResult> ckClassResultMap) {
        List<TraditionResult> traditionResults = new ArrayList<>();
        for (CKClassResult ckClassResult : ckClassResultMap.values()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(ckClassResult.getFile()));
                String line;
                int totalLines = 0;
                int commentLines = 0;
                int complexity = 1; // 默认圈复杂度为1
                String type = "class";

                while ((line = reader.readLine()) != null) {
                    totalLines++;
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }

                    if (line.contains("interface")) {
                        type = "interface";
                    }

                    if (line.startsWith("//")) { // 检查是否为单行注释
                        commentLines++;
                    } else if (line.startsWith("/*")) { // 检查是否为多行注释
                        commentLines++;
                        while ((line = reader.readLine()) != null) {
                            totalLines++;
                            commentLines++;
                            if (line.contains("*/")) break;
                        }
                    } else if (StrUtil.containsAny(line, "if", "else if", "case", "while", "for", "catch")) {
                        complexity++;
                    }
                }
                reader.close();
                // 计算注释行占比
                double commentPercentage = ((double) commentLines / totalLines) * 100;
                String cpStr = NumberUtil.roundStr(commentPercentage, 2) + "%";

                traditionResults.add(new TraditionResult(
                        ckClassResult.getClassName(),
                        type,
                        totalLines,
                        cpStr,
                        complexity
                ));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return traditionResults;
    }
}
