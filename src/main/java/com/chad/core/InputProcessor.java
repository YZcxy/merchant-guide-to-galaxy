package com.chad.core;

import com.chad.model.MetalValueMapping;
import com.chad.model.ValidWords;
import com.chad.model.WordRomanMapping;

import java.util.*;

public class InputProcessor{


	/**
	 * 根据不同的规则，处理不同的输入行
	 * 目前大致可分为三类：
	 * 1、第一类输入，规则为长度为三，中间一个字符串为is。
	 *    用来维护单词和罗马字符的对应表。
	 * 2、第二类输入，规则为以credits结尾。
	 *    用来计算金属的价格。
	 * 3、第三类输入，规则为以"?"结尾。
	 *    根据需要返回对应的答案
	 * @param line
	 */
	public static String ProcessLine(String line){
		String answer = null;
		String words[] = line.split("((?<=:)|(?=:))|( )");

		if (words.length == 3 && words[1].equalsIgnoreCase("is")){
			ProcessDictionary(words);
		}
		else if(line.toLowerCase().endsWith("credits")){
			ProcessMentalPrice(words);
		}
		else if (line.endsWith("?")){
			if (CheckQuestion(words)){
				answer = ProcessQuestion(words);
			}else {
				answer = "I have no idea what you are talking about";
			}

		}
		return answer;
	}

	/**
	 * 第一类输入，规则为长度为三，中间一个字符串为is。
	 * 这类输入的核心目的是建立单词和罗马字符对应表。
	 * @param words
	 */
	private static void ProcessDictionary(String[] words){
		WordRomanMapping.getInstance().put(words[0], words[words.length-1]);
	}

	/**
	 * 第二类输入，规则为以credits结尾。
	 * 用来计算金属的价格，并更新金属价格表。
	 * @param words
	 */
	private static void ProcessMentalPrice(String[] words){

		int splitIndex = 0;
		int creditValue = 0; String metal= null; String[] wordValue = null;

		//从输入中截取出数量单词，金属类型，金属价值
		for (int i = 0; i < words.length; i++) {
			if(words[i].toLowerCase().equals("credits")){
				creditValue = Integer.parseInt(words[i-1]);
			}
			if(words[i].toLowerCase().equals("is")){
				splitIndex = i-1;
				metal = words[i-1];
			}
		}
		wordValue = java.util.Arrays.copyOfRange(words, 0, splitIndex);


		//将数量单词，转换为数量罗马字符
		StringBuilder romanValue = new StringBuilder();
		for (int j = 0; j < wordValue.length; j++) {
			romanValue.append(WordRomanMapping.getInstance().get(wordValue[j]));
		}

		//将表示数量的罗马字符转换为十进制数，然后计算金属单价并保存
		float decimalValue = new RomanToDecimal().romanToDecimal(romanValue.toString());
		MetalValueMapping.getInstance().put(metal, creditValue/decimalValue);
	}


	/**
	 * 第三类输入，规则为以"?"结尾。
	 * 根据需要返回对应的答案
	 * @param words
	 * @return
	 */
	private static String ProcessQuestion(String[] words){
		int startIndex = 0, endIndex = 0;

		String metal= null; String[] wordValue = null;
		//从输入中截取出数量单词，金属类型，金属价值
		for (int i = 0; i < words.length; i++) {
			if(words[i].toLowerCase().equals("is")){
				startIndex = i+1;
			}
			if(words[i].toLowerCase().equals("?")){
				endIndex = i;
				//如果包含金属单词，则说明需要求金属价格
				if (MetalValueMapping.getInstance().containsKey(words[i-1])){
					metal = words[i-1];
					endIndex = i-1;
				}
			}
		}
		wordValue = java.util.Arrays.copyOfRange(words, startIndex, endIndex);


		//将数量单词，转换为数量罗马字符
		StringBuilder romanValue = new StringBuilder();
		for (int j = 0; j < wordValue.length; j++) {
			romanValue.append(WordRomanMapping.getInstance().get(wordValue[j]));
		}

		//将表示数量的罗马字符转换为十进制数
		float decimalValue = new RomanToDecimal().romanToDecimal(romanValue.toString());

		//返回对应的答案
		ArrayList<String> answerArray = new ArrayList<>(Arrays.asList(wordValue));
		if (metal != null){
			answerArray.add(metal);
			answerArray.add("is");
			answerArray.add(Float.toString(decimalValue * MetalValueMapping.getInstance().get(metal)));
			answerArray.add("Credits");
		}else {
			answerArray.add("is");
			answerArray.add(Float.toString(decimalValue));
		}

		return answerArray.toString().replace(",", "").replace("[", "").replace("]", "");
	}

	private static boolean CheckQuestion(String[] words){
		if (!"how".equals(words[0])){
			return false;
		}
		if (!("many".equals(words[1]) && "Credits".equals(words[2]))
				&& !"much".equals(words[1])){
			return false;
		}
		for (String word : words){
			if (!ValidWords.iscontains(word)
					&& !MetalValueMapping.getInstance().containsKey(word)
					&& !WordRomanMapping.getInstance().containsKey(word)){
				return false;
			}
		}
		return true;
	}

}
