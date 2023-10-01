package com.example.kwms.outbound.feature.command;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.location.domain.Inventory;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.Picking;
import com.example.kwms.outbound.domain.PickingRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppScanToPickManual {
    private final PickingRepository pickingRepository;

    @PostMapping("/app/outbounds/scan-to-pick-manual")
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        final Picking picking = pickingRepository.findById(request.pickingNo)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 집품입니다."));
        final Inventory inventory = picking.getInventory();
        final Outbound outbound = picking.getOutboundProduct().getOutbound();

        outbound.appScanToPickManual(inventory, request.quantity);
    }

    public record Request(
            @NotNull(message = "집품 번호는 필수입니다.")
            Long pickingNo,
            @NotNull(message = "집품할 수량은 필수입니다.")
            @Min(value = 1, message = "집품할 수량은 1개 이상이어야 합니다.")
            Long quantity) {
    }
}
