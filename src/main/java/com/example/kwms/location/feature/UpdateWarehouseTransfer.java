package com.example.kwms.location.feature;

import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateWarehouseTransfer {
    private final WarehouseTransferRepository warehouseTransferRepository;

    @Transactional
    @PatchMapping("/warehouse-transfers/{warehouseTransferNo}")
    public void request(
            @PathVariable final Long warehouseTransferNo,
            @RequestBody @Valid final Request request) {
        validate(request);
        final WarehouseTransfer warehouseTransfer = warehouseTransferRepository.getBy(warehouseTransferNo);

        warehouseTransfer.update(request.fromWarehouseNo, request.toWarehouseNo, request.barcode);
    }

    private void validate(final Request request) {
        warehouseTransferRepository.findBy(request.barcode)
                .ifPresent(w -> {
                    throw new IllegalArgumentException("이미 존재하는 바코드입니다. 바코드: %s".formatted(request.barcode));
                });
    }

    public record Request(
            @NotNull(message = "출발 창고 번호는 필수입니다.")
            Long fromWarehouseNo,
            @NotNull(message = "도착 창고 번호는 필수입니다.")
            Long toWarehouseNo,
            @NotBlank(message = "바코드는 필수입니다.")
            String barcode
    ) {
    }
}
