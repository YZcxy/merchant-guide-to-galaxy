package com.chad.core;

import com.chad.utils.FileProcessor;

import java.util.List;


public class Program{

	/**
	 * 方法主入口,此处采用文件的方式来读取输入行，也可以扩展为其他方式
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = null;

		List<String> fileLine = FileProcessor.ReadFile(filePath);

		if (fileLine == null || fileLine.size() == 0){
			System.out.println("请检查输入文件是否符合规范");
			return;
		}

		for (String line : fileLine){
			// 核心处理输入的方法
			String answer = InputProcessor.ProcessLine(line);
			if (answer != null){
				System.out.println(answer);
			}
		}
	}

}
