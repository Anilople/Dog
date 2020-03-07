package com.github.anilople.dog.util;

import com.github.anilople.dog.frontend.formater.CodeFormatter;

import java.util.HashSet;
import java.util.Set;

public class StringUtil {

    /**
     * 给定缩进个数，计算产生的字符串
     * @param indentationCount 缩进的个数
     * @return 缩进的字符串
     * @throws IllegalStateException 如果缩进个数小于0
     */
    public static String getIndentations(int indentationCount) {
        if(indentationCount < 0) {
            throw new IllegalStateException("缩进个数不能小于0");
        }
        StringBuilder stringBuilder = new StringBuilder(CodeFormatter.ONE_INDENTATION.length() * indentationCount + 1);
        for(int i = 0; i < indentationCount; i++) {
            stringBuilder.append(CodeFormatter.ONE_INDENTATION);
        }
        return stringBuilder.toString();
    }

    /**
     * 统计在{@code s}中有多少个{@code c}
     * @param s 字符串
     * @param c 字符
     * @return 字符在字符串中出现的次数
     */
    public static int countOccurrencesOf(String s, char c) {
        int count = 0;
        for(int i = 0; i < s.length(); i++) {
            if(c == s.charAt(i)) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * 统计在{@code s}中，{@code chars}中的字符出现过多少次
     */
    public static int countOccurrencesOf(String s, String chars) {
        // 去重复
        final Set<Character> characterSet = new HashSet<>();
        for(int i = 0; i < chars.length(); i++) {
            char c = chars.charAt(i);
            characterSet.add(c);
        }

        // 统计
        int count = 0;
        for(Character character : characterSet) {
            count += countOccurrencesOf(s, character);
        }
        return count;
    }
}
