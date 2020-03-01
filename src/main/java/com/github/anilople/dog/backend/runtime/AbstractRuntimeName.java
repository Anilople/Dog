package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;

import java.util.Collections;
import java.util.Set;

/**
 * 求值时使用的 变量
 */
public abstract class AbstractRuntimeName extends Name {

    public AbstractRuntimeName(String name) {
        super(name);
    }

    @Override
    public LambdaExpression replaceFreeVariable(Name name, LambdaExpression replacement) {
        return this;
    }

    @Override
    public abstract boolean isReducible(Context context);

    @Override
    public Set<Name> getAllSymbols() {
        return Collections.emptySet();
    }

    @Override
    public Name changeSymbol(Name source, Name target) {
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
}
