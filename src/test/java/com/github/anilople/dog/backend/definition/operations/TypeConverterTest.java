package com.github.anilople.dog.backend.definition.operations;

import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.definition.Booleans;
import com.github.anilople.dog.backend.definition.Numbers;
import com.github.anilople.dog.backend.runtime.Interpreter;
import org.junit.jupiter.api.Test;

import static com.github.anilople.dog.backend.definition.operations.TypeConverter.IS_NOT_ZERO;
import static com.github.anilople.dog.backend.definition.operations.TypeConverter.IS_ZERO;
import static org.junit.jupiter.api.Assertions.*;

class TypeConverterTest {

    @Test
    void isZero() {
        Application false2 = new Application(IS_ZERO, Numbers.TWO);
        assertTrue(
                Booleans.FALSE
                        .isSameStructureWithoutReduction(
                                Interpreter.interpret(false2)
                        )
        );

        Application true0 = new Application(IS_ZERO, Numbers.ZERO);
        assertTrue(
                Booleans.TRUE
                        .isSameStructureWithoutReduction(
                                Interpreter.interpret(true0)
                        )
        );
    }

    @Test
    void IS_NOT_ZERO() {
        assertTrue(
                Booleans.FALSE.isSameStructureWithoutReduction(
                        Interpreter.interpret(new Application(IS_NOT_ZERO, Numbers.ZERO))
                )
        );

        assertTrue(
                Booleans.TRUE.isSameStructureWithoutReduction(
                        Interpreter.interpret(new Application(IS_NOT_ZERO, Numbers.ONE))
                )
        );

        assertTrue(
                Booleans.TRUE.isSameStructureWithoutReduction(
                        Interpreter.interpret(new Application(IS_NOT_ZERO, Numbers.TWO))
                )
        );
    }

}