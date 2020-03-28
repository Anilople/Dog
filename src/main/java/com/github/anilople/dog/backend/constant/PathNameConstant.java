package com.github.anilople.dog.backend.constant;

/**
 * 与文件, 路径相关的常量
 */
public class PathNameConstant {

  /**
   * 代码的扩展名，后缀
   */
  public static final String CODE_FILE_SUFFIX = ".dog";

  /**
   * 程序的运行路径
   */
  public static final String WORK_DIRECTORY = System.getProperty("user.dir");

  /**
   * 通过vm参数
   * {@code
   *  -Dcode.library.root=xxx
   * }
   * 指定library的路径
   */
  public static final String CODE_LIBRARY_ROOT = "code.library.root";

}
