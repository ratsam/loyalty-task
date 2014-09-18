package com.loyaltyplant.test.repository;

import com.loyaltyplant.test.domain.operation.AbstractOperation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@RepositoryDefinition(domainClass = AbstractOperation.class, idClass = Integer.class)
public interface OperationRepository {

    @Modifying
    <T extends AbstractOperation> T save(T operation);

    AbstractOperation findById(Integer id);
}
