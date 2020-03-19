package com.github.anilople.dog.backend.runtime.metafunction;

import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 包管理。
 * 可以导入多个{@link Package}
 */
public class Import extends AbstractRuntimeFunction {

  private static final Import IMPORT = new Import(null, null);

  public static Import getInstance() {
    return IMPORT;
  }

  public Import(Name argument,
      LambdaExpression body) {
    super(argument, body);
  }

  @Override
  public LambdaExpression call(LambdaExpression replacement, Context context) {
    final Name name = (Name) replacement;
    // 包名
    final String packageName = name.toString();

    // 从包中添加context
    context.addContextFrom(packageName);

    // 返回一个Import，以便于后续可以继续导入package
    return this;
  }

  @Override
  public String toString() {
    return "package.name -> { Import[package.name] }";
  }
}
