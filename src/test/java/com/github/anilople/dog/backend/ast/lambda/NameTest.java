package com.github.anilople.dog.backend.ast.lambda;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    static Name name = new Name("x");

    @org.junit.jupiter.api.Test
    void replace() {
        // 变量替换成 另一个变量
        Name y = (Name) name.replaceFreeVariable(name, new Name("y"));
        assertEquals("y", y.getLiterals());

        // 变量替换成 函数
        Function function = (Function) name.replaceFreeVariable(name, new Function(new Name("y"), new Name("z")));
        assertEquals("y", function.getArgument().getLiterals());
        assertEquals("z", ((Name) function.getBody()).getLiterals());

        // 变量替换为 调用
        Application application = (Application) name.replaceFreeVariable(
                name,
                new Application(
                        new Name("a"),
                        new Name("b")
                )
        );
        assertEquals("a", ((Name) application.getLeft()).getLiterals());
        assertEquals("b", ((Name) application.getRight()).getLiterals());
    }
}