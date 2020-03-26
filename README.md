[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/Anilople/Dog) 

# Dog

[![Build Status](https://travis-ci.com/Anilople/Dog.svg?branch=master)](https://travis-ci.com/Anilople/Dog)

Dog 编程语言，年轻人的第一门自制编程语言

## 快速尝试运行

从[releases](https://github.com/Anilople/Dog/releases)中下载最新发行版本，解压后。

你只需要关心2个文件：

* `dog-xxx.jar`：Java包，提供了Dog语言的解释器。
* `HelloWorld.dog`：Dog语言的代码

在这2个文件的同路径下打开终端，运行

```shell
java -jar dog-xxx.jar HelloWorld.dog
```

即可开始了第一步。

后续在文件`HelloWorld.dog`上进行修改即可。

## Tutorial

Dog是一门基于[Lambda演算](https://personal.utdallas.edu/~gupta/courses/apl/lambda.pdf)的编程语言，语法规则很少（就是括号有点多）。

从常见的`hello, world`说起，如下

```ruby
(Print["hello, world"])
```

每句Dog语言，都由`(`和`)`包裹， 去掉小括号，得到里面的内容`Print["hello, world"]`就是即将被执行的语句，表示`Print`接受参数`"hello, world"`，将其打印到控制台。

### Meta Lambda Expression

在Dog中，有一些表达式是预置的，是解释器运行的环境，也被称为`runtime`

## Specification

### 前端语法

人生苦短，Parser太长。

使用了[antlr](https://www.antlr.org/)来解决文本到语法树的难题。

描述文件为[Dog.g4](src/main/antlr4/com/github/anilople/dog/frontend/Dog.g4)

### 后端设计

Everything is lambda!