package com.dubrovnyi.bohdan.services;

import com.dubrovnyi.bohdan.db.models.SLOCModel;

import java.util.List;

public interface SLOCService {
    public List<SLOCModel> getAllModels();

    public SLOCModel getSLOCModelById(int id);

    public void addNew(SLOCModel slocModel);

    public void deleteModel(int id);
}

