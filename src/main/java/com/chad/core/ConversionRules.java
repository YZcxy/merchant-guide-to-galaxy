package com.chad.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConversionRules {

	private static final Character[] NonRepeatingRomanNumerals = {'D', 'L', 'V'};
	private static final Character[] RepeatingRomanNumerals = {'I','V','X','M'};
	private static Map<Character,Integer> RepeatableLiteralsCount = getRepeatableLiteralsCount();

	private static Map<Character,Integer> getRepeatableLiteralsCount(){
		Map<Character,Integer>map = new HashMap<Character,Integer>() {
			{
				put('I', 0);
				put('X', 0);
				put('C', 0);
				put('M', 0);
			}
		};
		return map;
	}

	private static Map<Character,Integer> NonRepeatableLiteralsCount = getNonRepeatableLiteralsCount();

	private static Map<Character,Integer> getNonRepeatableLiteralsCount(){
		Map<Character,Integer>map = new HashMap<Character,Integer>() {
			{
				put('V', 0);
				put('L', 0);
				put('D', 0);
			}
		};
		return map;
	}

	private static Map<Integer, Integer[]> ROMAN_SUBTRACTABLE_MAPPING = Collections.unmodifiableMap(
			new HashMap<Integer, Integer[]>() {
				{
					put(1, new Integer[] {5, 10});
					put(5, new Integer[] {});
					put(10, new Integer[] {50,100});
					put(50, new Integer[] {});
					put(100, new Integer[] {100,1000});
					put(500, new Integer[] {});
					put(1000, new Integer[] {});
				}
			});

	private static Map<Character, Integer> ROMAN_TO_NUMERIC_MAPPING = Collections.unmodifiableMap(
			new HashMap<Character, Integer>() {
				{
					put('I', 1);
					put('V', 5);
					put('X', 10);
					put('L', 50);
					put('C', 100);
					put('D', 500);
					put('M', 1000);
				}
			});

	/**
	 * 检查当前罗马字符是否符合可重复，不可重复的规则
	 */
	public static void checkLiteralCountValidity(Character CurrentLiteral, Character lastChar){
		//这个罗马字符是不可重复的
		if(checkIfLiteralPresent(NonRepeatingRomanNumerals, CurrentLiteral)){
			NonRepeatableLiteralsCount.put(CurrentLiteral, NonRepeatableLiteralsCount.get(CurrentLiteral) + 1);
			if(NonRepeatableLiteralsCount.containsValue(3)){
				System.err.println("罗马字符 V,L,D 不能重复.");
				System.exit(0);
			}
		}
		//这个罗马字符是可重复的
		else if(checkIfLiteralPresent(RepeatingRomanNumerals, CurrentLiteral)){

			//得到已经出现三次的字符
			Character keyForValueContainingThree = getKeyForValueContainingThree();
			if(keyForValueContainingThree != '\0' && CurrentLiteral.equals(keyForValueContainingThree)){
				//如果已经有出现三次的罗马字符，这次又是这个字符，则报错
				System.err.println("罗马字符 "+CurrentLiteral+" 重复了四次");
				System.exit(0);

			}
			else{
				CurrentLiteralSmallerThanPrevious(CurrentLiteral,lastChar);
				RepeatableLiteralsCount.put(CurrentLiteral, RepeatableLiteralsCount.get(CurrentLiteral) +1);
			}
		}
	}

	/**
	 * 查看当前是否已经有数字重复了三次
	 * 
	 */
	private static char getKeyForValueContainingThree(){
		char key = '\0';
		Iterator<Map.Entry<Character,Integer>> iter = RepeatableLiteralsCount.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Character,Integer> entry = iter.next();
			if (entry.getValue().equals(3)) {
				return Character.valueOf(entry.getKey());
			}
		}
		return key;
	}

	/**
	 * 如果上一个罗马字符可重复，并且当前字符比他小，则可以清空重复计数。
	 * @param CurrentLiteral
	 * @param lastChar
	 * @return
	 */
	private static void CurrentLiteralSmallerThanPrevious(char CurrentLiteral, char lastChar){
		if (lastChar == ' '){
			return;
		}
		if (checkIfLiteralPresent(RepeatingRomanNumerals, lastChar)){
			if (ROMAN_TO_NUMERIC_MAPPING.get(CurrentLiteral) < ROMAN_TO_NUMERIC_MAPPING.get(lastChar)){
				RepeatableLiteralsCount.put(lastChar,0);
			}
		}
	}

	/**
	 * 如果这个罗马数字比上一个小，则查询是否符合减法的规范
	 * @param lastDecimal
	 * @param number
	 * @param lastNumber
	 * @return
	 */
	public static float subtractionLogic(Float lastDecimal, Float number, Float lastNumber){
		if(Arrays.asList(ROMAN_SUBTRACTABLE_MAPPING.get(Math.round(number))).contains(Math.round(lastNumber))){
			return lastDecimal - number;
		}
		else
			return lastDecimal + number;
	}


	/**
	 * 判断这个字符是否存在于这个数组中
	 * @param array
	 * @param literal
	 * @return
	 */
	public static boolean checkIfLiteralPresent(Character[] array, Character literal){
		boolean result = false;
		for (int i = 0; i < array.length; i++) {
			if(array[i].equals(literal))
				result =  true;
		}
		return result;
	}

	public static void resetLiteralsCounter(){
		RepeatableLiteralsCount = getRepeatableLiteralsCount();
		NonRepeatableLiteralsCount = getNonRepeatableLiteralsCount();

	}
}
