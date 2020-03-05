package com.github.anilople.dog.backend.runtime.metafunction.output;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 输出一行，额外附加换行符
 * 参考{@link java.io.PrintStream#println(String)}
 */
public class PrintLine extends AbstractRuntimeFunction {

    private static final PrintLine PRINT_LINE = new PrintLine(null, null);

    public static final PrintLine getInstance() {
        return PRINT_LINE;
    }

    private PrintLine(Name argument, LambdaExpression body) {
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
        System.out.println(result);
        return null;
    }

    @Override
    public String toString() {
        return "x -> { PrintLine[x] }";
    }
}
