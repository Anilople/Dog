package com.github.anilople.dog;

import com.github.anilople.dog.backend.runtime.Context;
import com.github.anilople.dog.backend.runtime.Interpreter;
import com.github.anilople.dog.backend.runtime.MetaContextEnvironment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class DogLanguage {

    public static void main(String[] args) throws IOException {
        if(args.length <= 0) {
            // 进入 交互模式
            interaction();
        } else {
            // 传入了文件路径
            for(String filePath : args) {
                // 将这些文件作为输入，进行解释
                Path path = Paths.get(filePath);
                byte[] bytes = Files.readAllBytes(path);
                final String text = new String(bytes);
                Interpreter.interpret(text);
            }
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

}
