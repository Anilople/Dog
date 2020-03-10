package com.github.anilople.dog.backend.runtime.metafunction;

import com.github.anilople.dog.backend.runtime.Interpreter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BindTest {

    @Test
    void evaluation() {
        final String text = "(Bind[A][B])";
        Interpreter.interpret(text);
    }

    @Test
    void bindPrint() {
        final String text = "(Bind[Out][Print])" +
                "(Out[\"d\"])";
        Interpreter.interpret(text);
    }

    @Test
    void bindBind() {
        final String text = "(Bind[NewBind][Bind])" +
                "(NewBind[Out][Print])" +
                "(Out[\"BC\"])";
        Interpreter.interpret(text);
    }

    /**
     * 绑定不可变变量
     */
    @Test
    void bindUnmodifiable() {
        final String text = "(Bind[A][B])" +
                "(Bind[A][C])";
        assertThrows(
                RuntimeException.class,
                () -> Interpreter.interpret(text)
        );
    }

}