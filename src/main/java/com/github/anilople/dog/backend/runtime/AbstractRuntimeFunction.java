package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.ast.lambda.Name;

import java.util.Collections;
import java.util.Set;

/**
 * 求值时使用的函数
 */
public abstract class AbstractRuntimeFunction extends Function {

    public AbstractRuntimeFunction(Name argument, LambdaExpression body) {
        super(argument, body);
    }

    /**
     * 运行环境的函数比较特殊，需要有自己的call方法
     */
    @Override
    public abstract LambdaExpression call(LambdaExpression replacement, Context context);

    @Override
    public LambdaExpression replaceFreeVariable(Name name, LambdaExpression replacement) {
        return this;
    }

    @Override
    public Set<Name> getAllSymbols() {
        return Collections.emptySet();
    }

    @Override
    public Function changeSymbol(Name source, Name target) {
        return this;
    }

    @Override
    public boolean isSameStructureWithoutReduction(LambdaExpression lambdaExpression) {
        return false;
    }

    @Override
    public boolean isFree(Name name) {
        return false;
    }

    @Override
    public boolean isBound(Name name) {
        return false;
    }

    @Override
    public Set<Name> getFreeVariables() {
        return Collections.emptySet();
    }

    @Override
    public Set<Name> getBoundVariables() {
        return Collections.emptySet();
    }

    @Override
    public abstract String toString();

}
