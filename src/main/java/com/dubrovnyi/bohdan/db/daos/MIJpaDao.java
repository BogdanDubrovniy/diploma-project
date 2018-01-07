package com.dubrovnyi.bohdan.db.daos;

import com.dubrovnyi.bohdan.db.models.MIModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Bohdan on 09.09.2017.
 */
@Repository
public interface MIJpaDao extends JpaRepository<MIModel, Integer> {
    @Query("select model from MIModel model where model.id = :id")
    public MIModel getById(@Param("id") int id);
}
