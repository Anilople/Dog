package com.github.anilople.dog.backend.runtime.metafunction.operations;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 除法
 * 调用{@link NumberName#div(NumberName)}
 */
public class Div extends AbstractRuntimeFunction {
    
    public static final Div DIV = new Div(null, null);

    public static Div getInstance() {
        return DIV;
    }

    public Div(Name argument, LambdaExpression body) {
        super(argument, body);
    }

    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
        LambdaExpression resultX = Evaluation.execute(replacement, context);
        NumberName x = (NumberName) resultX;
        return new Div.DivClosure(x);
    }

    @Override
    public String toString() {
        return "x -> { y -> { x / y } }";
    }

    private static class DivClosure extends AbstractRuntimeFunction {

        private final NumberName x;

        public DivClosure(Name argument, LambdaExpression body) {
            super(argument, body);
            this.x = null;
        }

        public DivClosure(NumberName x) {
            super(null, null);
            this.x = x;
        }

        @Override
        public LambdaExpression call(LambdaExpression replacement, Context context) {
            LambdaExpression resultY = Evaluation.execute(replacement, context);
            NumberName y = (NumberName) resultY;
            assert x != null;
            return x.div(y);
        }

        @Override
        public String toString() {
            assert x != null;
            return "y -> { " + x.toString() + " + y }";
        }
    }
}
