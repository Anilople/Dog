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

    /**
     * 减法
     */
    public NumberName sub(NumberName that) {
        int x = Integer.parseInt(this.getLiterals());
        int y = Integer.parseInt(that.getLiterals());
        int result = x - y;
        return new NumberName(result);
    }

    /**
     * 乘法
     */
    public NumberName mul(NumberName that) {
        int x = Integer.parseInt(this.getLiterals());
        int y = Integer.parseInt(that.getLiterals());
        int result = x * y;
        return new NumberName(result);
    }

    /**
     * 除法
     */
    public NumberName div(NumberName that) {
        int x = Integer.parseInt(this.getLiterals());
        int y = Integer.parseInt(that.getLiterals());
        int result = x / y;
        return new NumberName(result);
    }

    /**
     * 取模
     */
    public NumberName mod(NumberName that) {
        int x = Integer.parseInt(this.getLiterals());
        int y = Integer.parseInt(that.getLiterals());
        int result = x % y;
        return new NumberName(result);
    }
}
