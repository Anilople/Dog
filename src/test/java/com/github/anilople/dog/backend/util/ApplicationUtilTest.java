package com.github.anilople.dog.backend.util;

import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.Name;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.anilople.dog.backend.definition.Letters.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationUtilTest {

    @Test
    void generateApplicationFromLeft() {
        /*
              0
             | \
            0   z
           |  \
          x    y
         */
        Application xyz = ApplicationUtil.generateApplicationRightMost(X, Y, Z);
        System.out.println(xyz);
        assertEquals(X, ((Application) xyz.getLeft()).getLeft());
        assertEquals(Y, ((Application) xyz.getLeft()).getRight());
        assertEquals(Z, xyz.getRight());
    }

    @Test
    void generateApplicationRightMost() {
        /*
              0
             | \
            0   z
           |  \
          x    y
         */
        Application xyz = ApplicationUtil.generateApplicationRightMost(X, Arrays.asList(Y, Z));
        System.out.println(xyz);
        assertEquals(X, ((Application) xyz.getLeft()).getLeft());
        assertEquals(Y, ((Application) xyz.getLeft()).getRight());
        assertEquals(Z, xyz.getRight());
    }

    @Test
    void generateApplicationFromRight() {
        /*
            0
           | \
          x   0
              |  \
              y    z
         */
        Application xyz = ApplicationUtil.generateApplicationLeftMost(X, Y, Z);
        System.out.println(xyz);
        assertEquals(X, xyz.getLeft());
        assertEquals(Y, ((Application) xyz.getRight()).getLeft());
        assertEquals(Z, ((Application) xyz.getRight()).getRight());
    }

    @Test
    void generateApplicationLeftMost() {
        /*
                0
              /   \
             f     0
                 /   \
                f     0
                    /   \
                   f     x
         */
        Application fffx = ApplicationUtil.generateApplicationLeftMost(F, F, F, X);

        Application ffx = (Application) fffx.getRight();

        Application fx = (Application) ffx.getRight();

        Name f = (Name) fx.getLeft();

        Name x = (Name) fx.getRight();
    }
}