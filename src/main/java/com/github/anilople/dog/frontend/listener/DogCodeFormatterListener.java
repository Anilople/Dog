package com.github.anilople.dog.frontend.listener;

import com.github.anilople.dog.frontend.FormatterParser;
import com.github.anilople.dog.frontend.FormatterBaseListener;
import com.github.anilople.dog.util.StringUtil;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来format 代码，统一代码的风格.
 * 基本的思想很简单，
 * 1. 不考虑任何和缩进相关的问题，而是决定某个关键字出现后，是否换行？并将所有行的内容进行存储
 * 2. 根据多行的内容，还有相关的关键字，计算出缩进，插在每行的最左边
 */
public class DogCodeFormatterListener extends FormatterBaseListener {

    private final List<String> lines = new ArrayList<>();

    /**
     * 根据{@link this#lines}计算出缩进并返回
     * @return format后的代码内容
     */
    public String getContentFormatted() {
        int indentationsOffset = 0;
        List<String> linesAfterFormatted = new ArrayList<>();
        for(String line : lines) {
            // 缩进应该增加的个数
            int increase = StringUtil.countOccurrencesOf(line, "([{");
            // 缩进应该减少的个数
            int decrease = StringUtil.countOccurrencesOf(line, ")]}");

            int offset = increase - decrease;

            if(offset > 0) {
                // 缩进个数 + 内容
                final String indentations = StringUtil.getIndentations(indentationsOffset);
                linesAfterFormatted.add(indentations + line);
                indentationsOffset += offset;
            } else {
                indentationsOffset += offset;
                // 缩进个数 + 内容
                final String indentations = StringUtil.getIndentations(indentationsOffset);
                linesAfterFormatted.add(indentations + line);
            }
        }
        return String.join(System.lineSeparator(), linesAfterFormatted);
    }

    /**
     * 获取最后一行，如果一行都没有，
     * 那就加入新的一行
     * @return 最后一行
     */
    private String getLastLine() {
        if(lines.size() <= 0) {
            lines.add("");
        }
        return lines.get(lines.size() - 1);
    }

    /**
     * 确保最后一行为空行
     */
    private void appendEmptyLineIfNotExists() {
        if(!getLastLine().isEmpty()) {
            lines.add("");
        }
    }

    /**
     * 在当前位置加入内容
     */
    private void appendText(String text) {
        String last = getLastLine();
        lines.set(lines.size() - 1, last + text);
    }

    @Override
    public void exitName(FormatterParser.NameContext ctx) {
        final String text = ctx.getText();
        appendText(text);
    }

    @Override
    public void exitLineComment(FormatterParser.LineCommentContext ctx) {
        final String lineCommentContent = ctx.getText();
        appendEmptyLineIfNotExists();
        // 加行注释
        appendText(lineCommentContent);
        // 加新行
        appendEmptyLineIfNotExists();
    }

    @Override
    public void exitBlockComment(FormatterParser.BlockCommentContext ctx) {
        final String blockCommentContent = ctx.getText();
        appendEmptyLineIfNotExists();
        // 加块注释
        appendText(blockCommentContent);
        appendEmptyLineIfNotExists();
        appendEmptyLineIfNotExists();
    }

    /**
     * 要保留注释
     */
    @Override
    public void visitTerminal(TerminalNode node) {
        final String text = node.getText();

        switch (text) {
            case "->":
                appendText(" -> ");
                break;
            case "(":
            case ")":
                appendEmptyLineIfNotExists();
                appendText(text);
                appendEmptyLineIfNotExists();
                break;
            case "[":
                appendEmptyLineIfNotExists();
                appendText(text);
                break;
            case "]":
                appendText(text);
                appendEmptyLineIfNotExists();
                break;
            case "{": {
                appendText(text);
                appendEmptyLineIfNotExists();
                break;
            }
            case "}": {
                appendEmptyLineIfNotExists();
                appendText(text);
                appendEmptyLineIfNotExists();
                break;
            }
        }
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        throw new IllegalStateException("code format 出错" + node);
    }
}
