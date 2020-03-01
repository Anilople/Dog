package com.github.anilople.dog.backend.definition;

import com.github.anilople.dog.backend.ast.lambda.Function;

import static com.github.anilople.dog.backend.definition.Letters.*;

/**
 * 布尔值表示
 */
public class Booleans {

    /**
     * 真值
     * \xy.x
     * x -> (y -> x)
     */
    public static final Function TRUE = new Function(
            X,
            new Function(
                    Y,
                    X
            )
    );

    /**
     * 假值
     * \xy.y
     * x -> (y -> y)
     * 仔细一看，这个函数和{@link Numbers#ZERO}相同
     */
    public static final Function FALSE = new Function(
            X,
            new Function(
                    Y,
                    Y
            )
    );

}
