package com.github.anilople.dog.frontend.parser;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.lambda.Function;
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
    void pParseEvaluationNestedFunction() {
        List<Evaluation> evaluations = Parser.parse("(F[c -> {c}])");
        for(Evaluation evaluation : evaluations) {
            System.out.println(evaluation);
        }
        assertEquals(1, evaluations.size());
    }

}