package com.github.anilople.dog.frontend.formater;

import org.junit.jupiter.api.Test;

class CodeFormatterTest {

    /**
     * 复杂的内容
     */
    @Test
    void complex() {
        // 求第n项斐波那契数
        final String text = "(Bind[Fib][n -> {if[<[n][3]][1][+[Fib[-[n][1]]][Fib[-[n][2]]]]}])";
        String textAfterFormatted = CodeFormatter.format(text);
        System.out.println(textAfterFormatted);
    }

}