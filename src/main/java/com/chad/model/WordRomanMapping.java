package com.chad.model;

import java.util.concurrent.ConcurrentHashMap;

public class WordRomanMapping {

    private static ConcurrentHashMap<String,String> MAPPING = new ConcurrentHashMap<>();    //声明静态的单例对象的变量
    private WordRomanMapping(){}    //私有构造方法

    public static ConcurrentHashMap<String,String> getInstance(){
        return MAPPING;
    }
}
