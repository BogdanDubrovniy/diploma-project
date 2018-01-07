package com.dubrovnyi.bohdan.services;

import com.dubrovnyi.bohdan.db.models.ResearchModel;

import java.util.List;

public interface ResearchService {
    public List<ResearchModel> getAllModels();

    public ResearchModel getResearchModelById(int id);

    public void addNew(ResearchModel researchModel);

    public void deleteModel(int id);
}

