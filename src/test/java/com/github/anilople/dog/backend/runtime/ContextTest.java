package com.github.anilople.dog.backend.runtime;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContextTest {

  /**
   * 重复绑定不可变的变量
   */
  @Test
  void isUnmodifiable() {
    String text = "(Bind[ABCDEFG][1])" + "(Bind[ABCDEFG][2])";
    assertThrows(
        RuntimeException.class,
        () -> Interpreter.interpret(text)
    );
  }
}