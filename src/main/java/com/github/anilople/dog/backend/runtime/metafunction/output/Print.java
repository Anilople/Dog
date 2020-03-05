package com.github.anilople.dog.backend.runtime.metafunction.output;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.VariableName;
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

    /**
     * 输出换行{@link System#lineSeparator()}
     */
    public static void newLine() {
        System.out.println();
    }

    private Print(Name argument, LambdaExpression body) {
        super(argument, body);
    }

    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
        // 不断规约，直到不是一个 引用
        LambdaExpression result = Evaluation.reduceUntil(
                replacement,
                context,
                lambdaExpression -> ! (lambdaExpression instanceof VariableName)
        );
        System.out.print(result);
        return null;
    }

    @Override
    public String toString() {
        return "x -> { Print[x] }";
    }
}
