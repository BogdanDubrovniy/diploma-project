package com.dubrovnyi.bohdan.db.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "research")
public class ResearchModel implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "r_time")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Temporal(TemporalType.DATE)
    private Date researchDate;

    @Column(name = "script_name",
            length = 125, nullable = false)
    private String fileName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @JsonIgnore
    private SLOCModel slocModel;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @JsonIgnore
    private MIModel miModel;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @JsonIgnore
    private HVModel hvModel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getResearchDate() {
        return researchDate;
    }

    public void setResearchDate(Date researchDate) {
        this.researchDate = researchDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public SLOCModel getSlocModel() {
        return slocModel;
    }

    public void setSlocModel(SLOCModel slocModel) {
        this.slocModel = slocModel;
    }

    public MIModel getMiModel() {
        return miModel;
    }

    public void setMiModel(MIModel miModel) {
        this.miModel = miModel;
    }

    public HVModel getHvModel() {
        return hvModel;
    }

    public void setHvModel(HVModel hvModel) {
        this.hvModel = hvModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResearchModel that = (ResearchModel) o;

        return id == that.id && researchDate.equals(that.researchDate)
                && fileName.equals(that.fileName)
                && slocModel.equals(that.slocModel)
                && miModel.equals(that.miModel)
                && hvModel.equals(that.hvModel);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + researchDate.hashCode();
        result = 31 * result + fileName.hashCode();
        result = 31 * result + slocModel.hashCode();
        result = 31 * result + miModel.hashCode();
        result = 31 * result + hvModel.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ResearchModel{"
                + "id=" + id
                + ", researchDate=" + researchDate
                + ", fileName='" + fileName + '\''
                + ", slocModel=" + slocModel
                + ", miModel=" + miModel
                + ", hvModel=" + hvModel
                + '}';
    }
}
