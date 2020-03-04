package com.github.anilople.dog.backend.ast.number;

import com.github.anilople.dog.backend.ast.lambda.*;
import com.github.anilople.dog.backend.definition.Functions;
import com.github.anilople.dog.backend.runtime.Context;
import com.github.anilople.dog.backend.util.ApplicationUtil;

/**
 * 自然数
 * 0, 1, 2 ...
 *
 * 根据lambda演算中，
 * 对自然数字的定义，
 * Church numerals，
 * 自然数等价于整数。
 * https://en.wikipedia.org/wiki/Church_encoding
 */
public class NaturalName extends IntegerName implements Reducible {

    private NaturalName(String literals) {
        super(literals);
    }

    public NaturalName(int value) {
        super(value);
        assert value >= 0;
    }

    @Override
    public boolean isReducible(Context context) {
        return true;
    }

    @Override
    public LambdaExpression reduce(Context context) {
        int value = Integer.parseInt(this.getLiterals());
        return ChurchNumerals.getInstance(value);
    }

    public static class ChurchNumerals extends Function {

        private ChurchNumerals(Name argument, LambdaExpression body) {
            super(argument, body);
        }

        /**
         * 0: \fx.x
         * 1: \fx.fx
         * 2: \fx.f(fx)
         * 3: \fx.f(f(f(x)))
         * @param fCount 函数f的重复次数
         * @return lambda表达式下的自然数
         */
        public static ChurchNumerals getInstance(int fCount) {
            assert fCount >= 0 ;
            final Name f = new Name("f");
            final Name x = new Name("x");
            final ChurchNumerals churchNumerals;
            if(0 == fCount) {
                churchNumerals = new ChurchNumerals(new Name("f"), Functions.ID);
            } else {
                // 右倾斜的树
                Name[] names = new Name[fCount + 1];

                // 设置f
                for(int i = 0; i < names.length - 1; i++) {
                    names[i] = f;
                }
                // 设置x
                names[names.length - 1] = new Name("x");
                // 类似 f(f(fx))
                Application bodyRightPart = ApplicationUtil.generateApplicationLeftMost(names);
                // 类似 \x.f(f(fx))
                Function body = new Function(x, bodyRightPart);
                churchNumerals = new ChurchNumerals(f, body);
            }
            return churchNumerals;
        }
    }
}
