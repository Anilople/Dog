package com.github.anilople.dog.backend.definition.operations;

import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.definition.Functions;
import com.github.anilople.dog.backend.definition.Numbers;
import com.github.anilople.dog.backend.definition.branch.Branch;
import com.github.anilople.dog.backend.util.ApplicationUtil;
import com.github.anilople.dog.backend.util.FunctionUtil;

import static com.github.anilople.dog.backend.definition.Letters.*;

/**
 * 算术运算
 *
 * 加减乘除
 */
public class Arithmetic {

    /**
     * 后继
     * \wyx.y(wyx)
     */
    public static final Function SUCCESSOR = FunctionUtil.generateFunction(
            new Application(
                    Y,
                    ApplicationUtil.generateApplicationFromLeft(W, Y, X)
            ),
            W, Y, X
    );

    /**
     * 加法
     * \mnfx.mf(nfx)
     */
    public static final Function ADD = FunctionUtil.generateFunction(
            ApplicationUtil.generateApplicationFromLeft(
                    M,
                    F,
                    ApplicationUtil.generateApplicationFromLeft(N, F, X)
            ),
            M, N, F, X
    );

    /**
     * 乘法
     * \mnfx.m(nf)x
     */
    public static final Function MUL = FunctionUtil.generateFunction(
            ApplicationUtil.generateApplicationFromLeft(
                    M,
                    new Application(N, F),
                    X
            ),
            M, N, F, X
    );

    /**
     * 幂函数f
     * f(m, n) = m^n
     * \mn.nm
     */
    public static final Function EXP = new Function(
            M,
            new Function(
                    N,
                    new Application(N, M)
            )
    );

    /**
     * 前继
     * - 1
     * \nfx.n (\gh.h(gf)) (u -> x) (u -> u)
     */
    public static final Function PREDECESSOR = FunctionUtil.generateFunction(
            ApplicationUtil.generateApplicationFromLeft(
                    N,
                    new Function(
                            G,
                            new Function(
                                    H,
                                    new Application(
                                            H,
                                            new Application(G, F)
                                    )
                            )
                    ),
                    Functions.CONSTANT,
                    Functions.ID
            ),
            N, F, X
    );

    /**
     * 减法
     * m - n
     * \mn.nPm
     * P代表前继函数，在m上将这个函数作用n次
     */
    public static final Function SUB = new Function(
            M,
            new Function(
                    N,
                    ApplicationUtil.generateApplicationFromLeft(
                            N, PREDECESSOR, M
                    )
            )
    );

    /**
     * 阶乘函数
     * \f.\n if n==0 then 1 else n * (f(n-1))
     */
    public static final Function FACTORIAL = new Function(
            F,
            new Function(
                    N,
                    ApplicationUtil.generateApplicationFromLeft(
                            Branch.IF,
                            new Application(TypeConverter.IS_NOT_ZERO, N),
                            ApplicationUtil.generateApplicationFromLeft(
                                    MUL,
                                    N,
                                    new Application(
                                            F,
                                            new Application(
                                                    PREDECESSOR,
                                                    N
                                            )
                                    )
                            ),
                            Numbers.ONE
                    )
            )
    );

    /**
     * 数字转为0
     * \f.\n if n==0 then 0 else f(n-1)
     */
    public static final Function TO_ZERO = new Function(
            F,
            new Function(
                    N,
                    ApplicationUtil.generateApplicationFromLeft(
                            Branch.IF,
                            new Application(TypeConverter.IS_NOT_ZERO, N),
                            new Application(
                                    F,
                                    new Application(PREDECESSOR, N)
                            ),
                            Numbers.ZERO
                    )
            )
    );

}
