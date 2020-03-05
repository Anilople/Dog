package com.github.anilople.dog.backend.runtime.metafunction.operations;

import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.runtime.Interpreter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddTest {

    @Test
    void add() {
        final String text = "(Add[2][3])";
        NumberName numberName = (NumberName) Interpreter.interpret(text);

        assertEquals("5", numberName.toString());
    }

    @Test
    void addMultiple() {
        final String text = "(Add[Add[1][2]][Add[3][4]])";
        NumberName numberName = (NumberName) Interpreter.interpret(text);

        assertEquals("10", numberName.toString());
    }

}