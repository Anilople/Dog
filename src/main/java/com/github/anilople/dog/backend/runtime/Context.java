package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.runtime.environment.CodeLoader;
import com.github.anilople.dog.backend.runtime.environment.LocalCodeLoader;
import com.github.anilople.dog.backend.runtime.environment.PackageTree;
import com.github.anilople.dog.backend.runtime.metafunction.Import;
import com.github.anilople.dog.backend.util.ContextUtil;
import com.github.anilople.dog.backend.util.PackageUtil;
import com.github.anilople.dog.util.MapUtil;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 某条语句执行时的上下文， 也就是状态
 */
public class Context {

  private static final String META_SCOPES_PACKAGE_NAME = "";

  /**
   * 代码加载器
   */
  private final List<CodeLoader> codeLoaders;
  /**
   * 存储这个{@link Context}已经{@link Import}了那些包. 以及包对应的{@link Context}
   */
  private final PackageTree<Map<VariableName, Stack<LambdaExpression>>> packageTree = new PackageTree<>();
  /**
   * 包名，必须添加. 默认的包名无节点
   */
  private String currentPackageName = "";

  public Context() {
    this.codeLoaders = Collections.singletonList(LocalCodeLoader.getDefaultInstance());

    Map<VariableName, Stack<LambdaExpression>> scopes = MapUtil.transferValue(
        MetaContextEnvironment.getMetaScopes(),
        lambdaExpression -> {
          Stack<LambdaExpression> stack = new Stack<>();
          stack.push(lambdaExpression);
          return stack;
        }
    );

    this.packageTree.add(META_SCOPES_PACKAGE_NAME, new ConcurrentHashMap<>(scopes));
  }

  public Context(String currentPackageName) {
    this();
    this.currentPackageName = currentPackageName;
  }

  private Context(List<CodeLoader> codeLoaders) {
    this.codeLoaders = codeLoaders;

    Map<VariableName, Stack<LambdaExpression>> scopes = MapUtil.transferValue(
        MetaContextEnvironment.getMetaScopes(),
        lambdaExpression -> {
          Stack<LambdaExpression> stack = new Stack<>();
          stack.push(lambdaExpression);
          return stack;
        }
    );

    this.packageTree.add(META_SCOPES_PACKAGE_NAME, new ConcurrentHashMap<>(scopes));
  }

  public Context(String currentPackageName, List<CodeLoader> codeLoaders) {
    this(codeLoaders);
    this.currentPackageName = currentPackageName;
  }

  /**
   * 添加新的，包里面的内容
   *
   * @param packageTree          包和scope一一对应的数据结构
   * @param currentPackageName   当前包名
   * @param newScopesPackageName 要添加的包的包名
   * @param newScopes            新包的变量表
   */
  static void addContextFrom(
      final PackageTree<Map<VariableName, Stack<LambdaExpression>>> packageTree,
      final String currentPackageName,
      final String newScopesPackageName,
      final Map<VariableName, Stack<LambdaExpression>> newScopes) {
    if (!packageTree.exists(newScopesPackageName)) {
      // 之前没加入，才会进行加入
      final Map<VariableName, Stack<LambdaExpression>> thatNewScopes;
      if (currentPackageName.equals(newScopesPackageName)) {
        // 需要relink
        thatNewScopes = ContextUtil.relink(currentPackageName, newScopes);
      } else {
        // 已经relink过了，不需要relink
        thatNewScopes = newScopes;
      }
      packageTree.add(newScopesPackageName, thatNewScopes);
    }
  }

  /**
   * 首字母大写的{@link VariableName}是不可变的
   *
   * @return {@link VariableName}是否是可变的
   */
  static boolean isUnmodifiable(VariableName variableName) {
    final String first = variableName.getLiterals().charAt(0) + "";
    return first.matches("[A-Z]");
  }

