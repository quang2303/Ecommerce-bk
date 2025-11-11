package com.ecommerce.be.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.be.Entity.Option;
import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Integer> {
    List<Option> findByParentOptionOptionId(Integer parentOptionId);

    @Query(value = """
            SELECT o
            FROM Option o
            WHERE o.parentOption.optionId = (
                SELECT po.optionId
                FROM Option po
                WHERE po.optionName = ?1
            )
            """)
    List<Option> findChildOptionsByOptionName(String optionName);
}