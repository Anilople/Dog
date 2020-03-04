package com.github.anilople.dog.backend.ast.number;

import com.github.anilople.dog.backend.ast.NumberName;

/**
 * @see Integer
 */
public class IntegerName extends NumberName {
    public IntegerName(String literals) {
        super(literals);
    }

    public IntegerName(int value) {
        this(String.valueOf(value));
    }

    @Override
    public NumberName add(NumberName that) {
        int x = Integer.parseInt(this.getLiterals());
        int y = Integer.parseInt(that.getLiterals());
        int result = x + y;
        return new IntegerName(result);
    }

    @Override
    public NumberName sub(NumberName that) {
        int x = Integer.parseInt(this.getLiterals());
        int y = Integer.parseInt(that.getLiterals());
        int result = x - y;
        return new IntegerName(result);
    }

    @Override
    public NumberName mul(NumberName that) {
        int x = Integer.parseInt(this.getLiterals());
        int y = Integer.parseInt(that.getLiterals());
        int result = x * y;
        return new IntegerName(result);
    }

    @Override
    public NumberName div(NumberName that) {
        int x = Integer.parseInt(this.getLiterals());
        int y = Integer.parseInt(that.getLiterals());
        int result = x / y;
        return new IntegerName(result);
    }

    @Override
    public NumberName mod(NumberName that) {
        int x = Integer.parseInt(this.getLiterals());
        int y = Integer.parseInt(that.getLiterals());
        int result = x % y;
        return new IntegerName(result);
    }
}
