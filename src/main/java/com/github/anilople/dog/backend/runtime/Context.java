package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 某条语句执行时的上下文，
 * 也就是状态
 */
public class Context {

    /**
     * {@link VariableName}和{@link LambdaExpression}绑定的记录表
     */
    private final Map<VariableName, Stack<LambdaExpression>> scopes = new ConcurrentHashMap<>();
    
    Context() {

    }

    Context(Context context) {
        this();
        // 复制
        scopes.putAll(context.scopes);
    }

    /**
     * 首字母大写的{@link VariableName}是不可变的
     * @return {@link VariableName}是否是可变的
     */
    static boolean isUnmodifiable(VariableName variableName) {
        final String first = variableName.getLiterals().charAt(0) + "";
        return first.matches("[A-Z]");
    }

    /**
     * @param variableName 变量
     * @return 变量是否存在绑定
     */
    public boolean exists(VariableName variableName) {
        return scopes.containsKey(variableName) && !scopes.get(variableName).isEmpty();
    }

    /**
     * @param variableName 变量
     * @throws RuntimeException 如果变量不存在
     */
    public void ensureExists(VariableName variableName) {
        if(!exists(variableName)) {
            throw new RuntimeException(variableName + "未定义");
        }
    }

    /**
     * 添加{@link VariableName}和{@link LambdaExpression}对应的引用
     * @param variableName 变量
     * @param lambdaExpression 变量代表的{@link LambdaExpression}
     * @throws RuntimeException 如果变量不可变，并且已经绑定
     */
    public void addToScope(VariableName variableName, LambdaExpression lambdaExpression) {
        if(exists(variableName) && isUnmodifiable(variableName)) {
            throw new RuntimeException(variableName + "不可变");
        }

        if(!scopes.containsKey(variableName)) {
            scopes.put(variableName, new Stack<>());
        }

        scopes.get(variableName).push(lambdaExpression);
    }

    /**
     * @param variableName 变量
     * @return 变量代表的表达式
     */
    public LambdaExpression get(VariableName variableName) {
        ensureExists(variableName);
        Stack<LambdaExpression> stack = scopes.get(variableName);
        // 返回栈顶
        return stack.peek();
    }

    /**
     * 将之前变量和{@link LambdaExpression}的绑定关系解除，
     * 注意只会解除一个！！
     * @param variableName 变量
     */
    public void removeFromScope(VariableName variableName) {
        ensureExists(variableName);
        Stack<LambdaExpression> stack = scopes.get(variableName);
        if(stack.empty()) {
            throw new RuntimeException(variableName + "无定义");
        } else {
            stack.pop();
        }
    }

}
