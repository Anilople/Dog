package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.NullName;
import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.definition.Booleans;
import com.github.anilople.dog.backend.definition.Functions;
import com.github.anilople.dog.backend.definition.branch.Branch;
import com.github.anilople.dog.backend.definition.operations.Logical;
import com.github.anilople.dog.backend.runtime.metafunction.Bind;
import com.github.anilople.dog.backend.runtime.metafunction.Import;
import com.github.anilople.dog.backend.runtime.metafunction.IsNullName;
import com.github.anilople.dog.backend.runtime.metafunction.Let;
import com.github.anilople.dog.backend.runtime.metafunction.Package;
import com.github.anilople.dog.backend.runtime.metafunction.input.InputLine;
import com.github.anilople.dog.backend.runtime.metafunction.operations.Add;
import com.github.anilople.dog.backend.runtime.metafunction.operations.Div;
import com.github.anilople.dog.backend.runtime.metafunction.operations.Mod;
import com.github.anilople.dog.backend.runtime.metafunction.operations.Mul;
import com.github.anilople.dog.backend.runtime.metafunction.operations.Sub;
import com.github.anilople.dog.backend.runtime.metafunction.operations.comparator.NumberComparator;
import com.github.anilople.dog.backend.runtime.metafunction.output.Print;
import com.github.anilople.dog.backend.runtime.metafunction.output.PrintLine;
import com.github.anilople.dog.backend.runtime.metafunction.output.PrintNumber;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 解释器运行之前， 设置的环境
 */
public class MetaContextEnvironment {

  /**
   * @return meta 绑定的 scope
   */
  public static Map<VariableName, LambdaExpression> getMetaScopes() {
    Map<VariableName, LambdaExpression> scopes = new HashMap<>();

    // 绑定，定义变量
    scopes.put(new VariableName(Bind.class.getSimpleName()), Bind.getInstance());

    // let 表达式
    scopes.put(new VariableName(Let.class.getSimpleName()), Let.getInstance());

    // 包管理
    scopes.put(new VariableName(Package.class.getSimpleName()), Package.getInstance());
    scopes.put(new VariableName(Import.class.getSimpleName()), Import.getInstance());

    // NULL
    scopes.put(new VariableName("Null"), NullName.getInstance());
    scopes.put(new VariableName("NULL"), NullName.getInstance());
    // 判断是否为NULL
    scopes.put(new VariableName("Null?"), IsNullName.getInstance());
    scopes.put(new VariableName("IsNull"), IsNullName.getInstance());

    // 输出
    scopes.put(new VariableName(PrintLine.class.getSimpleName()), PrintLine.getInstance());
    scopes.put(new VariableName(Print.class.getSimpleName()), Print.getInstance());
    scopes.put(new VariableName(PrintNumber.class.getSimpleName()), PrintNumber.getInstance());

    // 输入
    scopes.put(new VariableName(InputLine.class.getSimpleName()), InputLine.getInstance());

    // 算术
    // 加法 +
    scopes.put(new VariableName(Add.class.getSimpleName()), Add.getInstance());
    scopes.put(new VariableName("+"), Add.getInstance());
    // 减法 -
    scopes.put(new VariableName(Sub.class.getSimpleName()), Sub.getInstance());
    scopes.put(new VariableName("-"), Sub.getInstance());
    // 乘法 *
    scopes.put(new VariableName(Mul.class.getSimpleName()), Mul.getInstance());
    scopes.put(new VariableName("*"), Mul.getInstance());
    // 除法 /
    scopes.put(new VariableName(Div.class.getSimpleName()), Div.getInstance());
    scopes.put(new VariableName("/"), Div.getInstance());
    // 取模 %
    scopes.put(new VariableName(Mod.class.getSimpleName()), Mod.getInstance());
    scopes.put(new VariableName("%"), Mod.getInstance());

    // id函数
    scopes.put(new VariableName("Id"), Functions.ID);
    scopes.put(new VariableName("id"), Functions.ID);

    // 布尔函数
    scopes.put(new VariableName("True"), Booleans.TRUE);
    scopes.put(new VariableName("true"), Booleans.TRUE);
    scopes.put(new VariableName("False"), Booleans.FALSE);
    scopes.put(new VariableName("false"), Booleans.FALSE);

    // 布尔运算
    scopes.put(new VariableName("And"), Logical.AND);
    scopes.put(new VariableName("and"), Logical.AND);
    scopes.put(new VariableName("&&"), Logical.AND);

    scopes.put(new VariableName("Or"), Logical.OR);
    scopes.put(new VariableName("or"), Logical.OR);
    scopes.put(new VariableName("||"), Logical.OR);

    scopes.put(new VariableName("Not"), Logical.NOT);
    scopes.put(new VariableName("not"), Logical.NOT);
    scopes.put(new VariableName("!"), Logical.NOT);

    // if语句
    scopes.put(new VariableName("If"), Branch.IF);
    scopes.put(new VariableName("if"), Branch.IF);

    // 比较
    scopes.put(new VariableName("=="), NumberComparator.Equal.getInstance());
    scopes.put(new VariableName("<"), NumberComparator.Less.getInstance());
    scopes.put(new VariableName(">"), NumberComparator.Great.getInstance());

    return Collections.unmodifiableMap(scopes);
  }

}
