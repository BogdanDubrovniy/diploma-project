package com.dubrovnyi.bohdan.services;

import com.dubrovnyi.bohdan.db.models.HVModel;

import java.util.List;

/**
 * Created by Bohdan on 09.09.2017.
 */
public interface HVService {
    public List<HVModel> getAllModels();

    public HVModel getHVModelById(int id);

    public void addNew(HVModel hvModel);

    public void deleteModel(int id);
}

