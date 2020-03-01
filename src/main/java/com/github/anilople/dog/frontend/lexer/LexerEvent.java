package com.github.anilople.dog.frontend.lexer;

/**
 * 匹配到内容后会触发这个事件，
 * 事件存在一些信息
 */
public class LexerEvent {

    private final String content;
    private final Integer rowOffset;
    private final Integer columnOffset;

    public LexerEvent(String content, Integer rowOffset, Integer columnOffset) {
        this.content = content;
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
    }

    public String getContent() {
        return content;
    }

    public Integer getRowOffset() {
        return rowOffset;
    }

    public Integer getColumnOffset() {
        return columnOffset;
    }
}
