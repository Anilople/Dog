package com.github.anilople.dog.backend.util;

import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.util.SetUtil;

import java.util.Arrays;
import java.util.Set;

public class FunctionUtil {

    /**
     * 有些函数很复杂，需要多个参数传入，
     * 使用{@link Function#Function(Name, LambdaExpression)}也可以实现，
     * 但是很麻烦，所以有了这个方法
     * 例子：
     *      generateFunction(body, a, b)  会得到 a -> { b -> { body } }
     * @param body 函数体，放在最后
     * @param names 多个参数，参数绑定的顺序为从左至右
     * @return 函数
     * @throws IllegalArgumentException 如果变量个数为0
     */
    public static Function generateFunction(LambdaExpression body, Name ... names) {
        if(names.length <= 0) {
            throw new IllegalArgumentException("要绑定的变量个数不能为0");
        }
        // 第一个被绑定的变量形参
        final Name firstName = names[0];
        if(names.length == 1) {
            return new Function(firstName, body);
        } else {
            // 余下部分的函数
            Function tailFunction = generateFunction(body, Arrays.copyOfRange(names, 1, names.length));
            return new Function(firstName, tailFunction);
        }
    }

    /**
     * 替换一个在函数中被绑定的变量
     * @param function 函数
     * @param boundVariable 在函数中被绑定的变量
     * @return 替换自由变量后的函数，函数结构保持不变
     * @throws IllegalArgumentException 如果 变量在函数中不存在，或者 变量在函数中没有被绑定
     */
    private static Function replaceBoundVariableInFunction(Function function, Name boundVariable) {
        Set<Name> allSymbols = function.getAllSymbols();
        if(!allSymbols.contains(boundVariable)) {
            throw new IllegalArgumentException(boundVariable + "在函数" + function + "中不存在");
        }
        Set<Name> freeVariablesInFunction = function.getBoundVariables();
        if(!freeVariablesInFunction.contains(boundVariable)) {
            throw new IllegalArgumentException(boundVariable + "在函数" + function + "中没有被绑定");
        }

        Name newName = NameUtil.generateRandomDifferentName(allSymbols);
        return function.changeSymbol(boundVariable, newName);
    }

    /**
     * 防止在{@link Application#reduce()}的时候出现歧义
     * 什么时候出现歧义？
     * 当{@link Name}在右边是自由的变量，但是在左边是绑定的变量，的时候。
     * @param function 函数
     * @param replacement 要替换成的表达式
     * @return reduce不会产生歧义的函数
     */
    public static Function replaceBoundVariablesInFunctionToForbidAmbiguity(Function function, LambdaExpression replacement) {
        // 函数所有绑定的变量
        final Set<Name> boundVariablesInFunction = function.getBoundVariables();
        // 右边所有的自由变量
        final Set<Name> freeVariablesInReplacement = replacement.getFreeVariables();

        // 找出交集
        final Set<Name> variablesIntersection = SetUtil.intersection(boundVariablesInFunction, freeVariablesInReplacement);

        Function newFunction = function;
        // 为了避免产生歧义，所以需要在函数中将它们替换成其它符号
        for(Name variable : variablesIntersection) {
            // 迭代
            newFunction = replaceBoundVariableInFunction(newFunction, variable);
        }
        return newFunction;
    }

}
