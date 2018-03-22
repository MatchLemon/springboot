package com.hm.user.utils;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private Integer code;
    private Object  data;
    private String  message;
    public final static Integer SUCCESS = 200;
    public final static Integer FIALED = 201;
    public static Map resultMap = new HashMap<String, Object>();

    public static Map success(Object ...obj) {
        if (!resultMap.containsKey("code") ||  resultMap.get("code") != Response.SUCCESS) {
            resultMap.put("code", Response.SUCCESS);
        }
        return Response.assemble("data", obj);
    }

    public static Map fialed(Object ...obj) {
        return Response.fialed(Response.FIALED, obj);
    }

    public static Map fialed(Integer code, Object ...obj) {
        resultMap.put("code", Response.FIALED);
        return Response.assemble("msg",obj);
    }

    private static Map assemble(String key, Object ...obj) {
        if (obj == null || obj.length < 2) {
            resultMap.put(key, obj[0]);
        } else {
            resultMap.put(key, obj);
        }
        return resultMap;
    }

    public Response code(Integer code) {
        resultMap.put("code", code);
        return this;
    }

    public static Map message(Object ...obj) {
        resultMap.put("msg", obj);
        return resultMap;
    }
}
