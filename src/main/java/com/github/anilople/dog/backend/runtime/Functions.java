package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.definition.operations.Arithmetic;

/**
 * 运行时的特殊函数
 */
public class Functions {


    public static final AbstractRuntimeFunction SUCCESSOR = Successor.getInstance();

    /**
     * 使数字 + 1 的函数
     */
    private static class Successor extends AbstractRuntimeFunction {

        private static final Successor INSTANCE = new Successor(null, null);

        public Successor(Name argument, LambdaExpression body) {
            super(argument, body);
        }

        @Override
        public LambdaExpression call(LambdaExpression replacement, Context context) {
            if(replacement instanceof Name) {
                Name oldNumber = (Name) replacement;
                int value = Integer.parseInt(oldNumber.getLiterals());
                // 新的值
                int newValue = value + 1;
                Name newName = new Name(String.valueOf(newValue));
                System.out.println(new Application(this, newName));
                return newName;
            } else if(replacement instanceof Function) {
                // SUCCESSOR函数的本意是让函数f多用一次，
                // 所以这里使用回原来的定义
                return Arithmetic.SUCCESSOR.call(replacement, context);
            } else if(replacement instanceof Application){
                // 先reduce直到不能reduce为止
                LambdaExpression afterReduceAll = Interpreter.interpret(replacement);
                // 取出数字
                Name oldNumber = (Name) afterReduceAll;
                int value = Integer.parseInt(oldNumber.getLiterals());
                int newValue = value + 1;
                Name newName = new Name(String.valueOf(newValue));
                System.out.println(new Application(this, newName));
                return newName;
            } else {
                throw new IllegalStateException("无法识别类型" + replacement);
            }
        }

        public static Successor getInstance() {
            return INSTANCE;
        }

        @Override
        public String toString() {
            return "x -> { x + 1 }";
        }
    }
}
