package com.github.anilople.dog.backend.runtime.metafunction.operations.comparator;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.definition.Booleans;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 数字比较
 */
public final class NumberComparator {

    /**
     * 比较数字是否相等
     */
    public static final class Equal extends AbstractRuntimeFunction {

        private static final Equal EQUAL = new Equal(null, null);

        private Equal(Name argument, LambdaExpression body) {
            super(argument, body);
        }

        public static Equal getInstance() {
            return EQUAL;
        }

        @Override
        public LambdaExpression call(LambdaExpression replacement, Context context) {
            NumberName x = Evaluation.reduceToConformType(replacement, context, NumberName.class);
            return new EqualClosure(x);
        }

        @Override
        public String toString() {
            return "x -> { y -> { x == y } }";
        }

        private static final class EqualClosure extends AbstractRuntimeFunction {

            private final NumberName x;

            private EqualClosure(Name argument, LambdaExpression body, NumberName x) {
                super(argument, body);
                this.x = x;
            }

            public EqualClosure(NumberName x) {
                this(null, null, x);
            }

            @Override
            public LambdaExpression call(LambdaExpression replacement, Context context) {
                NumberName y = Evaluation.reduceToConformType(replacement, context, NumberName.class);
                if(0 == x.compareTo(y)) {
                    return Booleans.TRUE;
                } else {
                    return Booleans.FALSE;
                }
            }

            @Override
            public String toString() {
                return "y -> { " + x + " == y }";
            }
        }
    }

    /**
     * 比较数字是否满足 小于 关系
     */
    public static final class Less extends AbstractRuntimeFunction {

        public static final Less LESS = new Less(null, null);

        public static Less getInstance() {
            return LESS;
        }

        public Less(Name argument, LambdaExpression body) {
            super(argument, body);
        }

        @Override
        public LambdaExpression call(LambdaExpression replacement, Context context) {
            NumberName x = Evaluation.reduceToConformType(replacement, context, NumberName.class);
            return new LessClosure(x);
        }

        @Override
        public String toString() {
            return "x -> { y -> { x < y } }";
        }

        private static final class LessClosure extends AbstractRuntimeFunction {

            private final NumberName x;

            private LessClosure(Name argument, LambdaExpression body, NumberName x) {
                super(argument, body);
                this.x = x;
            }

            public LessClosure(NumberName x) {
                this(null, null, x);
            }

            @Override
            public LambdaExpression call(LambdaExpression replacement, Context context) {
                NumberName y = Evaluation.reduceToConformType(replacement, context, NumberName.class);
                if(x.compareTo(y) < 0) {
                    return Booleans.TRUE;
                } else {
                    return Booleans.FALSE;
                }
            }

            @Override
            public String toString() {
                return "y -> { " + x + " < y }";
            }
        }
    }

    /**
     * 比较数字是否满足 大于 关系
     */
    public static final class Great extends AbstractRuntimeFunction {

        private static final Great GREAT = new Great(null, null);

        public static Great getInstance() {
            return GREAT;
        }

        public Great(Name argument, LambdaExpression body) {
            super(argument, body);
        }

        @Override
        public LambdaExpression call(LambdaExpression replacement, Context context) {
            NumberName x = Evaluation.reduceToConformType(replacement, context, NumberName.class);
            return new GreatClosure(x);
        }

        @Override
        public String toString() {
            return "x -> { y -> { x > y } }";
        }

        private static final class GreatClosure extends AbstractRuntimeFunction {

            private final NumberName x;

            private GreatClosure(Name argument, LambdaExpression body, NumberName x) {
                super(argument, body);
                this.x = x;
            }

            public GreatClosure(NumberName x) {
                this(null, null, x);
            }

            @Override
            public LambdaExpression call(LambdaExpression replacement, Context context) {
                NumberName y = Evaluation.reduceToConformType(replacement, context, NumberName.class);
                if(x.compareTo(y) > 0) {
                    return Booleans.TRUE;
                } else {
                    return Booleans.FALSE;
                }
            }

            @Override
            public String toString() {
                return "y -> { " + x + " > y }";
            }
        }
    }

}
