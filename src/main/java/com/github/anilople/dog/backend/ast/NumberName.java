package com.github.anilople.dog.backend.ast;

import com.github.anilople.dog.backend.ast.lambda.Name;

/**
 * 表达数字
 */
public abstract class NumberName extends Name implements Comparable<NumberName> {

    public NumberName(String literals) {
        super(literals);
    }
    
    /**
     * 加法
     */
    public abstract NumberName add(NumberName that);

    /**
     * 减法
     */
    public abstract NumberName sub(NumberName that);

    /**
     * 乘法
     */
    public abstract NumberName mul(NumberName that);

    /**
     * 除法
     */
    public abstract NumberName div(NumberName that);

    /**
     * 取模
     */
    public abstract NumberName mod(NumberName that);
}
