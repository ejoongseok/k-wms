package com.example.kwms.location.feature;

import com.example.kwms.location.domain.InventoryRepository;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CreateWarehouseTransferTest {

    private CreateWarehouseTransfer createWarehouseTransfer;

    @BeforeEach
    void setUp() {
        createWarehouseTransfer = new CreateWarehouseTransfer();
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

    private class CreateWarehouseTransfer {
        private InventoryRepository inventoryRepository;
        public void request(final Request request) {

        }

        public record Request(
                @NotNull(message = "출발 창고 번호는 필수입니다.")
                Long fromWarehouseNo,
                @NotNull(message = "도착 창고 번호는 필수입니다.")
                Long toWarehouseNo,
                @NotBlank(message = "바코드는 필수입니다.")
                String barcode,
                @NotEmpty(message = "상품은 필수입니다.")
                List<Product> products) {
            public record Product(
                    @NotNull(message = "상품 번호는 필수입니다.")
                    Long productNo,
                    @NotNull(message = "수량은 필수입니다.")
                    @Min(value = 1, message = "수량은 1보다 커야 합니다.")
                    Long quantity) {
            }
        }
    }
}
