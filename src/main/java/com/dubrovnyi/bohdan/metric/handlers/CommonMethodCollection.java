package com.dubrovnyi.bohdan.metric.handlers;


import com.dubrovnyi.bohdan.exceptions.WrongNumberValueException;

public class CommonMethodCollection {
    public static double log2(int number) throws WrongNumberValueException {
        if (number < 0) {
            throw new WrongNumberValueException();
        }

        return Math.log(number) / Math.log(2);
    }
}
