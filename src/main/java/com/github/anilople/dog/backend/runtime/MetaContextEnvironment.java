package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.definition.Booleans;
import com.github.anilople.dog.backend.definition.Functions;
import com.github.anilople.dog.backend.definition.branch.Branch;
import com.github.anilople.dog.backend.definition.operations.Logical;
import com.github.anilople.dog.backend.runtime.metafunction.Bind;
import com.github.anilople.dog.backend.runtime.metafunction.input.InputLine;
import com.github.anilople.dog.backend.runtime.metafunction.operations.*;
import com.github.anilople.dog.backend.runtime.metafunction.operations.comparator.NumberComparator;
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

        // id函数
        CONTEXT.addUnmodifiable(new VariableName("Id"), Functions.ID);
        CONTEXT.addUnmodifiable(new VariableName("id"), Functions.ID);

        // 布尔函数
        CONTEXT.addUnmodifiable(new VariableName("True"), Booleans.TRUE);
        CONTEXT.addUnmodifiable(new VariableName("true"), Booleans.TRUE);
        CONTEXT.addUnmodifiable(new VariableName("False"), Booleans.FALSE);
        CONTEXT.addUnmodifiable(new VariableName("false"), Booleans.FALSE);

        // 布尔运算
        CONTEXT.addUnmodifiable(new VariableName("And"), Logical.AND);
        CONTEXT.addUnmodifiable(new VariableName("and"), Logical.AND);
        CONTEXT.addUnmodifiable(new VariableName("&&"), Logical.AND);

        CONTEXT.addUnmodifiable(new VariableName("Or"), Logical.OR);
        CONTEXT.addUnmodifiable(new VariableName("or"), Logical.OR);
        CONTEXT.addUnmodifiable(new VariableName("||"), Logical.OR);

        CONTEXT.addUnmodifiable(new VariableName("Not"), Logical.NOT);
        CONTEXT.addUnmodifiable(new VariableName("not"), Logical.NOT);
        CONTEXT.addUnmodifiable(new VariableName("!"), Logical.NOT);

        // if语句
        CONTEXT.addUnmodifiable(new VariableName("If"), Branch.IF);
        CONTEXT.addUnmodifiable(new VariableName("if"), Branch.IF);

        // 比较
        CONTEXT.addUnmodifiable(new VariableName("=="), NumberComparator.Equal.getInstance());
        CONTEXT.addUnmodifiable(new VariableName("<"), NumberComparator.Less.getInstance());
        CONTEXT.addUnmodifiable(new VariableName(">"), NumberComparator.Great.getInstance());
    }

}
