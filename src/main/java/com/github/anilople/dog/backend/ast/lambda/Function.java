package com.github.anilople.dog.backend.ast.lambda;

import com.github.anilople.dog.backend.runtime.Context;
import com.github.anilople.dog.backend.util.FunctionUtil;
import com.github.anilople.dog.backend.util.NameUtil;
import com.github.anilople.dog.util.SetUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 函数
 */
public class Function extends LambdaExpression implements Callable {

    /**
     * 函数参数
     */
    private final Name argument;

    /**
     * 函数体
     */
    private final LambdaExpression body;

    public Function(Name argument, LambdaExpression body) {
        this.argument = argument;
        this.body = body;
    }

    /**
     * 对于函数来说，
     * 如果要替换的变量被这个函数绑定了，
     * 那么就无需替换了
     */
    @Override
    public LambdaExpression replaceFreeVariable(Name name, LambdaExpression replacement) {
        if (this.argument.equals(name)) {
            // 无法替换被绑定的变量
            return this;
        } else {
            LambdaExpression newBody = body.replaceFreeVariable(name, replacement);
            return new Function(this.argument, newBody);
        }
    }

    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {

        // 防止变量冲突，替换函数中一些会冲突的自由变量
//        Function newFunction = FunctionUtil.replaceBoundVariablesInFunctionToForbidAmbiguity(this, replacement);
        Function newFunction = FunctionUtil.replaceBoundVariablesInFunctionToForbidAmbiguity(this, replacement);

        return newFunction.getBody().replaceFreeVariable(
                newFunction.getArgument(),
                replacement
        );
    }

    @Override
    public String toString() {
        return argument + " -> { " + body.toString() + " }";
    }

    public Name getArgument() {
        return argument;
    }

    public LambdaExpression getBody() {
        return body;
    }

    @Override
    public Set<Name> getAllSymbols() {
        Set<Name> names = new HashSet<>();
        // 添加 形参
        names.addAll(argument.getAllSymbols());
        // 添加 body
        names.addAll(body.getAllSymbols());
        return Collections.unmodifiableSet(names);
    }

    @Override
    public Function changeSymbol(Name source, Name target) {
        final Name newName = argument.changeSymbol(source, target);
        final LambdaExpression newBody = body.changeSymbol(source, target);
        return new Function(newName, newBody);
    }

    @Override
    public boolean isSameStructureWithoutReduction(LambdaExpression lambdaExpression) {
        if(! (lambdaExpression instanceof Function)) {
            return false;
        }

        Function that = (Function) lambdaExpression;
        // 函数的参数名不影响结构上的相等
        // 如果要比较的2个函数，参数名不同，
        // 可以将它们的参数名变成相同的，再进行比较
        // 但是在改变参数名后，函数的结构不能被改变
        Set<Name> thisAllSymbols = this.getAllSymbols();
        Set<Name> thatAllSymbols = that.getAllSymbols();
        // 2个函数都有的符号
        Set<Name> allSymbols = SetUtil.union(thisAllSymbols, thatAllSymbols);

        // 获取不会改变函数结构的新变量名
        Name newName = NameUtil.generateRandomDifferentName(allSymbols);

        // 改变符号
        Function newThis = this.changeSymbol(this.getArgument(), newName);
        Function newThat = that.changeSymbol(that.getArgument(), newName);

        return newThis.getBody().isSameStructureWithoutReduction(newThat.getBody());
    }

    @Override
    public boolean isFree(Name name) {
        return !this.argument.equals(name);
    }

    @Override
    public boolean isBound(Name name) {
        return this.argument.equals(name);
    }

    @Override
    public Set<Name> getFreeVariables() {
        Set<Name> freeVariablesInBody = body.getFreeVariables();
        // functions's argument is bind, so we need to delete it
        Set<Name> variables = new HashSet<>(freeVariablesInBody);
        variables.remove(argument);
        return Collections.unmodifiableSet(variables);
    }

    @Override
    public Set<Name> getBoundVariables() {
        Set<Name> boundVariable = Collections.singleton(argument);
        Set<Name> boundVariablesInBody = body.getBoundVariables();

        return SetUtil.union(boundVariable, boundVariablesInBody);
    }

    @Override
    public boolean isReducible(Context context) {
        return false;
    }
}
