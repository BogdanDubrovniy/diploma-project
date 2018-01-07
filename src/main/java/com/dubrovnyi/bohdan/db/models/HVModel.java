package com.dubrovnyi.bohdan.db.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Bohdan on 09.09.2017.
 */
@Entity
@Table(name = "hv")
public class HVModel implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "hv_value", nullable = false)
    private double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HVModel hvModel = (HVModel) o;

        return id == hvModel.id && value == hvModel.value;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "HVModel{"
                + "id=" + id
                + ", value=" + value
                + '}';
    }
}
