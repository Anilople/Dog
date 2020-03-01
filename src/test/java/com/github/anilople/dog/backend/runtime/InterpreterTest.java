package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.Name;
import org.junit.jupiter.api.Test;

class InterpreterTest {

    @Test
    void interpret() {
        Interpreter.interpret(new Application(
                new VariableName("Print"),
                new Name("abc")
        ));

    }
}