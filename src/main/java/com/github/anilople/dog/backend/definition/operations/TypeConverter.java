package com.github.anilople.dog.backend.definition.operations;

import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.definition.Booleans;
import com.github.anilople.dog.backend.definition.Numbers;
import com.github.anilople.dog.backend.util.ApplicationUtil;

import static com.github.anilople.dog.backend.definition.Booleans.*;
import static com.github.anilople.dog.backend.definition.Letters.*;

public class TypeConverter {

    /**
     * {@link Numbers}转为{@link Booleans}
     * 判断数字是否为 0
     */
    public static final Function IS_ZERO = new Function(
            N,
            ApplicationUtil.generateApplicationFromLeft(
                    N,
                    new Function(X, FALSE),
                    TRUE
            )
    );

    /**
     * 判断数字是否为非0,
     */
    public static final Function IS_NOT_ZERO = new Function(
            N,
            ApplicationUtil.generateApplicationFromRight(
                    Logical.NOT,
                    IS_ZERO,
                    N
            )
    );

    /**
     * {@link Numbers}转为{@link Booleans}
     * 判断一个数字是否大于0
     */
    public static final Function IS_BIGGER_THAN_ZERO = new Function(
            N,
            ApplicationUtil.generateApplicationFromLeft(
                    N,
                    new Function(X, TRUE),
                    FALSE
            )
    );

}
