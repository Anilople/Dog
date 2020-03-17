package com.github.anilople.dog.backend.runtime.environment;

/**
 * 代码加载器
 */
public interface CodeLoader {

  /**
   * @param packageName 包名
   * @return 代码文本
   */
  String getText(String packageName);

  /**
   * @param packageName 包名
   * @return 是否存在包
   */
  boolean exists(String packageName);
}
