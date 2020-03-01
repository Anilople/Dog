package com.github.anilople.dog.backend.runtime.metafunction.input;

import com.github.anilople.dog.backend.ast.StringName;
import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

import java.util.Scanner;

/**
 * 读入一行
 */
public class InputLine extends AbstractRuntimeFunction {

    public static final InputLine INPUT_LINE = new InputLine(null, null);

    public static InputLine getInstance() {
        return INPUT_LINE;
    }

    private InputLine(Name argument, LambdaExpression body) {
        super(argument, body);
    }

    /**
     * @param replacement 读入的字符串被绑定在这个变量上
     * @return 读入的一行字符串(不包括换行符)
     */
    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
        // 请勿求值!
//        LambdaExpression lambdaExpression = Evaluation.execute(replacement, context);
        VariableName variableName = (VariableName) replacement;
        // 读入
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        // 添加到环境中
        context.add(variableName, new StringName(line));
        // 从环境中拿出
        return context.get(variableName);
    }

    @Override
    public String toString() {
        return "var -> { var <- input }";
    }
}
