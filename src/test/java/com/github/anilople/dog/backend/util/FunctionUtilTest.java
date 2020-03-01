package com.github.anilople.dog.backend.util;

import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.Function;
import org.junit.jupiter.api.Test;

import static com.github.anilople.dog.backend.definition.Letters.*;

class FunctionUtilTest {

    @Test
    void generateFunction() {

        /*

         */
        Function one = FunctionUtil.generateFunction(new Application(S, Z), S, Z);
        System.out.println(one);
    }
}