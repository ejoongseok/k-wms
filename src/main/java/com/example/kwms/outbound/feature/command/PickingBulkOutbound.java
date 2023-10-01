package com.example.kwms.outbound.feature.command;

import com.example.kwms.location.domain.InventoryRepository;
import com.example.kwms.outbound.domain.BulkOutbound;
import com.example.kwms.outbound.domain.BulkOutboundRepository;
import com.example.kwms.outbound.domain.BulkPicker;
import com.example.kwms.outbound.domain.DecreaseTarget;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PickingBulkOutbound {
    private final BulkOutboundRepository bulkOutboundRepository;
    private final InventoryRepository inventoryRepository;
    private final BulkPicker bulkPicker = new BulkPicker();

    @PostMapping("/outbounds/bulk/{bulkOutboundNo}/picking")
    @Transactional
    public void request(
            @PathVariable final Long bulkOutboundNo,
            @RequestBody @Valid final Request request) {
        final BulkOutbound bulkOutbound = bulkOutboundRepository.getBy(bulkOutboundNo);
        final List<DecreaseTarget> targets = mapToTargets(request.pickedList);

        bulkPicker.picking(bulkOutbound, targets);
    }

    private List<DecreaseTarget> mapToTargets(final List<Request.Picked> pickedList) {
        return pickedList.stream()
                .map(p -> new DecreaseTarget(inventoryRepository.getBy(p.locationBarcode, p.lpnBarcode), p.quantity))
                .collect(Collectors.toList());
    }

    public record Request(
            @NotEmpty(message = "피킹 정보는 필수입니다.")
            List<Request.Picked> pickedList) {
        public record Picked(
                @NotBlank(message = "로케이션 바코드는 필수입니다.")
                String locationBarcode,
                @NotBlank(message = "LPN 바코드는 필수입니다.")
                String lpnBarcode,
                @NotNull(message = "피킹 수량은 필수입니다.")
                @Min(value = 1, message = "피킹 수량은 1개 이상이어야 합니다.")
                Long quantity) {
        }
    }
}
