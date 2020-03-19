package com.github.anilople.dog.backend.runtime.metafunction;

import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 绑定变量，将{@link VariableName}代表的{@link LambdaExpression}添加到环境中
 */
public class Bind extends AbstractRuntimeFunction {

    private static final Bind BIND = new Bind(null, null);

    public static Bind getInstance() {
        return BIND;
    }

    private Bind(Name argument, LambdaExpression body) {
        super(argument, body);
    }

    /**
     *
     * @param replacement 请勿求值！！！
     * @param context 上下文
     * @return {@link BindClosure}
     */
    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
        // 不要进行求值，否则会出现很不好的行为
        // 1. 执行 a绑定b，
        // 2. 执行 a绑定c，
        // 如果求值，那么 2 会变成 b绑定c
        // 因为a在求值后变成了b
        // 所以不要进行求值，直接绑定即可
//        LambdaExpression lambdaExpression = Evaluation.execute(replacement, context);
        VariableName variableName = (VariableName) replacement;
        return new BindClosure(variableName);
    }

    @Override
    public String toString() {
        return "BindName -> { BindValue -> { BindName <- BindValue } }";
    }

    private static class BindClosure extends AbstractRuntimeFunction {

        private final VariableName variableName;

        public BindClosure(Name argument, LambdaExpression body) {
            super(argument, body);
            this.variableName = null;
        }

        public BindClosure(VariableName variableName) {
            super(null, null);
            this.variableName = variableName;
        }

        @Override
        public LambdaExpression call(LambdaExpression replacement, Context context) {
            context.addToScope(variableName, replacement);
            return null;
        }

        @Override
        public String toString() {
            return "BindValue -> { " + variableName + " <- BindValue }";
        }
    }
}
