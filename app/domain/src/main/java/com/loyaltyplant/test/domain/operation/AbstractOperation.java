package com.loyaltyplant.test.domain.operation;

import com.loyaltyplant.test.domain.BalanceOperation;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Entity
@Table(name = "loyalty_operation")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "operation_type")
public abstract class AbstractOperation implements BalanceOperation {

    @Id
    @GeneratedValue
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Force subclasses to implement equals & hashCode.
    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
