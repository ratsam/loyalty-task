package com.loyaltyplant.test.domain;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Collection;

/**
 * Transaction aggregates Orders that must be performed atomically.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private DateTime time;
    private Collection<Order> orders;
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

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
