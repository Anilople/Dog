package com.github.anilople.dog.backend.ast.lambda;

import com.github.anilople.dog.backend.runtime.Context;
import com.github.anilople.dog.util.SetUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Application extends LambdaExpression implements Reducible {

    private final LambdaExpression left;

    private final LambdaExpression right;

    public Application(LambdaExpression left, LambdaExpression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * 对于调用来说，替换会同时作用在左边和右边上
     */
    @Override
    public LambdaExpression replaceFreeVariable(Name name, LambdaExpression replacement) {
        LambdaExpression newLeft = left.replaceFreeVariable(name, replacement);
        LambdaExpression newRight = right.replaceFreeVariable(name, replacement);
        return new Application(newLeft, newRight);
    }

    @Override
    public boolean isReducible(Context context) {
        return left.isReducible(context) || right.isReducible(context) || left.isCallable();
    }

    @Override
    public LambdaExpression reduce(Context context) {
        if (left.isReducible(context)) {
            // reduce left
            Reducible reducible = (Reducible) left;
            LambdaExpression leftAfterReduceOneStep = reducible.reduce(context);
            return new Application(leftAfterReduceOneStep, right);
        } else {
            // left无法reduce了
            // 说明left现在应该是一个函数了
            // 注意求值顺序，当左边无法求值时，
            // 此时不能直接去求值右边的，否则Y组合子会死循环
            // 应该进行一次apply(call)了
            Function function = (Function) left;
            return function.call(right, context);
        }
    }

    @Override
    public String toString() {
        final String rightPart = "[" + right.toString() + "]";
        if(left instanceof Name || left instanceof Application) {
            final String leftPart = left.toString();
            return leftPart + rightPart;
        } else if(left instanceof Function) {
            final String leftPart = "(" + left.toString() + ")";
            return leftPart + rightPart;
        } else {
            throw new RuntimeException("无法预知的类型" + this.getClass());
        }
    }

    public LambdaExpression getLeft() {
        return left;
    }

    public LambdaExpression getRight() {
        return right;
    }

    @Override
    public Set<Name> getAllSymbols() {
        Set<Name> names = new HashSet<>();
        // 添加左边
        names.addAll(left.getAllSymbols());
        // 添加右边
        names.addAll(right.getAllSymbols());
        return Collections.unmodifiableSet(names);
    }

    @Override
    public Application changeSymbol(Name source, Name target) {
        LambdaExpression newLeft = left.changeSymbol(source, target);
        LambdaExpression newRight = right.changeSymbol(source, target);
        return new Application(newLeft, newRight);
    }

    @Override
    public boolean isSameStructureWithoutReduction(LambdaExpression lambdaExpression) {
        if(! (lambdaExpression instanceof Application)) {
            return false;
        }
        Application that = (Application) lambdaExpression;

        return this.getLeft().isSameStructureWithoutReduction(that.getLeft())
                && this.getRight().isSameStructureWithoutReduction(that.getRight());
    }

    @Override
    public boolean isFree(Name name) {
        return left.isFree(name) || right.isFree(name);
    }

    @Override
    public boolean isBound(Name name) {
        return left.isBound(name) || right.isBound(name);
    }

    @Override
    public Set<Name> getFreeVariables() {
        Set<Name> freeVariablesInLeft = left.getFreeVariables();
        Set<Name> freeVariablesInRight = right.getFreeVariables();
        return SetUtil.union(freeVariablesInLeft, freeVariablesInRight);
    }

    @Override
    public Set<Name> getBoundVariables() {
        Set<Name> boundVariablesInLeft = left.getBoundVariables();
        Set<Name> boundVariablesInRight = right.getBoundVariables();
        return SetUtil.union(boundVariablesInLeft, boundVariablesInRight);
    }

}
