package com.dubrovnyi.bohdan.db.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Bohdan on 09.09.2017.
 */
@Entity
@Table(name = "sloc")
public class SLOCModel implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "loc", nullable = false)
    private int loc;

    @Column(name = "com", nullable = false)
    private double com;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public double getCom() {
        return com;
    }

    public void setCom(double com) {
        this.com = com;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SLOCModel slocModel = (SLOCModel) o;

        return id == slocModel.id && loc == slocModel.loc
                && Double.compare(slocModel.com, com) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + loc;
        temp = Double.doubleToLongBits(com);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "SLOCModel{"
                + "id=" + id
                + ", loc=" + loc
                + ", com=" + com
                + '}';
    }
}
