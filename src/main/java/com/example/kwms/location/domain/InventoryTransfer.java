package com.example.kwms.location.domain;

import com.example.kwms.inbound.domain.LPN;
import org.springframework.util.Assert;

public class InventoryTransfer {

    private static void validate(
            final Location currentLocation,
            final Location targetLocation,
            final LPN lpn,
            final Long quantity) {
        Assert.notNull(currentLocation, "현재 로케이션은 필수입니다.");
        Assert.notNull(targetLocation, "이동할 로케이션은 필수입니다.");
        Assert.notNull(lpn, "LPN은 필수입니다.");
        Assert.notNull(quantity, "이동할 수량은 필수입니다.");
    }

    public void execute(
            final Location currentLocation,
            final Location targetLocation,
            final LPN lpn,
            final Long quantity) {
        validate(currentLocation, targetLocation, lpn, quantity);
        if (0 >= quantity) {
            throw new IllegalArgumentException("이동할 수량은 0보다 커야 합니다.");
        }
        currentLocation.decreaseInventory(lpn, quantity);
        targetLocation.addManualInventory(lpn, quantity);
    }
}