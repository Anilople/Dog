package com.github.anilople.dog.frontend.parser;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse() {
        final String text = "(Bind[ b -> { b }][c   ])" +
                " (IF[a])";
        List<Evaluation> evaluations = Parser.parse(text);
        System.out.println(evaluations);
    }

    @Test
    void parseEvaluation() {
        List<Evaluation> evaluations = Parser.parse("(F[b][c][d])");
        for(Evaluation evaluation : evaluations) {
            System.out.println(evaluation);
        }
        assertEquals(1, evaluations.size());
    }

    /**
     * 内嵌{@link Function}
     */
    @Test
    void parseEvaluationNestedFunction() {
        List<Evaluation> evaluations = Parser.parse("(F[c -> {c}])");
        for(Evaluation evaluation : evaluations) {
            System.out.println(evaluation);
        }
        assertEquals(1, evaluations.size());
    }

    @Test
    void functionNested() {
        final String text = "(x -> { y -> { x[y][z] }})";
        List<Evaluation> evaluations = Parser.parse(text);
        System.out.println(evaluations);
        assertEquals(1, evaluations.size());
        Evaluation evaluation = evaluations.get(0);
        System.out.println(evaluation);
        LambdaExpression lambdaExpression = evaluation.getLambdaExpression();

        // x -> { ... }
        Function x = (Function) lambdaExpression;
        assertEquals("x", x.getArgument().getLiterals());

        // y -> { ... }
        Function y = (Function) x.getBody();
        assertEquals("y", y.getArgument().getLiterals());

        // x[y][z]
        Application xyz = (Application) y.getBody();
    }

    @Test
    void bindPlus() {
        final String text = "(Bind[Plus][Add[1]])";
        /*
                      0
                   /      \
                  0        0
                /   \     /  \
              Bind  Plus Add  1
         */

        List<Evaluation> evaluations = Parser.parse(text);
        System.out.println(evaluations);
        assertEquals(1, evaluations.size());

        Application all = (Application) evaluations.get(0).getLambdaExpression();

        Application bindPlus = (Application) all.getLeft();
        System.out.println(bindPlus);
        assertEquals("Bind", bindPlus.getLeft().toString());
        assertEquals("Plus", bindPlus.getRight().toString());

        Application add1 = (Application) all.getRight();
        System.out.println(add1);
        assertEquals("Add", add1.getLeft().toString());
        assertEquals("1", add1.getRight().toString());

    }
}