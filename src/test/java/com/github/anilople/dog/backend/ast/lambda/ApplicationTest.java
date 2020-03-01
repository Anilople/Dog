package com.github.anilople.dog.backend.ast.lambda;

import org.junit.jupiter.api.Test;

import static com.github.anilople.dog.backend.definition.Letters.*;
import static org.junit.jupiter.api.Assertions.*;


class ApplicationTest {

    @Test
    void isSameStructureWith() {
        // x -> x
        Function id0 = new Function(X, X);
        Function id1 = new Function(Y, Y);
        assertTrue(id0.isSameStructureWithoutReduction(id1));

        // s -> (x -> sx)
        Function one0 = new Function(
                S,
                new Function(
                        X,
                        new Application(S, X)
                )
        );
        Function one1 = new Function(
                F,
                new Function(
                        Y,
                        new Application(F, Y)
                )
        );
        assertTrue(one0.isSameStructureWithoutReduction(one1));
    }
}