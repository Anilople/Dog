package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 某条语句执行时的上下文，
 * 也就是状态
 */
public class Context {

    /**
     * {@link VariableName}和{@link LambdaExpression}绑定的记录表
     * 一旦添加，后续不可变
     */
    private final Map<VariableName, LambdaExpression> table = new ConcurrentHashMap<>();

    Context() {

    }

    Context(Context context) {
        this.table.putAll(context.table);
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
        return table.containsKey(variableName);
    }

    /**
     * 增加不可变的变量
     * @param unmodifiableVariable 不可变的变量
     * @param lambdaExpression 对应的{@link LambdaExpression}
     */
    void addUnmodifiable(VariableName unmodifiableVariable, LambdaExpression lambdaExpression) {
        if(exists(unmodifiableVariable)) {
            throw new IllegalStateException(unmodifiableVariable + "已经绑定了" + table.get(unmodifiableVariable));
        }
        // 添加绑定
        table.put(unmodifiableVariable, lambdaExpression);
    }

    /**
     * 添加可变的临时变量
     * @param modifiableVariable 临时变量
     * @param lambdaExpression 对应的{@link LambdaExpression}
     */
    void addTemporary(VariableName modifiableVariable, LambdaExpression lambdaExpression) {
        table.put(modifiableVariable, lambdaExpression);
    }

    /**
     * 添加{@link VariableName}和{@link LambdaExpression}对应的引用
     * @param variableName 变量
     * @param lambdaExpression 表达式
     */
    public void add(VariableName variableName, LambdaExpression lambdaExpression) {
        if(isUnmodifiable(variableName)) {
            // 不可变
            addUnmodifiable(variableName, lambdaExpression);
        } else {
            // 可变
            addTemporary(variableName, lambdaExpression);
        }
    }

    /**
     * @param variableName 变量
     * @return 变量代表的表达式
     */
    public LambdaExpression get(VariableName variableName) {
        if(!exists(variableName)) {
            throw new RuntimeException(variableName + "没有定义在" + table);
        }
        return table.get(variableName);
    }

}
