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
        }
    }
}
