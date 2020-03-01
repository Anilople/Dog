package com.github.anilople.dog.backend.ast.lambda;

import com.github.anilople.dog.backend.runtime.Context;

import java.util.Set;

/**
 * lambda表达式的抽象
 */
public abstract class LambdaExpression {

    /**
     * 仅仅替换自由变量，
     * 如果当前表达式的{@link Name}被函数绑定，
     * 就不进行替换
     * @param name        变量名
     * @param replacement 变量将要变成的lambda表达式
     * @return 替换变量后的结果
     */
    public abstract LambdaExpression replaceFreeVariable(Name name, LambdaExpression replacement);

    /**
     * @return 是否可以被call
     * @see Function
     */
    public boolean isCallable() {
        return this instanceof Callable;
    }

    /**
     * @return 是否可以规约
     */
    public abstract boolean isReducible(Context context);

    /**
     * 获取lambda表达式中出现的所有符号
     * 注意是符号，无论是出现在函数{@link Function#getArgument()}中，还是{@link Application}中
     *
     * @return lambda表达式中出现的所有符号
     */
    public abstract Set<Name> getAllSymbols();

    /**
     * 替换{@link LambdaExpression}中出现的所有{@link Name}
     * 如果符号不一样，就不用替换
     * 这个方法和{@link this#replaceFreeVariable(Name, LambdaExpression)}不同，
     * 这里不执行任何规约，只是简单的变动符号
     * @param source 即将被替换的{@link Name}
     * @param target 替换后，变成的{@link Name}
     * @return 替换符号后的表达式
     */
    public abstract LambdaExpression changeSymbol(Name source, Name target);

    /**
     * 在不进行任何规约的情况下，
     * 表达式的结构是否是相等的？
     * 变量名不影响结构的相等性
     * @return 是否synonym
     */
    public abstract boolean isSameStructureWithoutReduction(LambdaExpression lambdaExpression);

    /**
     * @param name 变量
     * @return 变量是否是自由的
     */
    public abstract boolean isFree(Name name);

    /**
     * @param name 变量
     * @return 变量是否是绑定的
     */
    public abstract boolean isBound(Name name);

    /**
     * 获取{@link LambdaExpression}中所有的自由变量
     * @return
     */
    public abstract Set<Name> getFreeVariables();

    /**
     * 获取{@link LambdaExpression}中所有被绑定的变量
     * @return
     */
    public abstract Set<Name> getBoundVariables();
}
