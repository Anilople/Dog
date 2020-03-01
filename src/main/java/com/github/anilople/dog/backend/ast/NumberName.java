package com.github.anilople.dog.backend.ast;

import com.github.anilople.dog.backend.ast.lambda.Name;

/**
 * 表达数字
 */
public class NumberName extends Name {
    public NumberName(String literals) {
        super(literals);
    }

    public NumberName(int value) {
        super(Integer.toString(value));
    }

    /**
     * 加法
     */
    public NumberName add(NumberName that) {
        int x = Integer.parseInt(this.getLiterals());
        int y = Integer.parseInt(that.getLiterals());
        int result = x + y;
        return new NumberName(result);
    }
}
