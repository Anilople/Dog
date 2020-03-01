package com.github.anilople.dog.backend.definition.operations;

import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.util.ApplicationUtil;

import static com.github.anilople.dog.backend.definition.Letters.*;
import static com.github.anilople.dog.backend.definition.operations.TypeConverter.IS_BIGGER_THAN_ZERO;

/**
 * 比较
 */
public class Comparator {

    /**
     * 小于
     * \mn.m < n
     * n - m 大于0即可，
     * 注意是 n - m，不是 m - n
     */
    public static final Function IS_LESS = new Function(
            M,
            new Function(
                    N,
                    new Application(
                            IS_BIGGER_THAN_ZERO,
                            ApplicationUtil.generateApplicationFromLeft(
                                    Arithmetic.SUB, N, M
                            )
                    )
            )
    );

    /**
     * 等于
     * \mn.0?(+(-mn)(-nm))
     * 将(m-n)和(n-m)相加，如果结果为0，那么这2个数字相等
     */
    public static final Function IS_EQUAL = new Function(
            M,
            new Function(
                    N,
                    new Application(
                            TypeConverter.IS_ZERO,
                            ApplicationUtil.generateApplicationFromLeft(
                                    Arithmetic.ADD,
                                    ApplicationUtil.generateApplicationFromLeft(Arithmetic.SUB, M, N),
                                    ApplicationUtil.generateApplicationFromLeft(Arithmetic.SUB, N, M)
                            )
                    )
            )
    );

    /**
     * 大于
     * \mn.m > n
     */
    public static final Function IS_BIGGER = new Function(
            M,
            new Function(
                    N,
                    new Application(
                            IS_BIGGER_THAN_ZERO,
                            ApplicationUtil.generateApplicationFromLeft(
                                    Arithmetic.SUB, M, N
                            )
                    )
            )
    );

    /**
     * 小于等于
     * \mn. m <= n
     */
    public static final Function IS_LESS_OR_EQUAL = new Function(
            M,
            new Function(
                    N,
                    ApplicationUtil.generateApplicationFromLeft(
                            Logical.OR,
                            ApplicationUtil.generateApplicationFromLeft(IS_LESS, M, N),
                            ApplicationUtil.generateApplicationFromLeft(IS_EQUAL, M, N)
                    )
            )
    );

}
