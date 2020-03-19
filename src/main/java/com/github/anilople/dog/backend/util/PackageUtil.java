package com.github.anilople.dog.backend.util;

import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.runtime.metafunction.Package;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PackageUtil {

  /**
   * 获取包名上的节点
   * "a.b.c" -> ["a", "b", "c"]
   * @param packageName 包名
   * @return 多个节点（有序）
   */
  public static List<String> getNodes(String packageName) {
    ensureIsPackageName(packageName);
    // 根据 . 分割
    String[] nodes = packageName.split("\\" + Package.PACKAGE_NODE_SEPARATOR);
    return Arrays.asList(nodes);
  }

  public static boolean isPackageName(String packageName) {
    return packageName.matches("([a-zA-Z]+\\.)*([A-Z][a-zA-Z]*)?");
  }

  /**
   * 获取包名的最后一个节点
   * a.b.c -> c
   * @param packageName 包名
   * @return 最后一个节点的名称
   */
  public static String getLastNode(String packageName) {
    List<String> nodes = getNodes(packageName);
    return nodes.get(nodes.size() - 1);
  }

  /**
   * 移除最后一个节点.
   * "a.b.c" -> "a.b"
   * "a" -> ""
   * @param name
   * @return
   */
  public static String removeLastNode(String name) {
    // 根据 . 分割
    String[] nodes = name.split("\\" + Package.PACKAGE_NODE_SEPARATOR);
    // 假设nodes的数量大于1
    // 去除最后一个节点，只保留包相关的部分
    String[] packageNodes = new String[nodes.length - 1];
    System.arraycopy(nodes, 0, packageNodes, 0, nodes.length - 1);

    return String.join(Package.PACKAGE_NODE_SEPARATOR, packageNodes);
  }

  /**
   * 检查包名是否合法
   * @param packageName 包名
   * @throws IllegalStateException 如果报名不合法
   */
  public static void ensureIsPackageName(String packageName) {
    if(!isPackageName(packageName)) {
      throw new IllegalStateException(packageName + "不是合法的包名");
    }
  }

  public static String packageNameToPathName(String packageName) {
    return packageName.replace(Package.PACKAGE_NODE_SEPARATOR, File.separator);
  }

  /**
   * 加入包名的前缀.
   * 包名为 com.github，{@link VariableName}为Node时，
   * 变成 com.github.Node
   * @param packageName 包名
   * @param variableName 未添加包名的变量
   * @return 加入包名后的变量
   */
  public static VariableName wrapperPackageName(String packageName, VariableName variableName) {
    if(packageName.length() > 0) {
      return new VariableName(packageName + Package.PACKAGE_NODE_SEPARATOR + variableName.getLiterals());
    } else {
      return variableName;
    }
  }

  /**
   * 根据变量名获取包名
   */
  public static String resolvePackageName(String currentPackageName, VariableName variableName) {
    if(variableName.getLiterals().contains(Package.PACKAGE_NODE_SEPARATOR)) {
      // 包含 分隔符
      return removeLastNode(variableName.getLiterals());
    } else {
      // 不包含分隔符
      return currentPackageName;
    }
  }

  /**
   * 移除包名的前缀（包括分隔符）
   * "a.b.c" -> "c"
   * "a" -> "a"
   */
  public static VariableName resolveVariableName(String currentPackageName, VariableName variableName) {
    if(variableName.getLiterals().contains(Package.PACKAGE_NODE_SEPARATOR)) {
      // 包含 分隔符
      // 根据 . 分割
      String[] nodes = variableName.getLiterals().split("\\" + Package.PACKAGE_NODE_SEPARATOR);
      return new VariableName(nodes[nodes.length - 1]);
    } else {
      return variableName;
    }
  }

  /**
   *
   * @param variableName 变量名
   * @return 变量名中是否有包分割符
   */
  public static boolean containPackageSeparator(VariableName variableName) {
    return variableName.getLiterals().contains(Package.PACKAGE_NODE_SEPARATOR);
  }
}
