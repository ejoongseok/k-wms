package com.example.kwms.outbound.feature;

import com.example.kwms.common.auth.LoginUserNo;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
class AllocatePicker {
    private final OutboundRepository outboundRepository;

    @PostMapping("/outbounds/allocate-picker")
    @Transactional
    public void request(@LoginUserNo final Long userNo) {
        // 추후 성능 이슈가 생기면 SQL으로 출고 목록 조회하는 로직 변경
        final List<Outbound> outbounds = outboundRepository.findAll();

        outbounds.stream()
                .filter(o -> !o.isPicked())
                .filter(o -> userNo.equals(o.getPickerNo()))
                .findFirst()
                .ifPresent(outbound -> {
                    throw new RuntimeException("이미 작업자에게 할당된 출고가 있습니다.");
                });

        final List<Outbound> filteredOutbounds = outbounds.stream()
                .filter(outbound -> outbound.isReady())
                .filter(outbound -> null == outbound.getPickerNo())
                .filter(outbound -> null == outbound.getBulkOutbound())
                .collect(Collectors.toList());

        final List<Outbound> sortedOutbounds = filteredOutbounds.stream()
                .sorted(Comparator.comparing(Outbound::getIsPriorityDelivery)
                        .thenComparing(Outbound::getDesiredDeliveryAt)
                        .thenComparing(Outbound::getOutboundNo))
                .collect(Collectors.toList());

//        final int maxAllocations = 6;
        final int maxAllocations = Math.min(sortedOutbounds.size(), 6);
        for (int i = 0; i < maxAllocations; i++) {
            final Outbound outbound = sortedOutbounds.get(i);
            outbound.allocatePicker(userNo);
        }

    }
}
