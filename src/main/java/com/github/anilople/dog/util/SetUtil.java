package com.github.anilople.dog.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link Set}的工具类
 * 返回的都是不可变的，用{@link Collections#unmodifiableSet(Set)}进行了封装
 */
public class SetUtil {

    /**
     * @return 并集
     */
    public static <T> Set<T> union(Set<T> a, Set<T> b) {
        Set<T> ab = new HashSet<>();
        ab.addAll(a);
        ab.addAll(b);
        return Collections.unmodifiableSet(ab);
    }

    /**
     * @return 交集
     */
    public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
        Set<T> common = new HashSet<>();
        for(T value : a) {
            if(b.contains(value)) {
                // 如果b中也有a的这个值
                common.add(value);
            }
        }
        return Collections.unmodifiableSet(common);
    }

    /**
     * @return 差集，a - b
     */
    public static <T> Set<T> difference(Set<T> a, Set<T> b) {
        Set<T> inANotInB = new HashSet<>();
        for(T value : a) {
            if(!b.contains(value)) {
                // 在 a 中有，但是不能在 b 中存在
                inANotInB.add(value);
            }
        }
        return inANotInB;
    }

}
