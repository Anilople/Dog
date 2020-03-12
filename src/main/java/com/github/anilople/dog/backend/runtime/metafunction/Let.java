package com.github.anilople.dog.backend.runtime.metafunction;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.AbstractRuntimeFunction;
import com.github.anilople.dog.backend.runtime.Context;

/**
 * let表达式，定义临时变量
 * \vlb.b
 * v代表{@link VariableName},
 * l代表{@link LambdaExpression},
 * b代表{@link LambdaExpression}.
 * 定义一个变量v代表l，在b的执行时访问变量v会得到l
 */
public class Let extends AbstractRuntimeFunction {

  private static final Let LET = new Let(null, null);

  public static Let getInstance() {
    return LET;
  }

  public Let(Name argument,
      LambdaExpression body) {
    super(argument, body);
  }

  @Override
  public LambdaExpression call(LambdaExpression replacement, Context context) {
    VariableName variableName = (VariableName) replacement;
    return new LetLambda(null, null, variableName);
  }

  @Override
  public String toString() {
    return "variable -> { lambda -> { body -> { let variable <- lambda in body } } }";
  }

  static class LetLambda extends AbstractRuntimeFunction {

    private final VariableName variableName;

    public LetLambda(Name argument,
        LambdaExpression body, VariableName variableName) {
      super(argument, body);
      this.variableName = variableName;
    }

    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
      // 添加
      context.addToScope(variableName, replacement);
      return new LetBody(null, null, variableName);
    }

    @Override
    public String toString() {
      return "lambda -> { body -> { let " + variableName + " <- lambda in body } }";
    }
  }

  static class LetBody extends AbstractRuntimeFunction {

    private final VariableName variableName;

    public LetBody(Name argument,
        LambdaExpression body, VariableName variableName) {
      super(argument, body);
      this.variableName = variableName;
    }

    @Override
    public LambdaExpression call(LambdaExpression replacement, Context context) {
      LambdaExpression result = Evaluation.execute(replacement, context);

      // 销毁
      context.removeFromScope(variableName);

      return result;
    }

    @Override
    public String toString() {
      return "body -> { body }";
    }
  }
}
