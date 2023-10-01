package com.example.kwms.outbound.feature.command;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.location.domain.WarehouseRepository;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class TransferOutbound {
    private final OutboundRepository outboundRepository;
    private final WarehouseRepository warehouseRepository;

    @PostMapping("/outbounds/{outboundNo}/transfer-warehouse/{toWarehouseNo}")
    @Transactional
    public void request(
            @PathVariable final Long outboundNo,
            @PathVariable final Long toWarehouseNo) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);
        warehouseRepository.findById(toWarehouseNo)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 창고입니다. 창고 번호: %d".formatted(toWarehouseNo)));

        outbound.transferWarehouse(toWarehouseNo);
    }
}