  /**
   * 给{@link Context}添加包名
   *
   * @param packageName 包名
   * @throws IllegalStateException 如果包名已经存在
   */
  synchronized public void addPackageName(String packageName) {
    if (this.currentPackageName.length() > 0) {
      throw new IllegalStateException("包名已经存在:" + this.currentPackageName);
    }
    this.currentPackageName = packageName;
  }

  /**
   * 确保存在包名.
   *
   * @throws IllegalStateException 如果不存在包名
   */
  public void ensureExistsPackageName() {
    if (null == this.currentPackageName || this.currentPackageName.length() <= 0) {
      throw new IllegalStateException("包名不存在，请加入包名后再进行操作");
    }
  }

  Map<VariableName, Stack<LambdaExpression>> getScopesByPackageName(String packageName) {
    if (!packageTree.exists(packageName)) {
      packageTree.add(packageName, new ConcurrentHashMap<>());
    }
    return packageTree.get(packageName);
  }

  /**
   * 根据包名获取代码文本
   *
   * @param packageName 包名
   * @return 代码文本
   * @throws IllegalStateException 如果包名不存在
   */
  String getCodeTextFrom(String packageName) {
    for (CodeLoader codeLoader : this.codeLoaders) {
      if (codeLoader.exists(packageName)) {
        return codeLoader.getText(packageName);
      }
    }
    throw new IllegalStateException(packageName + "不存在");
  }

  /**
   * 从某个{@link Context}中添加环境. {@link Context}中使用的其它包也会被加入
   */
  private void addContextFrom(Context that) {
    that.packageTree.forEach(
        (packageName, scopes) ->
            addContextFrom(this.packageTree, this.currentPackageName, packageName, scopes)
    );
  }

  /**
   * 从包中加载新的{@link Context}
   *
   * @param packageName 包名，用来寻找到代码
   */
  public void addContextFrom(String packageName) {
    // 不存在才添加
    if (!packageTree.exists(packageName)) {
      // 获取代码
      final String codeText = this.getCodeTextFrom(packageName);
      // 获取 meta context
      final Context codeContext = new Context();

      // 在 meta context下运行代码
      Interpreter.interpret(codeText, codeContext);

      // 得到了包对应的 context
      // code context准备完毕，将其加入当前的context
      this.addContextFrom(codeContext);
    }
  }

  /**
   * @param variableName 变量
   * @return 变量是否存在绑定
   */
  public boolean exists(VariableName variableName) {
    if (!PackageUtil.containPackageSeparator(variableName)) {
      // 使用了meta，或者仅仅在本包内
      // 先检查meta
      if (this.packageTree.get(META_SCOPES_PACKAGE_NAME).containsKey(variableName)) {
        return true;
      }

      // 再检查本包
      Map<VariableName, Stack<LambdaExpression>> currentPackageScopes = this
          .getScopesByPackageName(this.currentPackageName);
      if (currentPackageScopes.containsKey(variableName)) {
        Stack<LambdaExpression> stack = currentPackageScopes.get(variableName);
        return !stack.isEmpty();
      } else {
        // do nothing
      }
    } else {
      // 使用了指定的包
      // 找出使用了哪个包
      final String packageName = PackageUtil
          .resolvePackageName(this.currentPackageName, variableName);

      // 包对应的scope
      final Map<VariableName, Stack<LambdaExpression>> packageScopes = this
          .getScopesByPackageName(packageName);

      // 检查是不是使用了当前的包，todo

      // 使用了其它包
      final VariableName variableNameWithoutPackage = PackageUtil
          .resolveVariableName(this.currentPackageName, variableName);

      // 带包的全路径变量
      final VariableName variableNameWithFullPackage = PackageUtil
          .wrapperPackageName(packageName, variableNameWithoutPackage);

      if (packageScopes.containsKey(variableNameWithFullPackage)) {
        Stack<LambdaExpression> stack = packageScopes.get(variableNameWithFullPackage);
        // 不为空说明存在
        return !stack.isEmpty();
      }
    }
    return false;
  }

  /**
   * @param variableName 变量
   * @throws RuntimeException 如果变量不存在
   */
  private void ensureExists(VariableName variableName) {
    if (!exists(variableName)) {
      throw new RuntimeException(variableName + "未定义");
    }
  }

