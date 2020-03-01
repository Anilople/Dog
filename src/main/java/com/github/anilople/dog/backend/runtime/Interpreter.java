package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Reducible;
import com.github.anilople.dog.frontend.parser.Parser;

import java.util.List;

/**
 * 解释器
 */
public class Interpreter {

    /**
     * 不断规约
     * @param lambdaExpression {@link LambdaExpression}表达式
     * @return 无法继续规约的表达式
     */
    public static LambdaExpression interpret(LambdaExpression lambdaExpression) {
//        System.out.println(lambdaExpression);
        Context metaContext = MetaContextEnvironment.getMetaContext();
        while(lambdaExpression.isReducible(metaContext)) {
            Reducible reducible = (Reducible) lambdaExpression;
            // 更新表达式
            lambdaExpression = reducible.reduce(metaContext);
//            System.out.println(lambdaExpression);
        }
        return lambdaExpression;
    }

    /**
     * 每次都重新初始化{@link Context}
     * @param text 要解释的文本
     */
    public static void interpret(String text) {
        final Context metaContext = MetaContextEnvironment.getMetaContext();
        List<Evaluation> evaluations = Parser.parse(text);
        for(Evaluation evaluation : evaluations) {
            evaluation.execute(metaContext);
        }
    }

    /**
     *
     * @param text 要解释的文本
     * @param context 解释时的系统环境
     */
    public static void interpret(String text, Context context) {
        List<Evaluation> evaluations = Parser.parse(text);
        for(Evaluation evaluation : evaluations) {
            evaluation.execute(context);
        }
    }
}
