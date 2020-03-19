package com.github.anilople.dog.backend.runtime.metafunction;

import com.github.anilople.dog.backend.ast.NullName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 包管理，
 * 程序声明自己属于哪个包下。
 * 导入其它包使用{@link Import}
 */
public class Package extends AbstractRuntimeFunction {

  public static final String PACKAGE_NODE_SEPARATOR = ".";

  private static final Package PACKAGE = new Package(null, null);

  public static Package getInstance() {
    return PACKAGE;
  }

  public Package(Name argument,
      LambdaExpression body) {
    super(argument, body);
  }

  @Override
  public LambdaExpression call(LambdaExpression replacement, Context context) {
    final Name name = (Name) replacement;
    // 包名
    final String packageName = name.toString();
    // 添加包名
    context.addPackageName(packageName);
    return NullName.getInstance();
  }

  @Override
  public String toString() {
    return "package.name -> { Package[package.name] }";
  }
}
