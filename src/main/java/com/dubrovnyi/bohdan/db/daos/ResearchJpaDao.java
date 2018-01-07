package com.dubrovnyi.bohdan.db.daos;

import com.dubrovnyi.bohdan.db.models.ResearchModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Bohdan on 09.09.2017.
 */
@Repository
public interface ResearchJpaDao extends JpaRepository<ResearchModel, Integer> {
    @Query("select model from ResearchModel model where model.id = :id")
    public ResearchModel getById(@Param("id") int id);
}
