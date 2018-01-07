package com.dubrovnyi.bohdan.metric.handlers.implementation;

import com.dubrovnyi.bohdan.db.models.HVModel;
import com.dubrovnyi.bohdan.db.models.MIModel;
import com.dubrovnyi.bohdan.db.models.SLOCModel;
import com.dubrovnyi.bohdan.metric.handlers.MIHandler;

public class MIHandlerImlementation implements MIHandler {

    private int ccValue;
    private HVModel hvModel;
    private SLOCModel slocModel;

    public MIHandlerImlementation(int ccValue,
                                  HVModel hvModel, SLOCModel slocModel) {
        this.ccValue = ccValue;
        this.hvModel = hvModel;
        this.slocModel = slocModel;
    }

    @Override
    public void showResult() {

    }

    @Override
    public MIModel getResult() {
        MIModel model = new MIModel();

        double result = 171 - 5.2
                * Math.log(hvModel.getValue())
                - 0.23 * ccValue - 16.2
                * Math.log(slocModel.getLoc())
                + 50 * Math.sin(Math.pow((2.4 * slocModel.getCom()), 0.5));

        model.setValue(result);
        return model;
    }
}
