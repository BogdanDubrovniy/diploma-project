package com.dubrovnyi.bohdan.db.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Bohdan on 09.09.2017.
 */
@Entity
@Table(name = "mi")
public class MIModel implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mi_value", nullable = false)
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

        MIModel icModel = (MIModel) o;

        return id == icModel.id && value == icModel.value;
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
        return "MIModel{"
                + "id=" + id
                + ", value=" + value
                + '}';
    }
}
