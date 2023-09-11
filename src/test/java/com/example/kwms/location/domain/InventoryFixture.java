package com.example.kwms.location.domain;

import com.example.kwms.inbound.domain.LPNFixture;

public class InventoryFixture {
    private Long inventoryNo = 1L;
    private Long quantity = 1L;
    private Long productNo = 1L;
    private Long warehouseNo = 1L;
    private final LPNFixture lpn = LPNFixture.aLPN();

    public static InventoryFixture anInventory() {
        return new InventoryFixture();
    }

    public InventoryFixture inventoryNo(final Long inventoryNo) {
        this.inventoryNo = inventoryNo;
        return this;
    }

    public InventoryFixture quantity(final Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public InventoryFixture productNo(final Long productNo) {
        this.productNo = productNo;
        return this;
    }

    public InventoryFixture warehouseNo(final Long warehouseNo) {
        this.warehouseNo = warehouseNo;
        return this;
    }

    public Inventory build() {
        return new Inventory(
                inventoryNo,
                quantity,
                productNo,
                warehouseNo,
                lpn.build()
        );
    }
}
