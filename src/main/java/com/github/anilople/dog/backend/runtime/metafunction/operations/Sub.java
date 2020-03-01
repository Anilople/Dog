package com.github.anilople.dog.backend.runtime.metafunction.operations;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 减法
 * 调用{@link NumberName#sub(NumberName)}
 */
public class Sub extends AbstractRuntimeFunction {

    public static final Sub SUB = new Sub(null, null);

    public static Sub getInstance() {
        return SUB;
    }

    public Sub(Name argument, LambdaExpression body) {
        super(argument, body);
    }

    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
        LambdaExpression resultX = Evaluation.execute(replacement, context);
        NumberName x = (NumberName) resultX;
        return new Sub.SubClosure(x);
    }

    @Override
    public String toString() {
        return "x -> { y -> { x - y } }";
    }

    private static class SubClosure extends AbstractRuntimeFunction {

        private final NumberName x;

        public SubClosure(Name argument, LambdaExpression body) {
            super(argument, body);
            this.x = null;
        }

        public SubClosure(NumberName x) {
            super(null, null);
            this.x = x;
        }

        @Override
        public LambdaExpression call(LambdaExpression replacement, Context context) {
            LambdaExpression resultY = Evaluation.execute(replacement, context);
            NumberName y = (NumberName) resultY;
            assert x != null;
            return x.sub(y);
        }

        @Override
        public String toString() {
            assert x != null;
            return "y -> { " + x.toString() + " + y }";
        }
    }
}
