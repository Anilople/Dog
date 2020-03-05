package com.github.anilople.dog.backend.runtime.metafunction.operations.comparator;

import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.definition.Booleans;
import com.github.anilople.dog.backend.runtime.Interpreter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberComparatorTest {

    @Test
    void equal() {
        final LambdaExpression resultFalse = Interpreter.interpret("(==[0][1])");
        assertTrue(Booleans.FALSE.isSameStructureWithoutReduction(resultFalse));

        final LambdaExpression resultTrue = Interpreter.interpret("(==[23][23])");
        assertTrue(Booleans.TRUE.isSameStructureWithoutReduction(resultTrue));
    }

    @Test
    void less() {
        final LambdaExpression resultFalse = Interpreter.interpret("(<[1][0])");
        assertTrue(Booleans.FALSE.isSameStructureWithoutReduction(resultFalse));

        final LambdaExpression resultTrue = Interpreter.interpret("(<[0][1])");
        assertTrue(Booleans.TRUE.isSameStructureWithoutReduction(resultTrue));
    }

    @Test
    void great() {
        final LambdaExpression resultFalse = Interpreter.interpret("(>[0][1])");
        assertTrue(Booleans.FALSE.isSameStructureWithoutReduction(resultFalse));

        final LambdaExpression resultTrue = Interpreter.interpret("(>[1][0])");
        assertTrue(Booleans.TRUE.isSameStructureWithoutReduction(resultTrue));
    }
}