package com.github.anilople.dog.frontend.formater;

import com.github.anilople.dog.frontend.FormatterLexer;
import com.github.anilople.dog.frontend.FormatterParser;
import com.github.anilople.dog.frontend.listener.DogCodeFormatterListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 格式化 编程语言的格式.
 * 类似Go语言，和rust语言的对代码格式化.
 * 困难的地方在于，要保留注释！！
 */
public class CodeFormatter {

    /**
     * 缩进的基础字符
     */
    public static final char INDENTATION_CHAR = '\t';
    /**
     * 一个缩进
     */
    public static final String ONE_INDENTATION = "" + INDENTATION_CHAR;

    /**
     * format 文件
     * @param path 文件
     */
    public static void format(Path path) throws IOException {
        byte[] bytes = Files.readAllBytes(path);
        String text = new String(bytes);
        String textAfterFormatted = format(text);

        // 将format后的文本写回文件
        Files.write(path, textAfterFormatted.getBytes());
    }

    /**
     * format 字符串
     * @param text 符合语法规范的字符串
     * @return format后的字符串
     */
    public static String format(String text) {
        FormatterLexer formatterLexer = new FormatterLexer(CharStreams.fromString(text));
        FormatterParser formatterParser = new FormatterParser(new CommonTokenStream(formatterLexer));

        DogCodeFormatterListener dogCodeFormatterListener = new DogCodeFormatterListener();
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        parseTreeWalker.walk(dogCodeFormatterListener, formatterParser.formatters());

        return dogCodeFormatterListener.getContentFormatted();
    }

}
