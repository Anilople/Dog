package com.github.anilople.dog.frontend.parser;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.ast.StringName;
import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.util.ApplicationUtil;
import com.github.anilople.dog.frontend.lexer.Lexer;
import com.github.anilople.dog.frontend.lexer.input.Token;
import com.github.anilople.dog.frontend.lexer.input.token.*;
import com.github.anilople.dog.frontend.lexer.input.token.Number;
import com.github.anilople.dog.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parser {
    
    public static List<Evaluation> parse(String text) {
        List<Token> tokens = Lexer.tokenize(text);
        return parse(tokens);
    }

    /**
     * @return {@link List<Evaluation>}不可变
     */
    public static List<Evaluation> parse(List<Token> tokens) {
        final List<Evaluation> evaluations = new ArrayList<>();
        
        int index = 0;
        while (index < tokens.size()) {
            Pair<Evaluation, Integer> result = tryParseEvaluation(tokens, index);
            if(index == result.getSecond()) {
                // parse 失败
                break;
            }
            // 拿出evaluation
            Evaluation evaluation = result.getFirst();
            // 添加evaluation
            evaluations.add(evaluation);
            // 更新
            index =  result.getSecond();
        }
        
        if(index < tokens.size()) {
            throw new RuntimeException("未解析完成，截止 " + index + " token = " + tokens.get(index) + ", tokens = " + tokens);
        }
        return Collections.unmodifiableList(evaluations);
    }

    /**
     * @see Evaluation
     */
    static Pair<Evaluation, Integer> tryParseEvaluation(final List<Token> tokens, final int startIndex) {
        if(startIndex >= tokens.size()) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }

        int index = startIndex;
        // skip (
        if(! (tokens.get(index) instanceof LeftParenthesis)) {
            return new Pair<>(null, startIndex);
        }
        index += 1;

        // parse next
        Pair<LambdaExpression, Integer> tryLambdaExpression = tryParseLambdaExpression(tokens, index);
        if(tryLambdaExpression.getSecond() == index) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }
        final LambdaExpression lambdaExpression = tryLambdaExpression.getFirst();
        index = tryLambdaExpression.getSecond();

        // skip )
        if(! (tokens.get(index) instanceof RightParenthesis)) {
            return new Pair<>(null, startIndex);
        }
        index += 1;

        final Evaluation evaluation = new Evaluation(lambdaExpression);
        return new Pair<>(evaluation, index);
    }

    /**
     * 为了在工程上防止自己写错代码，限制变量的作用域，
     * 所以使用了代码块，虽然看起来很丑，但是有点防止手残的用处
     * @see LambdaExpression
     */
    static Pair<LambdaExpression, Integer> tryParseLambdaExpression(final List<Token> tokens, final int startIndex) {
        {
            final Pair<Application, Integer> tryApplication = tryParseApplication(tokens, startIndex);
            if(tryApplication.getSecond() != startIndex) {
                // parse成功
                return new Pair<>(tryApplication.getFirst(), tryApplication.getSecond());
            }
        }

        {
            final Pair<Function, Integer> tryFunction = tryParseFunction(tokens, startIndex);
            if(tryFunction.getSecond() != startIndex) {
                // parse成功
                return new Pair<>(tryFunction.getFirst(), tryFunction.getSecond());
            }
        }

        {
            final Pair<Name, Integer> tryName = parseName(tokens, startIndex);
            if(tryName.getSecond() != startIndex) {
                // parse成功
                return new Pair<>(tryName.getFirst(), tryName.getSecond());
            }
        }

        // parse 失败
        return new Pair<>(null, startIndex);
    }

    /**
     * @see Application
     */
    static Pair<Application, Integer> tryParseApplication(final List<Token> tokens, final int startIndex) {
        final LambdaExpression first;
        final Integer index;
        Pair<Function, Integer> tryFunction = tryParseFunction(tokens, startIndex);
        if(tryFunction.getSecond() != startIndex) {
            // parse成功
            first = tryFunction.getFirst();
            index = tryFunction.getSecond();
        } else {
            Pair<Name, Integer> tryName = parseName(tokens, startIndex);
            if(tryName.getSecond() != startIndex) {
                // parse成功
                first = tryName.getFirst();
                index = tryName.getSecond();
            } else {
                // parse 是吧
                return new Pair<>(null, startIndex);
            }
        }

        // parse 剩下的部分
        Pair<List<LambdaExpression>, Integer> tryArguments = tryParseArguments(tokens, index);
        if(tryArguments.getSecond().equals(index)) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }

        // 创建数组参数
        LambdaExpression[] lambdaExpressions = new LambdaExpression[tryArguments.getFirst().size() + 1];
        lambdaExpressions[0] = first;
        for(int i = 0; i < tryArguments.getFirst().size(); i++) {
            lambdaExpressions[i + 1] = tryArguments.getFirst().get(i);
        }
        // 构建
        Application application = ApplicationUtil.generateApplicationRightMost(lambdaExpressions);
        return new Pair<>(application, tryArguments.getSecond());
    }

    /**
     * @see Function
     */
    static Pair<Function, Integer> tryParseFunction(final List<Token> tokens, final int startIndex) {
        Pair<Name, Integer> tryName = parseName(tokens, startIndex);
        if(tryName.getSecond() == startIndex) {
            //  parse 失败
            return new Pair<>(null, startIndex);
        }
        final Name name = tryName.getFirst();
        int index = tryName.getSecond();
        
        // skip ->
        if(! (tokens.get(index) instanceof FunctionArrow)) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }
        index += 1;
        
        // skip '{'
        if(! (tokens.get(index) instanceof LeftBigParenthesis)) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }
        index += 1;

        // parse next
        Pair<LambdaExpression, Integer> tryLambdaExpression = tryParseLambdaExpression(tokens, index);
        if(tryLambdaExpression.getSecond() == index) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }
        final LambdaExpression lambdaExpression = tryLambdaExpression.getFirst();
        index = tryLambdaExpression.getSecond();
        
        // skip '}'
        if(! (tokens.get(index) instanceof RightBigParenthesis)) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }
        index += 1;

        Function function = new Function(name, lambdaExpression);
        return new Pair<>(function, index);
    }

    /**
     * @see Name
     */
    static Pair<Name, Integer> parseName(final List<Token> tokens, final int startIndex) {
        if(startIndex >= tokens.size()) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }
        int index = startIndex;
        Token now = tokens.get(index);
        index += 1;
        if(now instanceof DogString) {
            StringName stringName = new StringName(now.getContent());
            return new Pair<>(stringName, index);
        } else if(now instanceof Variable) {
            VariableName variableName = new VariableName(now.getContent());
            return new Pair<>(variableName, index);
        } else if(now instanceof Number) {
            NumberName numberName = new NumberName(now.getContent());
            return new Pair<>(numberName, index);
        } else {
            throw new RuntimeException("parse 失败" + now);
        }
    }

    static Pair<List<LambdaExpression>, Integer> tryParseArguments(final List<Token> tokens, final int startIndex) {

        Pair<LambdaExpression, Integer> tryArgumentFirst = tryParseArgument(tokens, startIndex);
        if(tryArgumentFirst.getSecond() == startIndex) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }
        
        final List<LambdaExpression> lambdaExpressions = new ArrayList<>();
        lambdaExpressions.add(tryArgumentFirst.getFirst());
        
        int index = tryArgumentFirst.getSecond();
        // parse 余下的
        for(
                Pair<LambdaExpression, Integer> tryArgument = tryParseArgument(tokens, index);
                tryArgument.getSecond() != index;
                tryArgument = tryParseArgument(tokens, index)
        ) {
            LambdaExpression lambdaExpression = tryArgument.getFirst();
            lambdaExpressions.add(lambdaExpression);
            // 更新外部变量
            index = tryArgument.getSecond();
        }
        
        return new Pair<>(lambdaExpressions, index);
    }
    
    static Pair<LambdaExpression, Integer> tryParseArgument(final List<Token> tokens, final int startIndex) {
        if(startIndex >= tokens.size()) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }

        int index = startIndex;
        // skip '['
        if(! (tokens.get(index) instanceof LeftBrackets)) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }
        index += 1;

        Pair<LambdaExpression, Integer> tryLambdaExpression = tryParseLambdaExpression(tokens, index);
        if(tryLambdaExpression.getSecond() == index) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }
        final LambdaExpression lambdaExpression = tryLambdaExpression.getFirst();
        index = tryLambdaExpression.getSecond();

        // skip ']'
        if(! (tokens.get(index) instanceof RightBrackets)) {
            // parse 失败
            return new Pair<>(null, startIndex);
        }
        index += 1;
        
        return new Pair<>(lambdaExpression, index);
    }
}
