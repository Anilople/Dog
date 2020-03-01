package com.github.anilople.dog.backend.runtime.metafunction.operations;

import com.github.anilople.dog.backend.runtime.Interpreter;
import org.junit.After;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class SubTest {

    private static final InputStream STD_IN = System.in;
    private static final PrintStream STD_OUT = System.out;

    /**
     * 再每个单元测试后，
     * 重置标准的输入输出
     */
    @After
    void resetStd() {
        System.setIn(STD_IN);
        System.setOut(STD_OUT);
    }

    @Test
    void sub() {
        // 重置标准输出
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        final String text = "(Print[Sub[2][3]])";
        Interpreter.interpret(text);

        assertEquals("-1", byteArrayOutputStream.toString());
    }
}