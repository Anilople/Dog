/*
    公共部分的词法分析
*/
lexer grammar CommonLexerRules;

Identifier
    :   [a-zA-Z] ([a-zA-Z0-9] | '.' | '?')*
    ;

// 特殊字符，按键盘顺序列举
// 里面没有 ()[]{}
SpecialCharacters
    :   ('~'|'!'|'@'|'#'|'$'|'%'|'^'|'&'|'*'|'+'|'-'|'='|'|'|'<'|'>')+
    ;

// 字符串
StringLiteral
	:	'"' StringCharacters? '"'
	;
fragment
StringCharacters
	:	StringCharacter+
	;
// 字符
fragment
StringCharacter
    :   ~["]
    ;

// 自然数
NaturalLiteral
    :   '+'? Digits
    ;

// 整数
IntegerLiteral
    :   ('+' | '-')? Digits
    ;

// 纯数字
Digits
    :   [0-9]+
    ;

// 空白符 WhiteSpace
fragment
WhiteSpace
    :   [ \t\r\n]+
    ;

// 行注释
LineComment
    :   '//' ~[\r\n]*
    ;

// 块注释:
BlockComment
    :   '/*' .*? '*/'
    ;