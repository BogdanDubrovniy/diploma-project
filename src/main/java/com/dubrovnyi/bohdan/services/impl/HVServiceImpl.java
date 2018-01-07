package com.dubrovnyi.bohdan.services.impl;

import com.dubrovnyi.bohdan.db.daos.HVJpaDao;
import com.dubrovnyi.bohdan.db.models.HVModel;
import com.dubrovnyi.bohdan.services.HVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("hvService")
public class HVServiceImpl implements HVService {

    @Autowired
    private HVJpaDao hvJpaDao;

    @Override
    @Transactional(readOnly = true)
    public List<HVModel> getAllModels() {
        return hvJpaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public HVModel getHVModelById(int id) {
        return hvJpaDao.getById(id);
    }

    @Override
    @Transactional
    public void addNew(HVModel hvModel) {
        hvJpaDao.save(hvModel);
    }

    @Override
    @Transactional
    public void deleteModel(int id) {
        hvJpaDao.delete(id);
    }
}
