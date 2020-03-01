package com.github.anilople.dog.backend.ast;

import com.github.anilople.dog.backend.ast.lambda.Name;

/**
 * 字符串其实就是特殊的{@link Name}
 * 和Java的{@link String}一样，全局唯一
 */
public class StringName extends Name {
    public StringName(String name) {
        super(name);
    }
}
