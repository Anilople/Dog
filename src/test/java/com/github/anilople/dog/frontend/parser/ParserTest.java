package com.github.anilople.dog.frontend.parser;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.StringName;
import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.frontend.lexer.Lexer;
import com.github.anilople.dog.frontend.lexer.input.Token;
import com.github.anilople.dog.util.Pair;
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
    void tryParseEvaluation() {
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
    void tryParseEvaluationNestedFunction() {
        List<Evaluation> evaluations = Parser.parse("(F[c -> {c}])");
        for(Evaluation evaluation : evaluations) {
            System.out.println(evaluation);
        }
        assertEquals(1, evaluations.size());
    }

    @Test
    void tryParseLambdaExpression() {
    }

    @Test
    void tryParseApplication() {
        List<Token> tokens = Lexer.tokenize("x[y]");
        System.out.println(tokens);
        assertEquals(4, tokens.size());

        Pair<Application, Integer> tryApplication = Parser.tryParseApplication(tokens, 0);
        final Application application = tryApplication.getFirst();
        System.out.println(application);
        assert application != null;
    }

    /**
     * 2个参数的{@link Application}
     */
    @Test
    void tryParseApplicationTwo() {
        List<Token> tokens = Lexer.tokenize("x[y][z]");
        System.out.println(tokens);
        assertEquals(7, tokens.size());

        Pair<Application, Integer> tryApplication = Parser.tryParseApplication(tokens, 0);
        final Application application = tryApplication.getFirst();
        System.out.println(application);
        assert application != null;
    }

    @Test
    void tryParseFunction() {
        List<Token> tokens = Lexer.tokenize("x -> { y }");
        System.out.println(tokens);
        assertEquals(5, tokens.size());

        Pair<Function, Integer> tryFunction = Parser.tryParseFunction(tokens, 0);
        final Function function = tryFunction.getFirst();
        System.out.println(function);
        assert function != null;
    }

    @Test
    void tryParseFunctionNested() {
        List<Token> tokens = Lexer.tokenize("x -> { y -> { x } }");
        System.out.println(tokens);

        Pair<Function, Integer> tryFunction = Parser.tryParseFunction(tokens, 0);
        final Function function = tryFunction.getFirst();
        System.out.println(function);
        assert function != null;
    }

    @Test
    void parseName() {
        List<Token> tokens0 = Lexer.tokenize("apple");
        System.out.println(tokens0);
        assertEquals(1, tokens0.size());
        Pair<Name, Integer> nameResult0 = Parser.parseName(tokens0, 0);
        System.out.println(nameResult0);

        List<Token> tokens1 = Lexer.tokenize("Apple");
        System.out.println(tokens1);
        assertEquals(1, tokens1.size());
        Pair<Name, Integer> nameResult1 = Parser.parseName(tokens1, 0);
        System.out.println(nameResult1);
    }

    @Test
    void parseArguments() {
        List<Token> tokens = Lexer.tokenize("[ A ] [ B ] [c]");
        System.out.println(tokens);
        assertEquals(9, tokens.size());
        Pair<List<LambdaExpression>, Integer> tryArguments = Parser.tryParseArguments(tokens, 0);
        final List<LambdaExpression> lambdaExpressions = tryArguments.getFirst();
        final Integer context = tryArguments.getSecond();
        System.out.println(lambdaExpressions);
        System.out.println(context);
        assertEquals(3, lambdaExpressions.size());
    }

    @Test
    void tryParseArgument() {
        List<Token> tokens = Lexer.tokenize("[ a ]");
        System.out.println(tokens);
        assertEquals(3, tokens.size());
        Pair<LambdaExpression, Integer> tryArgument = Parser.tryParseArgument(tokens, 0);
        final LambdaExpression lambdaExpression = tryArgument.getFirst();
        final Integer context = tryArgument.getSecond();
        System.out.println(lambdaExpression);
        System.out.println(context);
    }
}