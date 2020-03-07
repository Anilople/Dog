package com.github.anilople.dog;

import com.github.anilople.dog.backend.runtime.Context;
import com.github.anilople.dog.backend.runtime.Interpreter;
import com.github.anilople.dog.backend.runtime.MetaContextEnvironment;
import com.github.anilople.dog.frontend.formater.CodeFormatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class DogLanguage {

    public static void main(String[] args) throws IOException {
        if(args.length <= 0) {
            // 进入 交互模式
            interaction();
        } else if(args[0].equals("-format")) {
            format(Arrays.copyOfRange(args, 1, args.length));
        } else {
            // 传入了文件路径
            // 执行这些代码
            execute(args);
        }
    }

    /**
     * 交互模式，
     * 读入用户输入，
     * 然后evaluation
     */
    public static void interaction() {
        System.out.println("交互模式：读取用户的输入，然后evaluation");
        // 系统环境
        Context context = MetaContextEnvironment.getMetaContext();

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
     * 仅仅格式化代码文件，不执行
     * 注意无论代码是否符合parser，都会进行format
     * @param filePaths 所有代码文件的路径
     */
    public static void format(String[] filePaths) throws IOException {
        for(String filePath : filePaths) {
            Path path = Paths.get(filePath);
            CodeFormatter.format(path);
        }
    }

    /**
     * 执行代码
     * @param filePaths 代码的路径
     * @throws IOException 如果IO出错
     */
    public static void execute(String[] filePaths) throws IOException {
        for (String filePath : filePaths) {
            // 将这些文件作为输入，进行解释
            Path path = Paths.get(filePath);
            byte[] bytes = Files.readAllBytes(path);
            final String text = new String(bytes);
            try {
                Interpreter.interpret(text);
                // 可以正常执行，那么就对这个代码文件进行format
                CodeFormatter.format(path);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
