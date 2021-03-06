package com.github.anilople.dog.frontend.parser;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.frontend.DogLexer;
import com.github.anilople.dog.frontend.DogParser;
import com.github.anilople.dog.frontend.listener.DogGrammarTreeListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;

public class Parser {

    public static List<Evaluation> parse(String text) {
        DogLexer dogLexer = new DogLexer(CharStreams.fromString(text));
        DogParser dogParser = new DogParser(new CommonTokenStream(dogLexer));

        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        DogGrammarTreeListener dogGrammarTreeListener = new DogGrammarTreeListener();
        parseTreeWalker.walk(dogGrammarTreeListener, dogParser.evaluations());

        return dogGrammarTreeListener.getEvaluations();
    }
}
