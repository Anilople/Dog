package com.github.anilople.dog.backend.definition.datastructure;

import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.definition.Booleans;
import com.github.anilople.dog.backend.util.ApplicationUtil;
import com.github.anilople.dog.backend.util.FunctionUtil;

import static com.github.anilople.dog.backend.definition.Letters.*;

/**
 * Pair(a, b)数据结构
 */
public class Pair {

    /**
     * \xyf.fxy
     */
    public static final Function PAIR = FunctionUtil.generateFunction(
            ApplicationUtil.generateApplicationRightMost(F, X, Y),
            X, Y, F
    );

    /**
     * 获取{@link Pair#PAIR}的第1个参数
     * Pair(a, b) -> a
     * \p.p(\xy.x)
     */
    public static final Function FIRST = new Function(
            P,
            Booleans.TRUE
    );

    /**
     * 获取{@link Pair#PAIR}的第2个参数
     * Pair(a, b) -> b
     * \p.p(\xy.y)
     */
    public static final Function SECOND = new Function(
            P,
            Booleans.FALSE
    );

}
