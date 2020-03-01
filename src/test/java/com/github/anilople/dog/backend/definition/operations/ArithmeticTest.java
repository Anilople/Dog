package com.github.anilople.dog.backend.definition.operations;

import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.definition.Functions;
import com.github.anilople.dog.backend.definition.Numbers;
import com.github.anilople.dog.backend.runtime.Converter;
import com.github.anilople.dog.backend.util.ApplicationUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticTest {

    private static final LambdaExpression three = ApplicationUtil.generateApplicationFromLeft(
            Arithmetic.ADD,
            Numbers.ONE,
            Numbers.TWO
    );

    @Test
    void predecessor() {
        LambdaExpression zero = new Application(Arithmetic.PREDECESSOR, Numbers.ONE);
        assertEquals(0, Converter.toJavaInt(zero));

        LambdaExpression one = new Application(Arithmetic.PREDECESSOR, Numbers.TWO);
        assertEquals(1, Converter.toJavaInt(one));
    }

    /**
     * 减法测试
     */
    @Test
    void subtract() {
        LambdaExpression one = ApplicationUtil.generateApplicationFromLeft(
                Arithmetic.SUB,
                Numbers.TWO,
                Numbers.ONE
        );
        assertEquals(1, Converter.toJavaInt(one));
    }

    /**
     * 阶乘测试
     */
    @Test
    void FACTORIAL() {
        // Y组合子结合阶乘函数，计算出1*2*3=6
        LambdaExpression six = ApplicationUtil.generateApplicationFromLeft(
                Functions.Y, Arithmetic.FACTORIAL, three
        );

        assertEquals(6, Converter.toJavaInt(six));
    }

    @Test
    void TO_ZERO() {
        LambdaExpression zero = ApplicationUtil.generateApplicationFromLeft(
                Functions.Y, Arithmetic.TO_ZERO, Numbers.TWO
        );
        assertEquals(0, Converter.toJavaInt(zero));
    }

}