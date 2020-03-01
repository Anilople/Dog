package com.github.anilople.dog.backend.runtime.metafunction.operations;

import com.github.anilople.dog.backend.runtime.Interpreter;
import org.junit.After;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AddTest {

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
    void add() {
        // 重置标准输出
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        final String text = "(Print[Add[2][3]])";
        Interpreter.interpret(text);

        assertEquals("5", byteArrayOutputStream.toString());
    }

    @Test
    void addMultiple() {
        // 重置标准输出
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        final String text = "(Print[Add[Add[1][2]][Add[3][4]]])";
        Interpreter.interpret(text);

        assertEquals("10", byteArrayOutputStream.toString());
    }

}