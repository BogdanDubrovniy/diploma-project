package com.dubrovnyi.bohdan.db.daos;

import com.dubrovnyi.bohdan.db.models.SLOCModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Bohdan on 09.09.2017.
 */
@Repository
public interface SLOCJpaDao extends JpaRepository<SLOCModel, Integer> {

    @Query("select sloc from SLOCModel sloc where sloc.id = :id")
    public SLOCModel getById(@Param("id") int id);
}
