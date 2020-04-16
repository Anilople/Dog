package com.github.anilople.dog.backend.ast.lambda;

import com.github.anilople.dog.backend.runtime.Context;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * lambda的变量
 */
public class Name extends LambdaExpression {

    /**
     * 变量名，字面上的字符串
     */
    private final String literals;

    public Name(String literals) {
        this.literals = literals;
    }

    /**
     * 会将变量变成其它表达式
     * 例如
     * x 变成 y          （变量换成另一个变量）
     * z 变成 a -> b     （变量变成函数）
     * u 变成 f(n)       （变量变成函数调用）
     */
    @Override
    public LambdaExpression replaceFreeVariable(Name name, LambdaExpression replacement) {
        if (this.equals(name)) {
            return replacement;
        } else {
            // 变量不一样，不用替换
            return this;
        }
    }

    @Override
    public boolean isReducible(Context context) {
        return this instanceof Reducible;
    }

    @Override
    public String toString() {
        return literals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return getLiterals().equals(name.getLiterals());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLiterals());
    }

    public String getLiterals() {
        return literals;
    }

    @Override
    public Set<Name> getAllSymbols() {
        return Collections.singleton(this);
    }

    @Override
    public Name changeSymbol(Name source, Name target) {
        return this.equals(source) ? target : this;
    }

    @Override
    public boolean isSameStructureWithoutReduction(LambdaExpression lambdaExpression) {
        if(lambdaExpression instanceof Name) {
            // 类型必须相同
            Name that = (Name) lambdaExpression;
            // 字面值也必须相同
            return this.equals(that);
        } else {
            return false;
        }
    }

    @Override
    public boolean isFree(Name name) {
        return true;
    }

    @Override
    public boolean isBound(Name name) {
        return false;
    }

    @Override
    public Set<Name> getFreeVariables() {
        return Collections.singleton(this);
    }

    @Override
    public Set<Name> getBoundVariables() {
        return Collections.emptySet();
    }
}
