package com.dubrovnyi.bohdan.managed.beans;

import com.dubrovnyi.bohdan.db.models.ResearchModel;
import com.dubrovnyi.bohdan.services.HVService;
import com.dubrovnyi.bohdan.services.MIService;
import com.dubrovnyi.bohdan.services.ResearchService;
import com.dubrovnyi.bohdan.services.SLOCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Bohdan on 09.09.2017.
 */
@ManagedBean(name = "diplomaBean")
@SessionScoped
@Component
public class DiplomaBean implements Serializable {

    @Autowired
    private HVService hvService;

    @Autowired
    private MIService miService;

    @Autowired
    private SLOCService slocService;

    @Autowired
    private ResearchService researchService;

    private List<ResearchModel> researchModelList;

    @PostConstruct
    public void init() {
        initSpringDependenciesIfNeccessary();
        //researchModelList = researchService.getAllModels();

    }

    //setters and getters:
    @Autowired
    public void setHvService(HVService hvService) {
        this.hvService = hvService;
    }

    @Autowired
    public void setIcService(MIService miService) {
        this.miService = miService;
    }

    @Autowired
    public void setSlocService(SLOCService slocService) {
        this.slocService = slocService;
    }

    @Autowired
    public void setResearchService(ResearchService researchService) {
        this.researchService = researchService;
    }

    //gets research list:
    public List<ResearchModel> getResearchModelList() {
        return researchService.getAllModels();
    }

    public HVService getHvService() {
        return hvService;
    }

    public MIService getMiService() {
        return miService;
    }

    public SLOCService getSlocService() {
        return slocService;
    }

    public ResearchService getResearchService() {
        return researchService;
    }

    public void setResearchModelList(List<ResearchModel> researchModelList) {
        this.researchModelList = researchModelList;
    }

    /**
     * If you use Spring as Model and Controller, and JSF like View,
     * Spring Beans could do not been injected
     */
    private void initSpringDependenciesIfNeccessary() {
        if (hvService == null
                || miService == null
                || slocService == null
                || researchService == null) {
            SpringBeanAutowiringSupport
                    .processInjectionBasedOnCurrentContext(this);
        }
    }
}
