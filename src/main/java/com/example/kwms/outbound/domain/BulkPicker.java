package com.example.kwms.outbound.domain;

import com.example.kwms.location.domain.Inventory;

import java.util.List;

public class BulkPicker {

    public void picking(
            final BulkOutbound bulkOutbound,
            final List<DecreaseTarget> targets) {
        bulkOutbound.pickedOptimalFirstOutbound(targets);
        decreaseInventory(targets);
    }

    private void decreaseInventory(final List<DecreaseTarget> targets) {
        for (final DecreaseTarget target : targets) {
            final Inventory inventory = target.getTarget();
            final Long quantity = target.getQuantity();
            inventory.decreaseQuantity(quantity);
        }
    }
}