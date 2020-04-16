package com.github.anilople.dog.backend.runtime.metafunction.assertion;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.definition.Booleans;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * 值必须和{@link Booleans#TRUE}有相同的结构.
 */
public class AssertTrue extends AbstractRuntimeFunction {

  public static final AssertTrue INSTANCE = new AssertTrue(null, null);

  public static AssertTrue getInstance() {
    return INSTANCE;
  }

  public AssertTrue(Name argument,
      LambdaExpression body) {
    super(argument, body);
  }

  @Override
  public LambdaExpression call(LambdaExpression replacement, Context context) {
    LambdaExpression result = Evaluation.execute(replacement, context);
    boolean b = result.isSameStructureWithoutReduction(Booleans.TRUE);
    if (!b) {
      throw new IllegalStateException("表达式不为true " + replacement);
    }
    return replacement;
  }

  @Override
  public String toString() {
    return "x -> { Assert[x] }";
  }
}
