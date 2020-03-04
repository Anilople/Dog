/*
    Dog编程语言的前端语法
    使用antlr构建
    https://www.antlr.org/
    不明白的地方可以翻文档，或者Wiki
*/
grammar Dog;

evaluations
    :   evaluation*? EOF
    ;

// 求值
evaluation
    :   '(' lambdaExpression ')'
    ;

// lambda 表达式
lambdaExpression
    :   name                                      #nameLabel
    |   name '->' '{' lambdaExpression '}'        #functionLabel
    |   lambdaExpression '[' lambdaExpression ']' #applicationLabel
    ;

name
    : variableName
    | stringName
    | numberName
    ;

variableName
    :   Identifier
    ;
stringName
    :   StringLiteral
    ;
numberName
    :   IntegerLiteral
    ;


Identifier
    :   [a-zA-Z]+
    ;

StringLiteral
	:	'"' StringCharacters? '"'
	;

fragment
StringCharacters
	:	StringCharacter+
	;

fragment
StringCharacter
    :   ~["]
    ;

IntegerLiteral
    :   [0-9]+
    ;

// 空白符 WhiteSpace
WhiteSpace
    :   [ \t\r\n]+ -> skip
    ;

/*
    注释 Comments
*/
LineComment
    :   '//' ~[\r\n]* -> skip
    ;

// 块注释:
BlockComment
    :   '/*' .*? '*/' -> skip
    ;