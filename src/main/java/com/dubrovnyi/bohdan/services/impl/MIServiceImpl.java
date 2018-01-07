package com.dubrovnyi.bohdan.services.impl;

import com.dubrovnyi.bohdan.db.daos.MIJpaDao;
import com.dubrovnyi.bohdan.db.models.MIModel;
import com.dubrovnyi.bohdan.services.MIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("icService")
public class MIServiceImpl implements MIService {
    
    @Autowired
    private MIJpaDao miJpaDao;

    @Override
    @Transactional(readOnly = true)
    public List<MIModel> getAllModels() {
        return miJpaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public MIModel getMIModelById(int id) {
        return miJpaDao.getById(id);
    }

    @Override
    @Transactional
    public void addIC(MIModel icModel) {
        miJpaDao.save(icModel);
    }

    @Override
    @Transactional
    public void deleteMI(int id) {
        miJpaDao.delete(id);
    }
}