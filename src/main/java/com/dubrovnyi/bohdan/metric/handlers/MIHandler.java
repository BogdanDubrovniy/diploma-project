package com.dubrovnyi.bohdan.metric.handlers;

import com.dubrovnyi.bohdan.db.models.MIModel;

/**
 * Created by Bohdan on 10.09.2017.
 */
public interface MIHandler {
    public void showResult();

    public MIModel getResult();
}

