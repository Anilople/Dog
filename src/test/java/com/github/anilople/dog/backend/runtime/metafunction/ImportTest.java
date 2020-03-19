package com.github.anilople.dog.backend.runtime.metafunction;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.runtime.Context;
import com.github.anilople.dog.backend.runtime.environment.CodeLoader;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ImportTest {

  @Test
  void simpleImport() {
    final CodeLoader codeLoader = Mockito.mock(CodeLoader.class);
    Mockito.when(codeLoader.exists(Mockito.anyString())).thenReturn(true);
    Mockito.when(codeLoader.getText(Mockito.anyString())).thenReturn("(Package[Pair])(Bind[One][1])");

    final Context context = new Context("Pair", Collections.singletonList(codeLoader));

    Import importInstance = Import.getInstance();

    importInstance.call(new VariableName("Pair"), context);

    assertTrue(context.exists(new VariableName("Pair.One")));
  }

}