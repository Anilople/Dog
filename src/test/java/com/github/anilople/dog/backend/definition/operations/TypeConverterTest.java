package com.github.anilople.dog.backend.definition.operations;

import com.github.anilople.dog.backend.ast.Evaluation;
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
                                Evaluation.execute(false2, null)
                        )
        );

        Application true0 = new Application(IS_ZERO, Numbers.ZERO);
        assertTrue(
                Booleans.TRUE
                        .isSameStructureWithoutReduction(
                                Evaluation.execute(true0, null)
                        )
        );
    }

    @Test
    void IS_NOT_ZERO() {
        assertTrue(
                Booleans.FALSE.isSameStructureWithoutReduction(
                        Evaluation.execute(new Application(IS_NOT_ZERO, Numbers.ZERO), null)
                )
        );

        assertTrue(
                Booleans.TRUE.isSameStructureWithoutReduction(
                        Evaluation.execute(new Application(IS_NOT_ZERO, Numbers.ONE), null)
                )
        );

        assertTrue(
                Booleans.TRUE.isSameStructureWithoutReduction(
                        Evaluation.execute(new Application(IS_NOT_ZERO, Numbers.TWO), null)
                )
        );
    }

}