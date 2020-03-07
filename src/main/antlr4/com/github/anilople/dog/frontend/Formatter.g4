/*
    code formatter
    格式化代码使用
    为了和原有的语法隔离和保留注释代码
    所以新建了这个文件
    和原有的语法区分开
*/
grammar Formatter;
import Name;

formatters
    :   word*
    |   EOF
    ;

word
    :   '('
    |   ')'
    |   '->'
    |   '['
    |   ']'
    |   '{'
    |   '}'
    |   name
    |   comment
    ;

evaluationsWithComment
    :   (comment | evaluationWithComment)*
    ;

// 带注释
evaluationWithComment
    :   '(' lambdaExpressionWithComment ')'
    ;

// 注释可以跟随在任何lambda后方或者前方
// lambda 表达式
lambdaExpressionWithComment
    :   specialName arguments comment*
    |   specialName body comment*
    |   specialName
    ;

specialName
    :   comment* name comment*
    ;


arguments
    :   argument+
    ;
argument
    :   '[' lambdaExpressionWithComment ']'
    ;

body
    :   '->' '{' lambdaExpressionWithComment '}'
    ;

// 注释
comment
    :   lineComment
    |   blockComment
    ;

lineComment
    :   LineComment
    ;

blockComment
    :   BlockComment
    ;

// 忽略空白符
SkipWhiteSpace
    : WhiteSpace -> skip
    ;