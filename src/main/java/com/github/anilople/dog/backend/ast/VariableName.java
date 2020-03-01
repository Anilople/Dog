package com.github.anilople.dog.backend.ast;

import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.ast.lambda.Reducible;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 变量代表对其它{@link LambdaExpression}的引用
 */
public class VariableName extends Name implements Reducible {

    public VariableName(String name) {
        super(name);
    }

    @Override
    public boolean isReducible(Context context) {
        return context.exists(this);
    }

    @Override
    public LambdaExpression reduce(Context context) {
        return context.get(this);
    }
}
