package com.github.anilople.dog.backend.runtime;

/**
 * 运行时的数字
 */
public class Numbers {

    public static AbstractRuntimeName ZERO = Zero.getInstance();

    /**
     * 代表运行时的数字"0"
     */
    private static class Zero extends AbstractRuntimeName {

        public static final Zero INSTANCE = new Zero("0");

        private Zero(String name) {
            super(name);
        }

        @Override
        public boolean isReducible(Context context) {
            return false;
        }

        public static Zero getInstance() {
            return INSTANCE;
        }
    }

}
