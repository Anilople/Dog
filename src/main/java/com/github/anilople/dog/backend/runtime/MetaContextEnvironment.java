package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.NullName;
import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.definition.Booleans;
import com.github.anilople.dog.backend.definition.Functions;
import com.github.anilople.dog.backend.definition.branch.Branch;
import com.github.anilople.dog.backend.definition.operations.Logical;
import com.github.anilople.dog.backend.runtime.metafunction.Bind;
import com.github.anilople.dog.backend.runtime.metafunction.IsNullName;
import com.github.anilople.dog.backend.runtime.metafunction.Let;
import com.github.anilople.dog.backend.runtime.metafunction.input.InputLine;
import com.github.anilople.dog.backend.runtime.metafunction.operations.*;
import com.github.anilople.dog.backend.runtime.metafunction.operations.comparator.NumberComparator;
import com.github.anilople.dog.backend.runtime.metafunction.output.Print;
import com.github.anilople.dog.backend.runtime.metafunction.output.PrintLine;
import com.github.anilople.dog.backend.runtime.metafunction.output.PrintNumber;

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
        CONTEXT.addToScope(new VariableName(Bind.class.getSimpleName()), Bind.getInstance());

        // let 表达式
        CONTEXT.addToScope(new VariableName(Let.class.getSimpleName()), Let.getInstance());

        // NULL
        CONTEXT.addToScope(new VariableName("Null"), NullName.getInstance());
        CONTEXT.addToScope(new VariableName("NULL"), NullName.getInstance());
        // 判断是否为NULL
        CONTEXT.addToScope(new VariableName("Null?"), IsNullName.getInstance());
        CONTEXT.addToScope(new VariableName("IsNull"), IsNullName.getInstance());

        // 输出
        CONTEXT.addToScope(new VariableName(PrintLine.class.getSimpleName()), PrintLine.getInstance());
        CONTEXT.addToScope(new VariableName(Print.class.getSimpleName()), Print.getInstance());
        CONTEXT.addToScope(new VariableName(PrintNumber.class.getSimpleName()), PrintNumber.getInstance());

        // 输入
        CONTEXT.addToScope(new VariableName(InputLine.class.getSimpleName()), InputLine.getInstance());

        // 算术
        // 加法 +
        CONTEXT.addToScope(new VariableName(Add.class.getSimpleName()), Add.getInstance());
        CONTEXT.addToScope(new VariableName("+"), Add.getInstance());
        // 减法 -
        CONTEXT.addToScope(new VariableName(Sub.class.getSimpleName()), Sub.getInstance());
        CONTEXT.addToScope(new VariableName("-"), Sub.getInstance());
        // 乘法 *
        CONTEXT.addToScope(new VariableName(Mul.class.getSimpleName()), Mul.getInstance());
        CONTEXT.addToScope(new VariableName("*"), Mul.getInstance());
        // 除法 /
        CONTEXT.addToScope(new VariableName(Div.class.getSimpleName()), Div.getInstance());
        CONTEXT.addToScope(new VariableName("/"), Div.getInstance());
        // 取模 %
        CONTEXT.addToScope(new VariableName(Mod.class.getSimpleName()), Mod.getInstance());
        CONTEXT.addToScope(new VariableName("%"), Mod.getInstance());

        // id函数
        CONTEXT.addToScope(new VariableName("Id"), Functions.ID);
        CONTEXT.addToScope(new VariableName("id"), Functions.ID);

        // 布尔函数
        CONTEXT.addToScope(new VariableName("True"), Booleans.TRUE);
        CONTEXT.addToScope(new VariableName("true"), Booleans.TRUE);
        CONTEXT.addToScope(new VariableName("False"), Booleans.FALSE);
        CONTEXT.addToScope(new VariableName("false"), Booleans.FALSE);

        // 布尔运算
        CONTEXT.addToScope(new VariableName("And"), Logical.AND);
        CONTEXT.addToScope(new VariableName("and"), Logical.AND);
        CONTEXT.addToScope(new VariableName("&&"), Logical.AND);

        CONTEXT.addToScope(new VariableName("Or"), Logical.OR);
        CONTEXT.addToScope(new VariableName("or"), Logical.OR);
        CONTEXT.addToScope(new VariableName("||"), Logical.OR);

        CONTEXT.addToScope(new VariableName("Not"), Logical.NOT);
        CONTEXT.addToScope(new VariableName("not"), Logical.NOT);
        CONTEXT.addToScope(new VariableName("!"), Logical.NOT);

        // if语句
        CONTEXT.addToScope(new VariableName("If"), Branch.IF);
        CONTEXT.addToScope(new VariableName("if"), Branch.IF);

        // 比较
        CONTEXT.addToScope(new VariableName("=="), NumberComparator.Equal.getInstance());
        CONTEXT.addToScope(new VariableName("<"), NumberComparator.Less.getInstance());
        CONTEXT.addToScope(new VariableName(">"), NumberComparator.Great.getInstance());
    }

}
