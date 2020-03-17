package com.github.anilople.dog.backend.ast.number;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.runtime.Context;
import com.github.anilople.dog.backend.runtime.Interpreter;
import com.github.anilople.dog.backend.runtime.MetaContextEnvironment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NaturalNameTest {

    /**
     * 让Plus只作用0次，
     * 也就是不进行任何作用
     */
    @Test
    void zero() {
        Context context = new Context();

        final String text = "(Bind[Plus][Add[1]])" + System.lineSeparator() +
                "(Bind[Five][0[Plus][5]])";

        Interpreter.interpret(text, context);

        LambdaExpression five = Evaluation.reduceUntil(
                context.get(new VariableName("Five")),
                context,
                lambdaExpression -> lambdaExpression instanceof NumberName
        );
        NumberName numberName = (NumberName) five;
        assertEquals("5", numberName.getLiterals());

    }

    /**
     * 让Plus6这个操作作用2次
     */
    @Test
    void two() {
        Context context = new Context();

        final String text = "(Bind[Plus6][Add[6]])" +
                "(Bind[Number15][2[Plus6][3]])" +
                "(Print[Number15])";

        Interpreter.interpret(text, context);

        LambdaExpression number15 = Evaluation.reduceUntil(
                context.get(new VariableName("Number15")),
                context,
                lambdaExpression -> lambdaExpression instanceof NumberName
        );
        NumberName numberName = (NumberName) number15;
        assertEquals("15", numberName.getLiterals());
    }
}