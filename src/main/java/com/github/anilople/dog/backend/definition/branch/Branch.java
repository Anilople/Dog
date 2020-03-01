package com.github.anilople.dog.backend.definition.branch;

import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.util.ApplicationUtil;
import com.github.anilople.dog.backend.util.FunctionUtil;

/**
 * 分支选择函数
 */
public class Branch {

    private static final Name BOOLEAN_EXPRESSION = new Name("BOOLEAN_EXPRESSION");
    private static final Name THEN_BRANCH = new Name("THEN_BRANCH");
    private static final Name ELSE_BRANCH = new Name("ELSE_BRANCH");

    /**
     * if表达式
     * if(BOOLEAN_EXPRESSION) {
     *     THEN_BRANCH
     * } else {
     *     ELSE_BRANCH
     * }
     * \fxy.fxy
     */
    public static final Function IF = FunctionUtil.generateFunction(
            ApplicationUtil.generateApplicationFromLeft(
                    BOOLEAN_EXPRESSION, THEN_BRANCH, ELSE_BRANCH
            ),
            BOOLEAN_EXPRESSION, THEN_BRANCH, ELSE_BRANCH
    );

}
