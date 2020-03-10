package com.github.anilople.dog.backend.runtime.metafunction;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.NullName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.definition.Booleans;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 检查是否是{@link NullName}
 * @see NullName
 */
public class IsNullName extends AbstractRuntimeFunction {

    private static final IsNullName INSTANCE = new IsNullName(null, null);

    public static IsNullName getInstance() {
        return INSTANCE;
    }

    public IsNullName(Name argument, LambdaExpression body) {
        super(argument, body);
    }

    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
        // 不断reduce
        LambdaExpression result = Evaluation.execute(replacement, context);
        if(NullName.getInstance().equals(result)) {
            return Booleans.TRUE;
        } else {
            return Booleans.FALSE;
        }
    }

    @Override
    public String toString() {
        return "x -> { x == Null }";
    }
}
