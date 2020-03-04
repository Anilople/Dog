package com.github.anilople.dog.backend.runtime.metafunction.output;

import com.github.anilople.dog.backend.runtime.Interpreter;
import org.junit.After;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PrintLineTest {

    private static final PrintStream STD_OUT = System.out;

    /**
     * 重置标准输出
     */
    @After
    public void resetStdout() {
        System.setOut(STD_OUT);
    }

    @Test
    void hello() {
        OutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Interpreter.interpret("(PrintLine[hello])");

        STD_OUT.println(outputStream.toString());
        assertEquals("hello" + System.lineSeparator(), outputStream.toString());
    }

    @Test
    void multi() {
        OutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Interpreter.interpret("(PrintLine[a][b])");

        assertEquals(
                "a"  + System.lineSeparator() +
                        "b" + System.lineSeparator(),
                outputStream.toString()
        );
    }

    @Test
    void simpleNest() {
        OutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Interpreter.interpret("(PrintLine[x -> {x}[one]][Void])");

        STD_OUT.println(outputStream.toString());
        assertEquals(
                "one"  + System.lineSeparator() +
                        "Void" + System.lineSeparator(),
                outputStream.toString()
        );
    }

    @Test
    void string() {
        OutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Interpreter.interpret("(PrintLine[\"hello, world\"])");

        STD_OUT.println(outputStream.toString());
        assertEquals("hello, world" + System.lineSeparator(), outputStream.toString());
    }

    /**
     * 空字符串测试（不是null）
     */
    @Test
    void emptyString() {
        OutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Interpreter.interpret("(PrintLine[\"\"])");

        STD_OUT.println(outputStream.toString());
        assertEquals("" + System.lineSeparator(), outputStream.toString());
    }

}