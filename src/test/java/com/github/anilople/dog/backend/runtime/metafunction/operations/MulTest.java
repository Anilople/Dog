package com.github.anilople.dog.backend.runtime.metafunction.operations;

import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.runtime.Interpreter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MulTest {

    @Test
    void mul() {
        final String text = "(Mul[2][3])";
        NumberName numberName = (NumberName) Interpreter.interpret(text);

        assertEquals("6", numberName.toString());
    }
}