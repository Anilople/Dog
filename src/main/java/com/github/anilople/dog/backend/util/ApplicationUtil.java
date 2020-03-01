package com.github.anilople.dog.backend.util;

import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;

import java.util.Arrays;

/**
 * @see Application
 */
public class ApplicationUtil {

    /**
     * 为了方便构造{@link Application}
     * 例子：
     *      generateApplicationFromLeft(s, s, z)  -> ssz，即 ((s)s)z)
     * @param lambdaExpressions 一串lambda表达式
     * @return 左结合的调用
     * @throws IllegalArgumentException 如果 表达式的数量不能为小于2个
     */
    public static Application generateApplicationFromLeft(LambdaExpression... lambdaExpressions) {
        if(lambdaExpressions.length < 2) {
            throw new IllegalArgumentException("表达式的数量不能为小于2个");
        }
        if(lambdaExpressions.length == 2) {
            return new Application(lambdaExpressions[0], lambdaExpressions[1]);
        }
        // 去除最后一个
        LambdaExpression init = generateApplicationFromLeft(Arrays.copyOf(lambdaExpressions, lambdaExpressions.length - 1));

        // 最后一个
        LambdaExpression last = lambdaExpressions[lambdaExpressions.length - 1];

        return new Application(init, last);
    }

    /**
     * 例子：
     *      generateApplicationFromLeft(s, s, z)  -> (s(s(z)))
     * @param lambdaExpressions 一串lambda表达式
     * @return 右结合的调用
     * @throws IllegalArgumentException 如果 表达式的数量不能为小于2个
     */
    public static Application generateApplicationFromRight(LambdaExpression... lambdaExpressions) {
        if(lambdaExpressions.length < 2) {
            throw new IllegalArgumentException("表达式的数量不能为小于2个");
        }
        if(lambdaExpressions.length == 2) {
            return new Application(lambdaExpressions[0], lambdaExpressions[1]);
        }

        // 头
        final LambdaExpression head = lambdaExpressions[0];
        // 余下部分
        final LambdaExpression tail = generateApplicationFromLeft(
                Arrays.copyOfRange(
                        lambdaExpressions,
                        1,
                        lambdaExpressions.length
                )
        );
        return new Application(head, tail);
    }

}