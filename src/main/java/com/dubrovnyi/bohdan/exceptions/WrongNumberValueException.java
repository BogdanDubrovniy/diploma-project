package com.dubrovnyi.bohdan.exceptions;

/**
 * Created by user on 11.10.17.
 */
public class WrongNumberValueException extends Exception {
    public WrongNumberValueException() {
        super("Value is less than 0");
    }
}
