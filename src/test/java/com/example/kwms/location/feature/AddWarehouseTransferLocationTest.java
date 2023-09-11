package com.example.kwms.location.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AddWarehouseTransferLocationTest {

    private AddWarehouseTransferLocation addWarehouseTransferLocation;

    @BeforeEach
    void setUp() {
        addWarehouseTransferLocation = new AddWarehouseTransferLocation();
    }

    @Test
    @DisplayName("재고이동에 사용할 로케이션 할당")
    void allocateWarehouseTransferLocation() {
        final Long warehouseTransferNo = 1L;
        final String locationBarcode = "TOTE-001";
        addWarehouseTransferLocation.request(warehouseTransferNo, locationBarcode);
    }

    private class AddWarehouseTransferLocation {
        public void request(final Long warehouseTransferNo, final String locationBarcode) {
            throw new UnsupportedOperationException("Unsupported request");
            //해당 창고의 로케이션인지 확인.
            // 로케이션이 집품중인게 있는지 확인.
            // 상태변경
            // 로케이션에 재고이동할 상품외에 상품이 존재하는지 확인
            // 로케이션에 재고이동할 상품이 있는지 확인.
        }
    }
}
