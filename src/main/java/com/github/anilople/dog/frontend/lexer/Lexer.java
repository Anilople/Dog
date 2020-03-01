package com.github.anilople.dog.frontend.lexer;

import com.github.anilople.dog.frontend.lexer.input.Token;
import com.github.anilople.dog.frontend.lexer.input.token.*;
import com.github.anilople.dog.frontend.lexer.input.token.Number;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * lexical analysis
 * 词法分析
 */
public class Lexer {

    /**
     * key: 正则表达式
     * value: 事件的消费者
     */
    private final Map<String, Consumer<LexerEvent>> stringConsumerMap;

    /**
     * 要解析的内容
     */
    private final String text;

    public Lexer(Map<String, Consumer<LexerEvent>> stringConsumerMap, String text) {
        this.stringConsumerMap = stringConsumerMap;
        this.text = text;
    }

    /**
     * 最长匹配原则
     * 只匹配一个
     * @param matcherConsumerMap 对应{@link Matcher} 会触发{@link Consumer<LexerEvent>}
     * @param text 文本
     * @param startIndex 文本开始的位置
     * @return 下一个 文本开始的位置
     */
    static int matchOne(
            Map<Matcher, Consumer<LexerEvent>> matcherConsumerMap,
            final String text,
            final int startIndex) {
        assert matcherConsumerMap.size() >= 1;
        // 保留的 matcher
        Set<Matcher> retainMatchers = new HashSet<>();
        for(Matcher matcher : matcherConsumerMap.keySet()) {
            if(matcher.reset(text).find(startIndex)) {
                // 不优化了
                if(matcher.start() == startIndex) {
                    // 刚好是从起始位置匹配到的
                    retainMatchers.add(matcher);
                }
            }
        }

        if(retainMatchers.size() <= 0) {
            throw new RuntimeException("未匹配到任何字符串，词法存在缺陷，位置 " + startIndex + "，剩余文本: " + text.substring(startIndex));
        } else if(retainMatchers.size() > 1) {
            System.out.println("匹配到的字符串和其匹配模式为:");
            for(Matcher matcher : retainMatchers) {
                System.out.println(matcher.group() + " : " + matcher);
            }
            throw new RuntimeException("匹配完了，但是匹配到的不止一个，词法出现了歧义。");
        }

        // 唯一对应的matcher
        final Matcher matcher = retainMatchers.iterator().next();
        // 匹配到的内容
        final String matchedContent = matcher.group();

        // 创建事件
        final LexerEvent lexerEvent = new LexerEvent(matchedContent, -1, -1);
        // 找到对应的消费者
        final Consumer<LexerEvent> lexerEventConsumer = matcherConsumerMap.get(matcher);
        // 发布事件
        lexerEventConsumer.accept(lexerEvent);

        // 返回解析后的位置
        return startIndex + matchedContent.length();
    }

    /**
     *
     * @param matchers 多个{@link Matcher}
     * @param input 输入
     * @return {@link Matcher}, 留下可以匹配输入的
     */
    static Set<Matcher> filterMatched(Set<Matcher> matchers, CharSequence input) {
        Set<Matcher> retainMatchers = new HashSet<>();
        for(Matcher matcher : matchers) {
            if(matcher.reset(input).matches()) {
                retainMatchers.add(matcher);
            }
        }
        return retainMatchers;
    }

    /**
     * value不变，key由{@link String}变成{@link Matcher}
     */
    static Map<Matcher, Consumer<LexerEvent>> regex2Matcher(Map<String, Consumer<LexerEvent>> stringConsumerMap) {
        final Map<Matcher, Consumer<LexerEvent>> matcherConsumerMap = new HashMap<>();
        for(Map.Entry<String, Consumer<LexerEvent>> entry : stringConsumerMap.entrySet()) {
            final String regex = entry.getKey();
            final Consumer<LexerEvent> lexerEventConsumer = entry.getValue();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher("");
            matcherConsumerMap.put(matcher, lexerEventConsumer);
        }
        return matcherConsumerMap;
    }

    /**
     * 开始运行词法分析
     */
    private void run() {
        final Map<Matcher, Consumer<LexerEvent>> matcherConsumerMap = regex2Matcher(this.stringConsumerMap);
        for(
                int startIndex = 0;
                startIndex < this.text.length();
                startIndex = matchOne(matcherConsumerMap, this.text, startIndex)) {
            // do nothing
        }
    }

    /**
     * 从文本解析出{@link Token}
     * @param text 文本
     * @return {@link Token}多个，并且不可变
     */
    public static List<Token> tokenize(String text) {
        // 存储
        final List<Token> tokens = new ArrayList<>();

        // 正则对应的消费者
        final Map<String, Consumer<LexerEvent>> stringConsumerMap = new HashMap<>();

        // 添加规则
        stringConsumerMap.put("[ \t\r\n]+", lexerEvent -> {});

        // 字符串
        stringConsumerMap.put(
                "\"[^\"]*\"",
                lexerEvent -> {
                    // 字符串不太一样，需要去除左边的" 和右边的"
                    final String content = lexerEvent.getContent();
                    final String newContent = content.substring(1, content.length() - 1);
                    LexerEvent newLexerEvent = new LexerEvent(newContent, lexerEvent.getRowOffset(), lexerEvent.getColumnOffset());
                    tokens.add(new DogString(newLexerEvent));
                }
        );
        stringConsumerMap.put("[a-zA-Z][A-Za-z0-9_?\\.]*", lexerEvent -> tokens.add(new Variable(lexerEvent)));
        stringConsumerMap.put("[0-9]+", lexerEvent -> tokens.add(new Number(lexerEvent)));
        stringConsumerMap.put("<-", lexerEvent -> tokens.add(new BindArrow(lexerEvent)));
        stringConsumerMap.put("->", lexerEvent -> tokens.add(new FunctionArrow(lexerEvent)));
        stringConsumerMap.put("[(]", lexerEvent -> tokens.add(new LeftParenthesis(lexerEvent)));
        stringConsumerMap.put("[)]", lexerEvent -> tokens.add(new RightParenthesis(lexerEvent)));
        stringConsumerMap.put("\\[", lexerEvent -> tokens.add(new LeftBrackets(lexerEvent)));
        stringConsumerMap.put("\\]", lexerEvent -> tokens.add(new RightBrackets(lexerEvent)));
        stringConsumerMap.put("[{]", lexerEvent -> tokens.add(new LeftBigParenthesis(lexerEvent)));
        stringConsumerMap.put("[}]", lexerEvent -> tokens.add(new RightBigParenthesis(lexerEvent)));

        // 开始解析
        Lexer lexer = new Lexer(stringConsumerMap, text);
        lexer.run();
        return Collections.unmodifiableList(tokens);
    }
}
