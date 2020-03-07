/*
    Dog编程语言的前端语法
    使用antlr构建
    https://www.antlr.org/
    不明白的地方可以翻文档，或者Wiki
*/
grammar Dog;
import Name;

evaluations
    :   evaluation*? EOF
    ;

// 求值
evaluation
    :   '(' lambdaExpression ')'
    ;

// lambda 表达式
lambdaExpression
    :   name arguments  #applicationLabel
    |   name body       #functionLabel
    |   name            #nameLabel
    ;

// 参数
arguments
    :   argument+
    ;
argument
    :   '[' lambdaExpression ']'
    ;

// 函数body
body
    :   '->' '{' lambdaExpression '}'
    ;

// 跳过空白符
SkipWhiteSpace
    : WhiteSpace -> skip
    ;


// 跳过行注释
SkipLineComment
    :   LineComment -> skip
    ;

// 跳过块注释:
SkipBlockComment
    :   BlockComment -> skip
    ;