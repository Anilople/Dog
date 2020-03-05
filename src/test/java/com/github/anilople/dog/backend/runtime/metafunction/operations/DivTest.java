package com.github.anilople.dog.backend.runtime.metafunction.operations;

import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.runtime.Interpreter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DivTest {

    @Test
    void div() {
        final String text = "(Div[8][2])";
        NumberName numberName = (NumberName) Interpreter.interpret(text);

        assertEquals("4", numberName.toString());
    }

}