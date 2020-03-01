package com.github.anilople.dog.backend.ast.lambda;

import com.github.anilople.dog.backend.runtime.Context;

/**
 * 变量是没法被规约的
 * 函数也是没法被规约的
 * 只有函数调用可以被规约
 */
public interface Reducible {

    /**
     * 在一些特殊的reduce中，会改变系统的状态
     * @param context 系统的状态
     * @return 规约一小步后的结果
     */
    LambdaExpression reduce(Context context);

}
