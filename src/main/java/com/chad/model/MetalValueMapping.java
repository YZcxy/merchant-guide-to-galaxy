package com.chad.model;

import java.util.concurrent.ConcurrentHashMap;

public class MetalValueMapping {
    private static final ConcurrentHashMap<String,Float> MAPPING = new ConcurrentHashMap<>();    //声明静态的单例对象的变量
    private MetalValueMapping(){}    //私有构造方法

    public static ConcurrentHashMap<String,Float> getInstance(){
        return MAPPING;
    }
}
