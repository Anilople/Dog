package com.github.anilople.dog.backend.definition.operations;

import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.definition.Booleans;
import com.github.anilople.dog.backend.util.ApplicationUtil;

import static com.github.anilople.dog.backend.definition.Booleans.*;
import static com.github.anilople.dog.backend.definition.Letters.*;

/**
 * 逻辑运算，
 * 为什么能得出这种运算符？
 * 仔细计算
 * {@link Booleans#FALSE}{@link Booleans#FALSE}
 * {@link Booleans#FALSE}{@link Booleans#TRUE}
 * {@link Booleans#TRUE}{@link Booleans#FALSE}
 * {@link Booleans#TRUE}{@link Booleans#TRUE}
 * 这4个表达式，会发现一些规律。
 *
 * 然而，一个定义存在多种表达形式
 */
public class Logical {

    /**
     * 与运算 &
     * \xy.xyF
     * x -> (y -> ( xyF ))
     */
    public static Function AND = new Function(
            X,
            new Function(
                    Y,
                    ApplicationUtil.generateApplicationFromLeft(
                            X, Y, FALSE
                    )
            )
    );

    /**
     * 或运算 |
     * \xy.xTy
     */
    public static Function OR = new Function(
            X,
            new Function(
                    Y,
                    ApplicationUtil.generateApplicationFromLeft(
                            X, TRUE, Y
                    )
            )
    );

    /**
     * 非运算 !
     * \b.bFT
     */
    public static Function NOT = new Function(
            B,
            ApplicationUtil.generateApplicationFromLeft(
                    B, FALSE, TRUE
            )
    );

}
