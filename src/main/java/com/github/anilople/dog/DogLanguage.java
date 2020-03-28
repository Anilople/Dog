package com.github.anilople.dog;

import com.github.anilople.dog.backend.constant.PathNameConstant;
import com.github.anilople.dog.backend.runtime.Context;
import com.github.anilople.dog.backend.runtime.Interpreter;
import com.github.anilople.dog.constant.DogConstant;
import com.github.anilople.dog.frontend.formater.CodeFormatter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;

public class DogLanguage {

  public static void main(String[] args) throws IOException {
    if (args.length <= 0) {
      printUsage();
    } else if (args[0].equals("-i")) {
      // 进入 交互模式
      interaction();
    } else if (args[0].equals("-format")) {
      format(Arrays.copyOfRange(args, 1, args.length));
    } else if (args[0].equals("-run")) {
      // 传入了文件路径
      // 执行这些代码，记得将参数去除，将文件名留下
      execute(Arrays.copyOfRange(args, 1, args.length));
    } else {
      throw new IllegalStateException("无法识别的命令: " + Arrays.toString(args));
    }
  }

  /**
   * 输出帮助信息.
   */
  public static void printUsage() {
    final StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("Dog编程语言，版本 ").append(DogConstant.VERSION).append(System.lineSeparator());
    stringBuilder.append("使用参数 -i 可以进入交互模式").append(System.lineSeparator());
    stringBuilder.append("使用参数 -run 代码文件名 可以运行代码").append(System.lineSeparator());
    stringBuilder.append("使用参数 -format 文件名或路径 可以对代码执行格式化").append(System.lineSeparator());
    stringBuilder.append("通过VM参数: ").append(PathNameConstant.CODE_LIBRARY_ROOT).append(" 指定包的路径").append(System.lineSeparator());

    System.out.println(stringBuilder.toString());
  }

  /**
   * 交互模式， 读入用户输入， 然后evaluation
   */
  public static void interaction() {
    System.out.println("交互模式：读取用户的输入，然后evaluation");
    // 系统环境
    Context context = new Context();

    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      try {
        Interpreter.interpret(line, context);
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 仅仅格式化代码文件，不执行 注意无论代码是否符合parser，都会进行format
   *
   * @param filePaths 所有代码文件的路径
   */
  public static void format(String[] filePaths) throws IOException {

    final Consumer<Path> pathConsumer = path -> {
      // 是文件，并且后缀符合，才会进行格式化
      if (!Files.isDirectory(path) && path.getFileName().toString().endsWith(PathNameConstant.CODE_FILE_SUFFIX)) {
        try {
          CodeFormatter.format(path);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };

    for (String filePath : filePaths) {
      Path path = Paths.get(filePath);
      Files.walk(path).forEach(pathConsumer);
    }
  }

  /**
   * 执行代码
   *
   * @param filePaths 代码的路径
   * @throws IOException 如果IO出错
   */
  public static void execute(String[] filePaths) throws IOException {
    for (String filePath : filePaths) {
      // 将这些文件作为输入，进行解释
      Path path = Paths.get(filePath);
      execute(path);
      // 可以正常执行，那么就对这个代码文件进行format
      CodeFormatter.format(path);
    }
  }

  /**
   * 只运行一个代码文件
   * @param path 代码文件
   * @throws IOException 如果无法读取代码
   */
  public static void execute(Path path) throws IOException {
    byte[] bytes = Files.readAllBytes(path);
    final String text = new String(bytes);
    try {
      Interpreter.interpret(text);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
