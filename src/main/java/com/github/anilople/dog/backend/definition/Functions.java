package com.github.anilople.dog.backend.definition;

import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.Function;

import static com.github.anilople.dog.backend.definition.Letters.*;

/**
 * 一些函数定义
 *
 * @see Function
 */
public class Functions {

    /**
     * id函数，接收什么，就返回什么
     */
    public static final Function ID = new Function(X, X);

    /**
     * 常量函数
     * 接收什么都会返回一个常量
     * u -> x
     */
    public static final Function CONSTANT = new Function(U, X);

    /**
     * Y组合子，用来优雅地实现递归
     * \f.(\x.f(xx))(\x.f(xx))
     */
    public static final Function Y = new Function(
            F,
            new Application(
                    new Function(
                            X,
                            new Application(
                                    F,
                                    new Application(X, X)
                            )
                    ),
                    new Function(
                            X,
                            new Application(
                                    F,
                                    new Application(X, X)
                            )
                    )
            )
    );
}
