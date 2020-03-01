package com.github.anilople.dog.frontend.lexer.input.token;

import com.github.anilople.dog.frontend.lexer.LexerEvent;
import com.github.anilople.dog.frontend.lexer.input.Token;
import com.github.anilople.dog.frontend.lexer.input.WhiteSpace;

/**
 * 存在的意义是为了让用户
 * 能输入{@link WhiteSpace}
 * 名字不是{@link String}的原因是会覆盖同package下使用的{@link String}
 * 原始字符串两侧的 " 不会存入{@link DogString}中
 */
public class DogString extends Token {
    public DogString(LexerEvent lexerEvent) {
        super(lexerEvent);
    }
}
