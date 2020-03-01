package com.github.anilople.dog.backend.runtime.metafunction.operations;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 乘法
 * 调用{@link NumberName#mul(NumberName)}
 */
public class Mul extends AbstractRuntimeFunction {

    public static final Mul MUL = new Mul(null, null);

    public static Mul getInstance() {
        return MUL;
    }

    public Mul(Name argument, LambdaExpression body) {
        super(argument, body);
    }

    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
        LambdaExpression resultX = Evaluation.execute(replacement, context);
        NumberName x = (NumberName) resultX;
        return new Mul.MulClosure(x);
    }

    @Override
    public String toString() {
        return "x -> { y -> { x * y } }";
    }

    private static class MulClosure extends AbstractRuntimeFunction {

        private final NumberName x;

        public MulClosure(Name argument, LambdaExpression body) {
            super(argument, body);
            this.x = null;
        }

        public MulClosure(NumberName x) {
            super(null, null);
            this.x = x;
        }

        @Override
        public LambdaExpression call(LambdaExpression replacement, Context context) {
            LambdaExpression resultY = Evaluation.execute(replacement, context);
            NumberName y = (NumberName) resultY;
            assert x != null;
            return x.mul(y);
        }

        @Override
        public String toString() {
            assert x != null;
            return "y -> { " + x.toString() + " + y }";
        }
    }
}
