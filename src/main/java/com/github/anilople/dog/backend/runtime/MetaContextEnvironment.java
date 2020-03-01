package com.github.anilople.dog.backend.runtime;

import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.runtime.metafunction.Bind;
import com.github.anilople.dog.backend.runtime.metafunction.input.InputLine;
import com.github.anilople.dog.backend.runtime.metafunction.output.PrintLine;

/**
 * 解释器运行之前，
 * 设置的环境
 */
public class MetaContextEnvironment {

    private static final Context CONTEXT = new Context();

    public static Context getMetaContext() {
        // 新的meta上下文
        return new Context(CONTEXT);
    }

    static {
        // meta 绑定
        CONTEXT.addUnmodifiable(new VariableName(PrintLine.class.getSimpleName()), PrintLine.getInstance());
        CONTEXT.addUnmodifiable(new VariableName(Bind.class.getSimpleName()), Bind.getInstance());
        CONTEXT.addUnmodifiable(new VariableName(InputLine.class.getSimpleName()), InputLine.getInstance());
    }

}
