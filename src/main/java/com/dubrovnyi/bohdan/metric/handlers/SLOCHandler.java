package com.dubrovnyi.bohdan.metric.handlers;

import com.dubrovnyi.bohdan.db.models.SLOCModel;

import java.io.IOException;

/**
 * Created by Bohdan on 28.05.2017.
 */
public interface SLOCHandler {

    public void showCodeInformation();

    public SLOCModel getResults() throws IOException;
}
