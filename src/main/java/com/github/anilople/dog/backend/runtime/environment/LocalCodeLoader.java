package com.github.anilople.dog.backend.runtime.environment;

import com.github.anilople.dog.backend.constant.PathNameConstant;
import com.github.anilople.dog.backend.util.PackageUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * 从本地的文件系统中读取代码
 */
public class LocalCodeLoader implements CodeLoader {

  /**
   * 从程序的运行路径获取.
   * 从指定的包路径获取.
   */
  private static final LocalCodeLoader DEFAULT_INSTANCE = new LocalCodeLoader(
      Arrays.asList(
          Paths.get(PathNameConstant.WORK_DIRECTORY),
          Paths.get(PathNameConstant.LIBRARY_ROOT)
      )
  );
  /**
   * 代码所处的起始路径. 设计生
   */
  private final List<Path> roots;

  public LocalCodeLoader(List<Path> roots) {
    this.roots = roots;
  }

  public static LocalCodeLoader getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static Path resolveCodeFilePath(Path base, String packageName) {
    return base.resolve(
        PackageUtil.packageNameToPathName(packageName) + PathNameConstant.CODE_FILE_SUFFIX);
  }

  public Path getCodeFilePath(String packageName) {
    // 包名 + 代码文件的后缀
    for (Path root : roots) {
      Path path = resolveCodeFilePath(root, packageName);
      if (Files.exists(path)) {
        return path;
      }
    }
    // 没找到
    throw new IllegalStateException(packageName + "不存在");
  }

  @Override
  public String getText(String packageName) {
    Path path = this.getCodeFilePath(packageName);
    final byte[] bytes;
    try {
      bytes = Files.readAllBytes(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new String(bytes);
  }

  @Override
  public boolean exists(String packageName) {
    for (Path root : roots) {
      Path path = resolveCodeFilePath(root, packageName);
      if (Files.exists(path)) {
        return true;
      }
    }
    return false;
  }

}
