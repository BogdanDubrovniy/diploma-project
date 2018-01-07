package com.dubrovnyi.bohdan.services;

import com.dubrovnyi.bohdan.db.models.MIModel;

import java.util.List;

public interface MIService {
    public List<MIModel> getAllModels();

    public MIModel getMIModelById(int id);

    public void addIC(MIModel icModel);

    public void deleteMI(int id);
}