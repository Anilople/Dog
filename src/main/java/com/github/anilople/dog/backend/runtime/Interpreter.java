package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.frontend.parser.Parser;
import java.util.List;

/**
 * 解释器
 */
public class Interpreter {

    /**
     * 每次都重新初始化{@link Context}
     * @param text 要解释的文本
     */
    public static LambdaExpression interpret(String text) {
        final Context metaContext = new Context();
        List<Evaluation> evaluations = Parser.parse(text);
        LambdaExpression result = null;
        for(Evaluation evaluation : evaluations) {
            result = evaluation.execute(metaContext);
        }
        return result;
    }

    /**
     *
     * @param text 要解释的文本
     * @param context 解释时的系统环境
     */
    public static LambdaExpression interpret(String text, Context context) {
        List<Evaluation> evaluations = Parser.parse(text);
        LambdaExpression result = null;
        for(Evaluation evaluation : evaluations) {
            result = evaluation.execute(context);
        }
        return result;
    }
}
