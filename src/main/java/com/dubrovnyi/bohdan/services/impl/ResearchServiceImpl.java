package com.dubrovnyi.bohdan.services.impl;

import com.dubrovnyi.bohdan.db.daos.ResearchJpaDao;
import com.dubrovnyi.bohdan.db.models.ResearchModel;
import com.dubrovnyi.bohdan.services.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("researchService")
public class ResearchServiceImpl implements ResearchService {

    @Autowired
    private ResearchJpaDao researchJpaDao;

    @Override
    @Transactional(readOnly = true)
    public List<ResearchModel> getAllModels() {
        return researchJpaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ResearchModel getResearchModelById(int id) {
        return researchJpaDao.getById(id);
    }

    @Override
    @Transactional
    public void addNew(ResearchModel researchModel) {
        researchJpaDao.save(researchModel);
    }

    @Override
    @Transactional
    public void deleteModel(int id) {
        researchJpaDao.delete(id);
    }
}
