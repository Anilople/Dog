package com.github.anilople.dog.backend.ast;

import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Reducible;
import com.github.anilople.dog.backend.runtime.Context;

public class Evaluation {

    private final LambdaExpression lambdaExpression;

    public Evaluation(LambdaExpression lambdaExpression) {
        this.lambdaExpression = lambdaExpression;
    }

    /**
     * 语句的执行会影响系统的状态
     * @param context 系统的状态
     */
    public LambdaExpression execute(Context context) {
        return execute(lambdaExpression, context);
    }

    /**
     * 不断reduce
     */
    public static LambdaExpression execute(LambdaExpression lambdaExpression, Context context) {
        while(null != lambdaExpression && lambdaExpression.isReducible(context)) {
            Reducible reducible = (Reducible) lambdaExpression;
            // 更新表达式
            lambdaExpression = reducible.reduce(context);
        }
        return lambdaExpression;
    }

    @Override
    public String toString() {
        return "(" + lambdaExpression + ")";
    }

    public LambdaExpression getLambdaExpression() {
        return lambdaExpression;
    }
}