package com.dubrovnyi.bohdan.metric.handlers;

import com.dubrovnyi.bohdan.db.models.HVModel;
import com.dubrovnyi.bohdan.exceptions.WrongNumberValueException;

import java.io.IOException;

/**
 * Created by Bohdan on 10.09.2017.
 */
public interface HVHandler {
    public void showResult() throws WrongNumberValueException;

    public HVModel getResult() throws IOException;
}
