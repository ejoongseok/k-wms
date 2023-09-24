package com.example.kwms.outbound.domain;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.location.domain.Inventory;
import org.springframework.util.Assert;

import java.util.List;

public class PickingAllocator {

    public void allocatePicking(final Outbound outbound, final List<Inventory> inventories) {
        validate(outbound, inventories);
        outbound.allocatePicking(inventories);
        deductAllocatedInventory(outbound.getPickings(), inventories);
    }

    private void validate(final Outbound outbound, final List<Inventory> inventories) {
        Assert.notNull(outbound, "출고 정보가 없습니다.");
        Assert.notEmpty(inventories, "재고 정보가 없습니다.");
        if (outbound.isCanceled()) {
            throw new IllegalArgumentException("취소된 출고에는 집품을 할당할 수 없습니다.");
        }
        if (outbound.hasPickings()) {
            throw new IllegalArgumentException("이미 피킹이 할당된 출고입니다.");
        }
        if (null == outbound.getPickerNo()) {
            throw new IllegalArgumentException("집품 작업자가 지정되지 않았습니다.");
        }
    }

    private void deductAllocatedInventory(
            final List<Picking> pickings,
            final List<Inventory> inventories) {
        for (final Picking picking : pickings) {
            final Inventory inventory = getBy(inventories, picking.getInventory());
            inventory.decreaseInventory(picking.getQuantityRequiredForPick());
        }
    }

    private Inventory getBy(final List<Inventory> inventories, final Inventory target) {
        return inventories.stream()
                .filter(inventory -> inventory.equals(target))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "재고 정보가 없습니다. 로케이션 바코드:%d , 상품 번호: %d"
                                .formatted(target.getLocationBarcode(), target.getProductNo())));
    }
}