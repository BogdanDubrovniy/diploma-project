package com.dubrovnyi.bohdan.managed.beans;

import com.dubrovnyi.bohdan.db.models.HVModel;
import com.dubrovnyi.bohdan.db.models.MIModel;
import com.dubrovnyi.bohdan.db.models.ResearchModel;
import com.dubrovnyi.bohdan.db.models.SLOCModel;
import com.dubrovnyi.bohdan.db.models.dto.ProblemMetricValues;
import com.dubrovnyi.bohdan.exceptions.WrongNumberValueException;
import com.dubrovnyi.bohdan.metric.handlers.HVHandler;
import com.dubrovnyi.bohdan.metric.handlers.MIHandler;
import com.dubrovnyi.bohdan.metric.handlers.SLOCHandler;
import com.dubrovnyi.bohdan.metric.handlers.implementation.HVHandlerImpl;
import com.dubrovnyi.bohdan.metric.handlers.implementation.MIHandlerImlementation;
import com.dubrovnyi.bohdan.metric.handlers.implementation.SLOCHandlerImplementation;
import org.primefaces.context.RequestContext;
import org.apache.catalina.core.ApplicationPart;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@ManagedBean
@SessionScoped
public class SessionBean {

    @ManagedProperty(value = "#{diplomaBean}")
    private DiplomaBean diplomaBean;

    private HVModel hvModel;
    private MIModel miModel;
    private SLOCModel slocModel;

    private ApplicationPart file;
    private ProblemMetricValues problemMetricValues;

    @PostConstruct
    public void init() {
        problemMetricValues = new ProblemMetricValues();
        // no operation
    }

    public DiplomaBean getDiplomaBean() {
        return diplomaBean;
    }

    public void setDiplomaBean(DiplomaBean diplomaBean) {
        this.diplomaBean = diplomaBean;
    }

    public HVModel getHvModel() {
        return hvModel;
    }

    public void setHvModel(HVModel hvModel) {
        this.hvModel = hvModel;
    }

    public MIModel getMiModel() {
        return miModel;
    }

    public void setMiModel(MIModel miModel) {
        this.miModel = miModel;
    }

    public SLOCModel getSlocModel() {
        return slocModel;
    }

    public void setSlocModel(SLOCModel slocModel) {
        this.slocModel = slocModel;
    }

    public ApplicationPart getFile() {
        return file;
    }

    public void setFile(ApplicationPart file) {
        this.file = file;
    }

    public ProblemMetricValues getProblemMetricValues() {
        return problemMetricValues;
    }

    public void setProblemMetricValues(ProblemMetricValues problemMetricValues) {
        this.problemMetricValues = problemMetricValues;
    }

    //other methods:
    public void openHVDialog() {
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();

        Integer id = Integer.valueOf(request.getParameter("res-id"));

        hvModel = diplomaBean.getHvService().getHVModelById(id);

        String pagePath = "/pages/metrics/holsted-volume-results.xhtml";
        RequestContext.getCurrentInstance().openDialog(pagePath);
    }

    public void openMIDialog() {
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();

        Integer id = Integer.valueOf(request.getParameter("res-id"));

        miModel = diplomaBean.getMiService().getMIModelById(id);

        String pagePath = "/pages/metrics/maintainability-index-results.xhtml";
        RequestContext.getCurrentInstance().openDialog(pagePath);
    }

    public void openSLOCDialog() {
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();

        Integer id = Integer.valueOf(request.getParameter("res-id"));

        slocModel = diplomaBean.getSlocService().getSLOCModelById(id);

        String pagePath = "/pages/metrics/sloc-results.xhtml";
        RequestContext.getCurrentInstance().openDialog(pagePath);
    }


    public void deleteResult() {
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();

        int researchId = Integer.parseInt(request
                .getParameter("hidden-field-operation-id"));

        deleteRecord(researchId);
    }

    public void addResult() throws IOException {

        SLOCHandler slocHandler = new SLOCHandlerImplementation(
                file.getInputStream());

        SLOCModel tempSlocModel = slocHandler.getResults();

        HVHandler hvHandler = new HVHandlerImpl(file.getInputStream());

        HVModel tempHvModel = hvHandler.getResult();
        System.out.println(tempHvModel);

        try {
            hvHandler.showResult();
        } catch (WrongNumberValueException e) {
            e.printStackTrace();
        }

        MIHandler miHandler = new MIHandlerImlementation(
                problemMetricValues.getCCValue(),
                tempHvModel, tempSlocModel);

        MIModel tempMiModel = miHandler.getResult();

        ResearchModel tempResearchModel = new ResearchModel();
        tempResearchModel.setFileName(file.getSubmittedFileName());
        tempResearchModel.setResearchDate(new Date());
        tempResearchModel.setHvModel(tempHvModel);
        tempResearchModel.setMiModel(tempMiModel);
        tempResearchModel.setSlocModel(tempSlocModel);

        //save record in DB:
        diplomaBean.getResearchService().addNew(tempResearchModel);

        //refresh all links and values to default:
        setDefaultValues();
    }

    private void setDefaultValues() {
        problemMetricValues = new ProblemMetricValues();
        file = null;
    }

    private void deleteRecord(int researchId) {
        diplomaBean.getResearchService().deleteModel(researchId);

        diplomaBean.getHvService().deleteModel(researchId);
        diplomaBean.getMiService().deleteMI(researchId);
        diplomaBean.getSlocService().deleteModel(researchId);
    }

}
