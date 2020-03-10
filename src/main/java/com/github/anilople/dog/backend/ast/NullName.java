package com.github.anilople.dog.backend.ast;

import com.github.anilople.dog.backend.ast.lambda.Name;

/**
 * 为了表示什么都没有，所以设计了这个
 */
public class NullName extends Name {

    /**
     * 单例
     */
    public static final NullName NULL_NAME = new NullName(null);

    /**
     * 显示出来的字符串，
     * 打印出来的字符串
     */
    public static final String SHOW = "Null";

    public static NullName getInstance() {
        return NULL_NAME;
    }

    private NullName(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return SHOW;
    }

}
