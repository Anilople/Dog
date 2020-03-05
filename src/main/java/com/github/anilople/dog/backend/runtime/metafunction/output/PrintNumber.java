package com.github.anilople.dog.backend.runtime.metafunction.output;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * @see NumberName
 */
public class PrintNumber extends AbstractRuntimeFunction {

    private static final PrintNumber PRINT_NUMBER = new PrintNumber(null, null);

    public static PrintNumber getInstance() {
        return PRINT_NUMBER;
    }

    private PrintNumber(Name argument, LambdaExpression body) {
        super(argument, body);
    }

    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
        // 不断规约，直到 变成 数字
        NumberName numberName = Evaluation.reduceToConformType(
                replacement,
                context,
                NumberName.class
        );
        Print.getInstance().call(numberName, context);
        return null;
    }

    @Override
    public String toString() {
        return "x -> { PrintNumber[x] }";
    }

}
