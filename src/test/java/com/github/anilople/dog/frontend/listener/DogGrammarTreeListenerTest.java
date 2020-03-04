package com.github.anilople.dog.frontend.listener;

import com.github.anilople.dog.frontend.DogLexer;
import com.github.anilople.dog.frontend.DogParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

class DogGrammarTreeListenerTest {

    @Test
    void simple() {
        String text = "(Bind[a -> {a}][B])";
        DogLexer dogLexer = new DogLexer(CharStreams.fromString(text));
        DogParser dogParser = new DogParser(new CommonTokenStream(dogLexer));
        ParseTree parseTree = dogParser.evaluations();
//        System.out.println(parseTree.toStringTree(dogParser));
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        DogGrammarTreeListener dogGrammarTreeListener = new DogGrammarTreeListener();
        parseTreeWalker.walk(dogGrammarTreeListener, parseTree);
        System.out.println(dogGrammarTreeListener.getEvaluations());
    }

}