package com.github.anilople.dog.backend.definition;

import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.util.ApplicationUtil;

import static com.github.anilople.dog.backend.definition.Letters.S;
import static com.github.anilople.dog.backend.definition.Letters.Z;

public class Numbers {

    /**
     * s -> (z -> z)
     * lambda sz.z
     */
    public static final Function ZERO = new Function(
            S,
            new Function(
                    Z,
                    Z
            )
    );

    /**
     * s -> (z -> sz)
     * lambda sz.sz
     */
    public static final Function ONE = new Function(
            S,
            new Function(
                    Z,
                    new Application(
                            S,
                            Z
                    )
            )
    );

    /**
     * s -> (z -> s(s(z)))
     * lambda sz.s(s(z))
     */
    public static final Function TWO = new Function(
            S,
            new Function(
                    Z,
                    ApplicationUtil.generateApplicationFromRight(S, S, Z)
            )
    );

}
