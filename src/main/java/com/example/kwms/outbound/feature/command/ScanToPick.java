package com.example.kwms.outbound.feature.command;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.InventoryRepository;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScanToPick {
    private final OutboundRepository outboundRepository;
    private final InventoryRepository inventoryRepository;

    @PostMapping("/outbounds/scan-to-pick")
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        final Outbound outbound = outboundRepository.getBy(request.pickingToteBarcode);
        final Inventory inventory = inventoryRepository.getBy(request.barcodeForPickingLocation, request.lpnBarcode);

        outbound.scanToPick(inventory);
    }

    public record Request(
            @NotBlank(message = "피킹 토트 바코드는 필수입니다.")
            String pickingToteBarcode,
            @NotBlank(message = "LPN 바코드는 필수입니다.")
            String lpnBarcode,
            @NotBlank(message = "집품할 로케이션 바코드는 필수입니다.")
            String barcodeForPickingLocation) {
    }
}
