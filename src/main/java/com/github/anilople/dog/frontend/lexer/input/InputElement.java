package com.github.anilople.dog.frontend.lexer.input;

import com.github.anilople.dog.frontend.lexer.LexerEvent;

public abstract class InputElement {

    private final String content;
    private final Integer rowOffset;
    private final Integer columnOffset;

    public InputElement(LexerEvent lexerEvent) {
        this.content = lexerEvent.getContent();
        this.rowOffset = lexerEvent.getRowOffset();
        this.columnOffset = lexerEvent.getColumnOffset();
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

    @Override
    public String toString() {
        final String className = this.getClass().getSimpleName();
        return className + "{" +
                "content='" + content + '\'' +
                ", rowOffset=" + rowOffset +
                ", columnOffset=" + columnOffset +
                '}';
    }
}
