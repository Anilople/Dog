package com.github.anilople.dog.backend.ast.lambda;

import com.github.anilople.dog.backend.runtime.Context;

/**
 * 只有{@link Function}才能被调用
 */
public interface Callable {

    /**
     * 调用函数后，函数体内被这个函数绑定的变量，
     * 全部会变成给定的lambda表达式
     *
     * @return call一个函数后的结果
     * @see Function
     */
    LambdaExpression call(LambdaExpression replacement, Context context);

}
