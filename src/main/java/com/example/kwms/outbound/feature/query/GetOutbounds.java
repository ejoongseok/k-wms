package com.example.kwms.outbound.feature.query;

import com.example.kwms.location.domain.WarehouseRepository;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetOutbounds {
    private final OutboundRepository outboundRepository;
    private final WarehouseRepository warehouseRepository;

    @GetMapping("/outbounds")
    @Transactional(readOnly = true)
    public List<Response> findAll() {
        final List<Outbound> outbounds = outboundRepository.findAll();
        final List<Response> responses = new ArrayList<>();
        for (final Outbound outbound : outbounds) {
            final Long outboundNo = outbound.getOutboundNo();
            final Long orderNo = outbound.getOrderNo();
            final LocalDate desiredDeliveryAt = outbound.getDesiredDeliveryAt();
            final String warehouseName = warehouseRepository.getBy(outbound.getWarehouseNo()).getName();
            final String trackingNumber = StringUtils.hasText(outbound.getTrackingNumber()) ? outbound.getTrackingNumber() : "송장 발행전";
            final String priorityType = outbound.getIsPriorityDelivery() ? "긴급" : "일반";
            String status = "";
            if (outbound.isReady()) {
                status = "대기";
            } else if (!outbound.isPicked() && !outbound.getPickings().isEmpty() && !outbound.isCanceled() && !outbound.isInspected() && !outbound.isPacked()) {
                status = "집품 중";
            } else if (outbound.isPicked() && !outbound.isCanceled() && !outbound.isInspected() && !outbound.isPacked()) {
                status = "집품 완료";
            } else if (outbound.isCanceled()) {
                status = "출고 중지";
            } else if (!outbound.isCanceled() && outbound.isInspected() && !outbound.isPacked()) {
                status = "검수 완료";
            } else if (!outbound.isCanceled() && outbound.isPacked() && null == outbound.getTrackingNumber()) {
                status = "포장 완료(송장 발행전)";
            } else if (!outbound.isCanceled() && outbound.isPacked() && null != outbound.getTrackingNumber()) {
                status = "포장 완료(송장 발행 완료)";
            }
            final int skuQuantity = outbound.getOutboundProducts().size();
            final Long orderQuantity = outbound.getOutboundProducts().stream()
                    .mapToLong(outboundProduct -> outboundProduct.getQuantity())
                    .sum();
            final Response response = new Response(
                    outboundNo,
                    orderNo,
                    desiredDeliveryAt,
                    warehouseName,
                    trackingNumber,
                    priorityType,
                    status,
                    (long) skuQuantity,
                    orderQuantity
            );
            responses.add(response);
        }
        return responses;
    }

    private record Response(
            Long outboundNo,
            Long orderNo,
            LocalDate desiredDeliveryAt,
            String warehouseName,
            String trackingNumber,
            String priorityType,
            String status,
            Long skuQuantity,
            Long orderQuantity) {

    }
}
