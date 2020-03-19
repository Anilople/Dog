package com.github.anilople.dog.backend.util;

import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.runtime.metafunction.Import;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ContextUtil {

  /**
   * 为了使用{@link Import}，需要对所有出现在{@link LambdaExpression}中的{@link VariableName}进行改名.
   * @param lambdaExpression 表达式
   * @param nameToNewNameFunction 改名的函数
   * @return 改名后的新表达式
   */
  public static LambdaExpression relink(
      LambdaExpression lambdaExpression,
      java.util.function.Function<VariableName, VariableName> nameToNewNameFunction
      ) {
    if(null == lambdaExpression) {
      return null;
    }

    if(lambdaExpression instanceof Name) {
      if(lambdaExpression instanceof VariableName) {
        VariableName variableName = (VariableName) lambdaExpression;
        return nameToNewNameFunction.apply(variableName);
      } else {
        return lambdaExpression;
      }
    } else if(lambdaExpression instanceof Function) {
      Function function = (Function) lambdaExpression;
      return new Function(
          (Name) relink(function.getArgument(), nameToNewNameFunction),
          relink(function.getBody(), nameToNewNameFunction)
      );
    } else if(lambdaExpression instanceof Application) {
      Application application = (Application) lambdaExpression;
      return new Application(
          relink(application.getLeft(), nameToNewNameFunction),
          relink(application.getRight(), nameToNewNameFunction)
      );
    } else {
      throw new IllegalStateException("无法处理的类型:" + lambdaExpression.getClass() + "，内容为" + lambdaExpression);
    }
  }

  public static Map<VariableName, Stack<LambdaExpression>> relink(String packageName, Map<VariableName, Stack<LambdaExpression>> scopes) {
    final Set<VariableName> oldVariableNames = Collections.unmodifiableSet(scopes.keySet());
    final java.util.function.Function<VariableName, VariableName> nameToNewNameFunction =
        variableName -> oldVariableNames.contains(variableName) ? PackageUtil.wrapperPackageName(packageName, variableName) : variableName;

    final Map<VariableName, Stack<LambdaExpression>> newScopes = new HashMap<>();
    for (Map.Entry<VariableName, Stack<LambdaExpression>> entry : scopes.entrySet()) {
      VariableName newVariableName = PackageUtil.wrapperPackageName(packageName, entry.getKey());
      LambdaExpression oldValue = entry.getValue().peek();
      LambdaExpression newValue = relink(oldValue, nameToNewNameFunction);
      Stack<LambdaExpression> newStack = new Stack<>();
      newStack.add(newValue);
      newScopes.put(newVariableName, newStack);
    }

    return newScopes;
  }
}
