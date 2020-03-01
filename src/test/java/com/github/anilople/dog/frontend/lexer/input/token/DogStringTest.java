package com.github.anilople.dog.frontend.lexer.input.token;

import com.github.anilople.dog.frontend.lexer.Lexer;
import com.github.anilople.dog.frontend.lexer.input.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DogStringTest {

    @Test
    void tokenize() {
        final String content = "a\tb c";
        final String text = '"' + content + '"';
        List<Token> tokens = Lexer.tokenize(text);
        System.out.println(tokens);
        assertEquals(1, tokens.size());
        assertEquals(content, tokens.get(0).getContent());
    }

}