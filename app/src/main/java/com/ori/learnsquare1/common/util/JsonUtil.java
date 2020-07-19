package com.ori.learnsquare1.common.util;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * 类说明:基于fastjson的json处理类
 */

public class JsonUtil {

    private static final String TAG = JsonUtil.class.getSimpleName();


    /**
     * 序列化为Json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object){
        if (null == object){
            return null;
        }
        return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * json转JavaBean
     * @param jsonStr
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T jsonToJavaBean(String jsonStr, Class cls){
        if (TextUtils.isEmpty(jsonStr)){
            return null;
        }
        return (T) JSON.parseObject(jsonStr, cls);
    }

    /**
     * json转ListBean
     * @param jsonStr
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T jsonToListBean(String jsonStr, Class cls){
        if (TextUtils.isEmpty(jsonStr)){
            return null;
        }
        return (T) JSON.parseArray(jsonStr, cls);
    }

}
