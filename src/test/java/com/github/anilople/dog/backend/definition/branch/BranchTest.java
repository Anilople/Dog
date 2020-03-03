package com.github.anilople.dog.backend.definition.branch;

import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.definition.Numbers;
import com.github.anilople.dog.backend.definition.operations.TypeConverter;
import com.github.anilople.dog.backend.runtime.Converter;
import com.github.anilople.dog.backend.util.ApplicationUtil;
import org.junit.jupiter.api.Test;

import static com.github.anilople.dog.backend.definition.branch.Branch.IF;
import static org.junit.jupiter.api.Assertions.*;

class BranchTest {

    @Test
    void ifTest() {
        LambdaExpression lambdaExpression = ApplicationUtil.generateApplicationRightMost(
                IF, new Application(TypeConverter.IS_ZERO, Numbers.TWO),
                Numbers.ONE,
                Numbers.TWO
        );
        assertEquals(2, Converter.toJavaInt(lambdaExpression));
    }

}