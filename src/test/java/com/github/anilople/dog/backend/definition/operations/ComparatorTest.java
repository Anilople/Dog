package com.github.anilople.dog.backend.definition.operations;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.definition.Booleans;
import com.github.anilople.dog.backend.definition.Numbers;
import com.github.anilople.dog.backend.runtime.Interpreter;
import com.github.anilople.dog.backend.util.ApplicationUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComparatorTest {

    @Test
    void IS_LESS() {
        LambdaExpression isLess_2_1 = ApplicationUtil.generateApplicationRightMost(Comparator.IS_LESS, Numbers.TWO, Numbers.ONE);
        assertTrue(
                Booleans.FALSE.isSameStructureWithoutReduction(
                        Evaluation.execute(isLess_2_1, null)
                )
        );

        LambdaExpression isLess_1_1 = ApplicationUtil.generateApplicationRightMost(Comparator.IS_LESS, Numbers.ONE, Numbers.ONE);
        assertTrue(
                Booleans.FALSE.isSameStructureWithoutReduction(
                        Evaluation.execute(isLess_1_1, null)
                )
        );

        LambdaExpression isLess_1_2 = ApplicationUtil.generateApplicationRightMost(Comparator.IS_LESS, Numbers.ONE, Numbers.TWO);
        assertTrue(
                Booleans.TRUE.isSameStructureWithoutReduction(
                        Evaluation.execute(isLess_1_2, null)
                )
        );
    }

    @Test
    void IS_EQUAL() {
        // 1 和 2 进行比较
        LambdaExpression notEqual = ApplicationUtil.generateApplicationRightMost(Comparator.IS_EQUAL, Numbers.ONE, Numbers.TWO);
        assertTrue(
                Booleans.FALSE.isSameStructureWithoutReduction(
                        Evaluation.execute(notEqual, null)
                )
        );

        // 2 和 2 进行比较
        LambdaExpression equal = ApplicationUtil.generateApplicationRightMost(Comparator.IS_EQUAL, Numbers.TWO, Numbers.TWO);
        assertTrue(
                Booleans.TRUE.isSameStructureWithoutReduction(
                        Evaluation.execute(equal, null)
                )
        );
    }
}