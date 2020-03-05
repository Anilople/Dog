package com.github.anilople.dog.backend.runtime.metafunction.operations;

import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.runtime.Interpreter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubTest {

    @Test
    void sub() {
        final String text = "(Sub[2][3])";
        NumberName numberName = (NumberName) Interpreter.interpret(text);

        assertEquals("-1", numberName.toString());
    }
}