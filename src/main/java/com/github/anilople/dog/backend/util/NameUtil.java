package com.github.anilople.dog.backend.util;

import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.definition.Letters;

import java.util.Set;

public class NameUtil {

    /**
     * 生成一个{@link Name}，
     * 并且不在给定的参数里
     * @param names 一堆变量
     * @return 不重名的变量
     */
    public static Name generateRandomDifferentName(Set<Name> names) {
        // 首先从26个字母中，看有没有不重名的
        for(char c : Letters.LETTERS.toCharArray()) {
            String letter = c + "";
            Name now = new Name(letter);
            if(!names.contains(now)) {
                return now;
            }
        }
        // 暂时不考虑26个字母不够用的情况
        throw new IllegalArgumentException("找不到可用的变量名, 当前在用的变量名有" + names);
    }

}
