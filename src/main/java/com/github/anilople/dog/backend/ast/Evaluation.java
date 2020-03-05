package com.github.anilople.dog.backend.ast;

import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Reducible;
import com.github.anilople.dog.backend.runtime.Context;

import java.util.function.BiFunction;
import java.util.function.Function;

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
     * 不断规约
     * @param lambdaExpression {@link LambdaExpression}表达式
     * @param context 规约时的系统环境{@link Context}
     * @return 无法继续规约的表达式
     */
    public static LambdaExpression execute(LambdaExpression lambdaExpression, Context context) {
        while(null != lambdaExpression && lambdaExpression.isReducible(context)) {
            Reducible reducible = (Reducible) lambdaExpression;
            // 更新表达式
            lambdaExpression = reducible.reduce(context);
        }
        return lambdaExpression;
    }

    /**
     * 一直规约，直到条件满足
     * @param booleanBiFunction 条件
     * @return 满足条件后的表达式
     */
    public static LambdaExpression reduceUntil(
            LambdaExpression lambdaExpression,
            Context context,
            BiFunction<LambdaExpression, Context, Boolean> booleanBiFunction
    ) {
        while(null != lambdaExpression
                && lambdaExpression.isReducible(context)
                && !booleanBiFunction.apply(lambdaExpression, context)
        ) {
            Reducible reducible = (Reducible) lambdaExpression;
            // 更新表达式
            lambdaExpression = reducible.reduce(context);
        }
        return lambdaExpression;
    }

    /**
     * 一直规约，直到条件满足
     * @param function 条件
     * @return 满足条件后的表达式
     */
    public static LambdaExpression reduceUntil(
            LambdaExpression lambdaExpression,
            Context context,
            Function<LambdaExpression, Boolean> function
    ) {
        while(null != lambdaExpression
                && lambdaExpression.isReducible(context)
                && !function.apply(lambdaExpression)
        ) {
            Reducible reducible = (Reducible) lambdaExpression;
            // 更新表达式
            lambdaExpression = reducible.reduce(context);
        }
        return lambdaExpression;
    }

    /**
     * 不断规约，直到{@link LambdaExpression}的类型
     * 是所给类型的子类，或者和所给类型相同
     * @param tClass 给定的类型对应的{@link java.lang.Class}
     * @param <T> 类型
     * @return 对应类型的对象
     */
    public static <T extends LambdaExpression> T reduceToConformType(
            LambdaExpression lambdaExpression,
            Context context,
            Class<T> tClass
    ) {
        while(null != lambdaExpression
                && lambdaExpression.isReducible(context)
                && ! tClass.isAssignableFrom(lambdaExpression.getClass())
        ) {
            Reducible reducible = (Reducible) lambdaExpression;
            // 更新表达式
            lambdaExpression = reducible.reduce(context);
        }
        return tClass.cast(lambdaExpression);
    }

    @Override
    public String toString() {
        return "(" + lambdaExpression + ")";
    }

    public LambdaExpression getLambdaExpression() {
        return lambdaExpression;
    }
}
