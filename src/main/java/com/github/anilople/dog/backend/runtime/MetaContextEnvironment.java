package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.runtime.metafunction.Bind;
import com.github.anilople.dog.backend.runtime.metafunction.input.InputLine;
import com.github.anilople.dog.backend.runtime.metafunction.operations.*;
import com.github.anilople.dog.backend.runtime.metafunction.output.Print;
import com.github.anilople.dog.backend.runtime.metafunction.output.PrintLine;

/**
 * 解释器运行之前，
 * 设置的环境
 */
public class MetaContextEnvironment {

    private static final Context CONTEXT = new Context();

    public static Context getMetaContext() {
        // 新的meta上下文
        return new Context(CONTEXT);
    }

    /**
     * meta 绑定
     */
    static {
        // 绑定，定义变量
        CONTEXT.addUnmodifiable(new VariableName(Bind.class.getSimpleName()), Bind.getInstance());

        // 输出
        CONTEXT.addUnmodifiable(new VariableName(PrintLine.class.getSimpleName()), PrintLine.getInstance());
        CONTEXT.addUnmodifiable(new VariableName(Print.class.getSimpleName()), Print.getInstance());

        // 输入
        CONTEXT.addUnmodifiable(new VariableName(InputLine.class.getSimpleName()), InputLine.getInstance());

        // 算术
        // 加法 +
        CONTEXT.addUnmodifiable(new VariableName(Add.class.getSimpleName()), Add.getInstance());
        CONTEXT.addUnmodifiable(new VariableName("+"), Add.getInstance());
        // 减法 -
        CONTEXT.addUnmodifiable(new VariableName(Sub.class.getSimpleName()), Sub.getInstance());
        CONTEXT.addUnmodifiable(new VariableName("-"), Sub.getInstance());
        // 乘法 *
        CONTEXT.addUnmodifiable(new VariableName(Mul.class.getSimpleName()), Mul.getInstance());
        CONTEXT.addUnmodifiable(new VariableName("*"), Mul.getInstance());
        // 除法 /
        CONTEXT.addUnmodifiable(new VariableName(Div.class.getSimpleName()), Div.getInstance());
        CONTEXT.addUnmodifiable(new VariableName("/"), Div.getInstance());
        // 取模 %
        CONTEXT.addUnmodifiable(new VariableName(Mod.class.getSimpleName()), Mod.getInstance());
        CONTEXT.addUnmodifiable(new VariableName("%"), Mod.getInstance());
    }

}
