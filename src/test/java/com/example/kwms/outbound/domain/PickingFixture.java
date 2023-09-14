package com.example.kwms.outbound.domain;

import com.example.kwms.location.domain.InventoryFixture;

import static com.example.kwms.location.domain.InventoryFixture.anInventory;

public class PickingFixture {
    private Long pickingNo = 1L;
    private InventoryFixture inventory = anInventory();
    private Long quantityRequiredForPick = 1L;
    private Long pickedQuantity = 0L;

    public static PickingFixture aPicking() {
        return new PickingFixture();
    }

    public PickingFixture pickingNo(final Long pickingNo) {
        this.pickingNo = pickingNo;
        return this;
    }

    public PickingFixture inventory(final InventoryFixture inventory) {
        this.inventory = inventory;
        return this;
    }

    public PickingFixture quantityRequiredForPick(final Long quantityRequiredForPick) {
        this.quantityRequiredForPick = quantityRequiredForPick;
        return this;
    }

    public PickingFixture pickedQuantity(final Long pickedQuantity) {
        this.pickedQuantity = pickedQuantity;
        return this;
    }

    public Picking build() {
        return new Picking(
                pickingNo,
                quantityRequiredForPick,
                pickedQuantity,
                inventory.build()
        );
    }
}