  /**
   * 添加{@link VariableName}和{@link LambdaExpression}对应的引用
   *
   * @param variableName     变量
   * @param lambdaExpression 变量代表的{@link LambdaExpression}
   * @throws IllegalStateException 如果包名不对
   * @throws RuntimeException      如果变量不可变，并且已经绑定
   */
  public void addToScope(VariableName variableName, LambdaExpression lambdaExpression) {

    // 获得包名
    final String packageName = PackageUtil
        .resolvePackageName(this.currentPackageName, variableName);

    if (!this.currentPackageName.equals(packageName)) {
      // 得到的包名和当前包名不同
      throw new IllegalStateException(packageName + " 不同于当前所属包名 " + this.currentPackageName);
    }

    final VariableName realVariableName = PackageUtil
        .resolveVariableName(this.currentPackageName, variableName);

    if (exists(variableName) && isUnmodifiable(realVariableName)) {
      throw new RuntimeException(variableName + "不可变");
    }

    final Map<VariableName, Stack<LambdaExpression>> scopes = packageTree.get(packageName);

    if (!scopes.containsKey(realVariableName)) {
      scopes.put(realVariableName, new Stack<>());
    }

    scopes.get(realVariableName).push(lambdaExpression);
  }

  /**
   * @param variableName 变量
   * @return 变量代表的表达式
   */
  public LambdaExpression get(VariableName variableName) {

    // 确保存在
    ensureExists(variableName);

    // 先检查 在meta中有没有
    if (this.packageTree.get(META_SCOPES_PACKAGE_NAME).containsKey(variableName)) {
      // 返回栈顶
      return this.packageTree.get(META_SCOPES_PACKAGE_NAME).get(variableName).peek();
    }

    // 获得包名
    final String packageName = PackageUtil
        .resolvePackageName(this.currentPackageName, variableName);

    final VariableName realVariableName = PackageUtil
        .resolveVariableName(this.currentPackageName, variableName);

    final VariableName key;
    if (!this.currentPackageName.equals(packageName)) {
      // 得到的包名和当前包名不同
      // 属于其它模块
      key = PackageUtil.wrapperPackageName(packageName, realVariableName);
    } else {
      // 属于本模块
      key = realVariableName;
    }

    Map<VariableName, Stack<LambdaExpression>> scopes = this.getScopesByPackageName(packageName);

    Stack<LambdaExpression> stack = scopes.get(key);
    // 返回栈顶
    return stack.peek();
  }

  /**
   * 将之前变量和{@link LambdaExpression}的绑定关系解除， 注意只会解除一个！！. 只可以解除自己所属包下的绑定
   *
   * @param variableName 变量
   * @throws IllegalStateException 如果尝试解除的变量是其它包的
   */
  public void removeFromScope(VariableName variableName) {
    // 确保存在
    ensureExists(variableName);

    // 获得包名
    final String packageName = PackageUtil
        .resolvePackageName(this.currentPackageName, variableName);

    if (!this.currentPackageName.equals(packageName)) {
      throw new IllegalStateException(
          "在包" + this.currentPackageName + "，不能解除包" + packageName + "中的绑定");
    }

    final VariableName realVariableName = PackageUtil
        .resolveVariableName(this.currentPackageName, variableName);

    Map<VariableName, Stack<LambdaExpression>> scopes = this.getScopesByPackageName(packageName);

    final VariableName key;
    if (!this.currentPackageName.equals(packageName)) {
      // 得到的包名和当前包名不同
      // 属于其它模块
      key = realVariableName;
    } else {
      key = PackageUtil.wrapperPackageName(packageName, realVariableName);
    }

    // 将其移除
    Stack<LambdaExpression> stack = scopes.get(realVariableName);
    if (stack.empty()) {
      throw new RuntimeException(variableName + "无定义");
    } else {
      stack.pop();
    }
  }

  public String getCurrentPackageName() {
    return currentPackageName;
  }

}
