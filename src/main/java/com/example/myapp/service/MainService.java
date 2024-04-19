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
    public Map<String, CKClassResult> ck(String path) {
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
            // 计算cs
            int cs = ckClassResult.getNumberOfFields() + ckClassResult.getNumberOfMethods();

            int dit = ckClassResult.getDit();
            int nom = ckClassResult.getNumberOfMethods();

            // 计算noo和noa
            int noo = 0;
            int noa = nom;
            if (dit > 1) {  // 有extends
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(ckClassResult.getFile()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (line.isEmpty()) {
                            continue;
                        }
                        // TODO: 如果既有继承父类，又有实现接口，noo可能偏大；如果重写的方法没有标注@Override，noo可能偏小
                        if (line.contains("@Override")) {
                            noo++;
                        }
                    }
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                noa -= noo;
            }
            // 计算si
            double si = nom == 0 ? 0 : noo * dit / (double) nom;
            String siStr = NumberUtil.roundStr(si, 2);

            lkResults.add(new LKResult(
                    ckClassResult.getClassName(),
                    ckClassResult.getType(),
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

                while ((line = reader.readLine()) != null) {
                    totalLines++;
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
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
                        ckClassResult.getType(),
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
