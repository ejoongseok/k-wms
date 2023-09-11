package com.example.kwms.location.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CreateWarehouseTransferTest extends ApiTest {

    @Autowired
    private CreateWarehouseTransfer createWarehouseTransfer;

    @BeforeEach
    void createWarehouseTransferSetUp() {
        Scenario.createWarehouse().request();
        Scenario.createWarehouse().request();
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 10L;
        Scenario.createLocation().locationBarcode(locationBarcode).request()
                .createInbound().request()
                .registerInboundProductInspectionResult().request()
                .createLPN().lpnBarcode(lpnBarcode).request();
        Scenario.addManualInventory()
                .locationBarcode(locationBarcode)
                .lpnBarcode(lpnBarcode)
                .quantity(quantity)
                .request();
    }

    @Test
    @DisplayName("창고간 재고 이동을 생성한다.")
    void createWarehouseTransfer() {
        final Long productNo = 1L;
        final Long quantity = 1L;
        final CreateWarehouseTransfer.Request.Product product = new CreateWarehouseTransfer.Request.Product(
                productNo,
                quantity
        );
        final List<CreateWarehouseTransfer.Request.Product> products = List.of(product);
        final Long fromWarehouseNo = 1L;
        final Long toWarehouseNo = 2L;
        final String barcode = "WT-001";
        final CreateWarehouseTransfer.Request request = new CreateWarehouseTransfer.Request(
                fromWarehouseNo,
                toWarehouseNo,
                barcode,
                products
        );

        createWarehouseTransfer.request(request);
    }

}
