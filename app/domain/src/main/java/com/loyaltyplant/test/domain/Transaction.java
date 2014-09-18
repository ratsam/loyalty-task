package com.loyaltyplant.test.domain;

import com.loyaltyplant.test.domain.operation.AbstractOperation;
import org.joda.time.DateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

/**
 * Transaction aggregates Orders that must be performed atomically.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
@Entity
@Table(name = "loyalty_transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private DateTime time;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Collection<Order<? extends AbstractOperation>> orders;

    @Column
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public Collection<Order<? extends AbstractOperation>> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order<? extends AbstractOperation>> orders) {
        this.orders = orders;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
