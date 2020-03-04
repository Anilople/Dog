package com.github.anilople.dog.backend.runtime.metafunction.operations;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 取模
 * 调用{@link NumberName#mod(NumberName)}
 */
public class Mod extends AbstractRuntimeFunction {

    public static final Mod MOD = new Mod(null, null);

    public static Mod getInstance() {
        return MOD;
    }

    public Mod(Name argument, LambdaExpression body) {
        super(argument, body);
    }

    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
        LambdaExpression resultX = Evaluation.reduceUntil(
                replacement, context,
                lambdaExpression -> lambdaExpression instanceof NumberName
        );
        NumberName x = (NumberName) resultX;
        return new Mod.ModClosure(x);
    }

    @Override
    public String toString() {
        return "x -> { y -> { x / y } }";
    }

    private static class ModClosure extends AbstractRuntimeFunction {

        private final NumberName x;

        public ModClosure(Name argument, LambdaExpression body) {
            super(argument, body);
            this.x = null;
        }

        public ModClosure(NumberName x) {
            super(null, null);
            this.x = x;
        }

        @Override
        public LambdaExpression call(LambdaExpression replacement, Context context) {
            LambdaExpression resultY = Evaluation.reduceUntil(
                    replacement, context,
                    lambdaExpression -> lambdaExpression instanceof NumberName
            );
            NumberName y = (NumberName) resultY;
            assert x != null;
            return x.mod(y);
        }

        @Override
        public String toString() {
            assert x != null;
            return "y -> { " + x.toString() + " + y }";
        }
    }
    
}
