package com.chad.core;

import java.util.HashMap;
import java.util.Map;

public class RomanToDecimal extends ConversionRules{

	/**
	 * 将罗马字符从后往前的方式，转换为数字。
	 * @param romanNumber
	 * @return
	 */
	public float romanToDecimal(java.lang.String romanNumber) {

		//最后转换的数字总和
		float decimal = 0;
		//上一次处理的数字
		float lastNumber = 0;
		//上一次处理的罗马字符
		Character lastChar = ' ';
		char[] romanNumeral = romanNumber.toUpperCase().toCharArray();

		//Operation to be performed on upper cases even if user enters Roman values in lower case chars
		for (int x = romanNumeral.length- 1; x >= 0 ; x--) {
			Character convertToDecimal = romanNumeral[x];

			switch (convertToDecimal) {
			case 'M':
				super.checkLiteralCountValidity(convertToDecimal, lastChar);
				decimal = processDecimal(1000, lastNumber, decimal);
				lastNumber = 1000;
				lastChar = 'M';
				break;

			case 'D':
				super.checkLiteralCountValidity(convertToDecimal, lastChar);
				decimal = processDecimal(500, lastNumber, decimal);
				lastNumber = 500;
				lastChar = 'D';
				break;

			case 'C':
				super.checkLiteralCountValidity(convertToDecimal, lastChar);
				decimal = processDecimal(100, lastNumber, decimal);
				lastNumber = 100;
				lastChar = 'C';
				break;

			case 'L':
				super.checkLiteralCountValidity(convertToDecimal, lastChar);
				decimal = processDecimal(50, lastNumber, decimal);
				lastNumber = 50;
				lastChar = 'L';
				break;

			case 'X':
				super.checkLiteralCountValidity(convertToDecimal, lastChar);
				decimal = processDecimal(10, lastNumber, decimal);
				lastNumber = 10;
				lastChar = 'X';
				break;

			case 'V':
				super.checkLiteralCountValidity(convertToDecimal, lastChar);
				decimal = processDecimal(5, lastNumber, decimal);
				lastNumber = 5;
				lastChar = 'V';
				break;

			case 'I':
				super.checkLiteralCountValidity(convertToDecimal, lastChar);
				decimal = processDecimal(1, lastNumber, decimal);
				lastNumber = 1;
				lastChar = 'I';
				break;
			}
		}
		super.resetLiteralsCounter();
		return decimal;
	}

	/**
	 * 处理当前数字，判断是加还是减。
	 * @param number
	 * @param lastNumber
	 * @param lastDecimal
	 * @return
	 */
	public float processDecimal(float number, float lastNumber, float lastDecimal) {
		if (lastNumber > number) {
			return super.subtractionLogic(lastDecimal, number, lastNumber);
		}
		else {
			return lastDecimal + number;
		}
	}
}
