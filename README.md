# Dog

Dog 编程语言，年轻人的第一门自制编程语言

## Tutorial

Dog是一门基于[Lambda演算](https://personal.utdallas.edu/~gupta/courses/apl/lambda.pdf)的编程语言

```ruby
(PrintLine["hello, world"])
```

### Meta Lambda Expression

在Dog中，有一些表达式是预置的，是解释器运行的环境，也被称为`runtime`

## Specification

### Lexer 词法分析

```assembly
InputElement:
	WhiteSpace
	Comment
	Token
WhiteSpace: [ \t\r\n]+
Token:
	DogString: "[^"]*"
    Variable: [a-zA-Z][A-Za-z0-9_?\.]*
    Number: [0-9]+
    BindArrow: <-
    FunctionArrow: ->
    LeftParenthesis: (
    RightParenthesis: )
    LeftBrackets: [
    RightBrackets: ]
    LeftBigParenthesis: {
    RightBigParenthesis: }
```

### Parser 语法分析

The syntax `{x}` on the right-hand side of a production denotes **zero or more**
occurrences of `x`.

`'c'`表示字面常量`c`

```assembly
letter:
	a b c
	ed f
```

表示`letter`可以是`a b c`或者`ed f`，如果2种情况都满足，那么会优先选择排在前面的。

终止符为词法分析中，分析出来的`Token`

```assembly
Evaluations:
	{Evaluation}
Evaluation:
	'(' LambdaExpression ')'
LambdaExpression:
	Application
	Function
	Name
Application:
	Function Arguments
	Name Arguments
Function:
	Name FunctionArrow '{' LambdaExpression '}'
Arguments:
	Argument {Argument}
Argument:
	'[' LambdaExpression ']'
Name:
	StringName
	VariableName
	NumberName
StringName:
	DogString
VariableName:
	Variable
NumberName:
	Number
```