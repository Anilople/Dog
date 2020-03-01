package com.github.anilople.dog.frontend.lexer;

import com.github.anilople.dog.frontend.lexer.input.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void tokenize() {
        final String text = "\tf -> { IF[a][b] }";
        List<Token> tokens = Lexer.tokenize(text);
        System.out.println(tokens);
        assertEquals(11, tokens.size());
    }

    @Test
    void whiteSpace() {
        final String text = "Print[abc\n]";
        List<Token> tokens = Lexer.tokenize(text);
        System.out.println(tokens);
    }
}