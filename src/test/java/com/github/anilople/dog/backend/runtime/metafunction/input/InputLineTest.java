package com.github.anilople.dog.backend.runtime.metafunction.input;

import com.github.anilople.dog.backend.runtime.Interpreter;
import org.junit.After;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class InputLineTest {

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
    void echo() {
        // 重置标准输入
        final String lineContent = "here";
        final String lineContentWrapperLineSeparator = lineContent + System.lineSeparator();
        InputStream inputStream = new ByteArrayInputStream(lineContentWrapperLineSeparator.getBytes());
        System.setIn(inputStream);

        // 重置标准输出
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        final String text = "(InputLine[a])" +
                "(PrintLine[a])";
        Interpreter.interpret(text);

        assertEquals(lineContentWrapperLineSeparator, byteArrayOutputStream.toString());
    }
}