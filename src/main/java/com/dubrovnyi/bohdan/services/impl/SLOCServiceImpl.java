package com.dubrovnyi.bohdan.services.impl;

import com.dubrovnyi.bohdan.db.daos.SLOCJpaDao;
import com.dubrovnyi.bohdan.db.models.SLOCModel;
import com.dubrovnyi.bohdan.services.SLOCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("slocService")
public class SLOCServiceImpl implements SLOCService {

    @Autowired
    private SLOCJpaDao slocJpaDao;

    @Override
    @Transactional(readOnly = true)
    public List<SLOCModel> getAllModels() {
        return slocJpaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public SLOCModel getSLOCModelById(int id) {
        return slocJpaDao.getById(id);
    }

    @Override
    @Transactional
    public void addNew(SLOCModel slocModel) {
        slocJpaDao.save(slocModel);
    }

    @Override
    @Transactional
    public void deleteModel(int id) {
        slocJpaDao.delete(id);
    }
}
