package com.example.kwms.outbound.feature.query;

import com.example.kwms.common.auth.LoginUserNo;
import com.example.kwms.location.domain.Location;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundProduct;
import com.example.kwms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppGetPickingList {
    private final OutboundRepository outboundRepository;

    @GetMapping("/app/get-picking-list")
    @Transactional(readOnly = true)
    public List<Response> request(@LoginUserNo final Long userNo) {
        final List<Outbound> outbounds = outboundRepository.listByPickerNo(userNo);
        if (outbounds.isEmpty()) {
            throw new RuntimeException("할당된 출고가 없습니다.");
        }

        final List<Response> responses = new ArrayList<>();
        for (final Outbound outbound : outbounds) {
            final Long outboundNo = outbound.getOutboundNo();
            final Location pickingTote = outbound.getPickingTote();
            final String locationBarcode = null == pickingTote ? null : pickingTote.getLocationBarcode();
            final Long orderNo = outbound.getOrderNo();
            final Long skuQuantity = (long) outbound.getOutboundProducts().size();
            final long pickingQuantity = outbound.getOutboundProducts().stream()
                    .mapToLong(OutboundProduct::getQuantity)
                    .sum();
            final String status;
            final boolean isPicked = outbound.isPicked();
            final boolean hasPickList = outbound.hasPickings();
            if (!hasPickList) {
                status = "피킹 할당 전";
            } else if (isPicked) {
                status = "피킹 완료";
            } else {
                status = "피킹 중";
            }
            final Response response = new Response(
                    outboundNo,
                    orderNo,
                    locationBarcode,
                    skuQuantity,
                    pickingQuantity,
                    status);
            responses.add(response);
        }
        return responses;
    }

    record Response(
            Long outboundNo,
            Long orderNo,
            String pickingToteBarcode,
            Long skuQuantity,
            Long pickingQuantity,
            String status) {
    }

}
