package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.util.ApplicationUtil;

public class Converter {

    /**
     * {@link LambdaExpression}转为其表达的自然数 0, 1, 2, 3, ...
     * @param integer 表达式
     * @return 自然数
     */
    public static int toJavaInt(LambdaExpression integer) {
        Application number = ApplicationUtil.generateApplicationRightMost(
                integer,
                Functions.SUCCESSOR,
                Numbers.ZERO
        );
        return Integer.parseInt(Evaluation.execute(number, null).toString());
    }

}
