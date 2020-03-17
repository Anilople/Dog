package com.github.anilople.dog.backend.runtime.metafunction;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.runtime.Context;
import org.junit.jupiter.api.Test;

class PackageTest {

  @Test
  void call() {
    Package packageInstance = Package.getInstance();
    Context context = new Context("");
    packageInstance.call(new VariableName("b.Tree"), context);

    assertEquals("b.Tree", context.getCurrentPackageName());
  }
}