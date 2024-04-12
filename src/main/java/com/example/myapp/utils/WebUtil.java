package com.example.myapp.utils;

import org.jfree.data.json.impl.JSONObject;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WebUtil {
    public static String trueReturn(String msg, Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("msg", msg);
        result.put("data", data);
        return JSONObject.toJSONString(result);
    }

    public static String falseReturn(String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("msg", msg);
        return JSONObject.toJSONString(result);
    }
}
