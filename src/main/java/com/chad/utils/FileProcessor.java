package com.chad.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {

    /**
     * 读取目标文件，如果没有指定文件路径，则读取默认input.txt
     * @param filePath
     * @return
     * 以行为单位，返回文件的所有行List<String>
     */
    public static List<String> ReadFile(String filePath){
        ArrayList<String> fileLines = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            if (filePath == null){
                FileReader fileReader = new FileReader("input.txt");
                bufferedReader = new BufferedReader(fileReader);
            }else {
                FileReader fileReader = new FileReader(filePath);
                bufferedReader = new BufferedReader(fileReader);
            }

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileLines.add(line);
            }
            return fileLines;
        }catch (FileNotFoundException e){
            System.out.println("找不到输入文件，请确认文件目录");
            return null;
        }catch (IOException e){
            System.out.println("文件读取错误");
            return null;
        }finally {
            try {
                bufferedReader.close();
            }catch (Exception e) {
                System.out.println("关闭bufferedReader失败");
            }
        }
    }

}
