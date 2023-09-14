package com.example.kwms.outbound.domain;

import com.example.kwms.location.domain.Inventory;

public class DecreaseTarget {
    private final Inventory target;
    private final Long quantity;

    public DecreaseTarget(final Inventory target, final Long quantity) {
        this.target = target;
        this.quantity = quantity;
    }

    public Inventory getTarget() {
        return target;
    }

    public Long getQuantity() {
        return quantity;
    }
}
