package com.github.anilople.dog.backend.runtime.metafunction.output;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 无换行符，
 * 直接输出
 */
public class Print extends AbstractRuntimeFunction {

    private static final Print PRINT = new Print(null, null);

    public static final Print getInstance() {
        return PRINT;
    }

    private Print(Name argument, LambdaExpression body) {
        super(argument, body);
    }

    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
        LambdaExpression lambdaExpression = Evaluation.execute(replacement, context);
        System.out.print(lambdaExpression);
        return this;
    }

    @Override
    public String toString() {
        return "x -> { Print[x] }";
    }
}
