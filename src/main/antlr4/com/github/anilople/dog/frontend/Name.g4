/*
    Name相关的语法比较多，
    且比较独立，
    所以单独放一个文件
*/
grammar Name;
import CommonLexerRules;

name
    : variableName
    | stringName
    | numberName
    ;

variableName
    :   Identifier
    |   SpecialCharacters
    ;

stringName
    :   StringLiteral
    ;
numberName
    :   naturalName
    |   integerName
    ;

naturalName
    :   NaturalLiteral
    ;

integerName
    :   IntegerLiteral
    ;