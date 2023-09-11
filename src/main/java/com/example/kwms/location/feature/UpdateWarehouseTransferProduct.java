package com.example.kwms.location.feature;

import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateWarehouseTransferProduct {
    private final WarehouseTransferRepository warehouseTransferRepository;

    @PatchMapping("/warehouse-transfers/{warehouseTransferNo}/transfer-products/{warehouseTransferProductNo}")
    @Transactional
    public void request(
            @PathVariable final Long warehouseTransferNo,
            @PathVariable final Long warehouseTransferProductNo,
            @RequestBody @Valid final Request request) {
        final WarehouseTransfer warehouseTransfer = warehouseTransferRepository.getBy(warehouseTransferNo);

        warehouseTransfer.updateProduct(warehouseTransferProductNo, request.productNo, request.quantity);
    }

    public record Request(
            @NotNull(message = "상품 번호는 필수입니다.")
            Long productNo,
            @NotNull(message = "수량은 필수입니다.")
            @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
            Long quantity
    ) {
    }
}
