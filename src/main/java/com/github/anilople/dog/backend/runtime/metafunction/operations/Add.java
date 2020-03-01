package com.github.anilople.dog.backend.runtime.metafunction.operations;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

public class Add extends AbstractRuntimeFunction {

    private static final Add ADD = new Add(null, null);

    public static Add getInstance() {
        return ADD;
    }

    private Add(Name argument, LambdaExpression body) {
        super(argument, body);
    }

    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
        LambdaExpression resultX = Evaluation.execute(replacement, context);
        NumberName x = (NumberName) resultX;
        return new AddClosure(x);
    }

    @Override
    public String toString() {
        return "x -> { y -> { x + y } }";
    }

    private static class AddClosure extends AbstractRuntimeFunction {

        private final NumberName x;

        private AddClosure(Name argument, LambdaExpression body) {
            super(argument, body);
            this.x = null;
        }

        public AddClosure(NumberName x) {
            super(null, null);
            this.x = x;
        }

        @Override
        public LambdaExpression call(LambdaExpression replacement, Context context) {
            LambdaExpression result = Evaluation.execute(replacement, context);
            NumberName numberName = (NumberName) result;
            assert x != null;
            return x.add(numberName);
        }

        @Override
        public String toString() {
            assert x != null;
            return "y -> { " + x.toString() + " + y }";
        }
    }
}
