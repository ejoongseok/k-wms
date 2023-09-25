package com.example.kwms.outbound.feature.query;

import com.example.kwms.common.auth.LoginUserNo;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import com.example.kwms.outbound.domain.Picking;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@RestController
@RequiredArgsConstructor
public class AppStartPickings {
    private final OutboundRepository outboundRepository;

    @GetMapping("/start-pickings")
    @Transactional(readOnly = true)
    public List<Response> startPickingAppView(@LoginUserNo final Long userNo) {
        final List<Outbound> outbounds = outboundRepository.listByPickerNo(userNo);
        final Map<String, List<Picking>> locationPickingMap = outbounds.stream()
                .filter(o -> null != o.getPickingTote())
                .filter(o -> !o.isPicked())
                .filter(o -> o.hasPickings())
                .filter(o -> !o.isCanceled())
                .flatMap(outbound -> outbound.getPickings().stream())
                .collect(groupingBy(p -> p.getInventory().getLocationBarcode()));
        final List<Response> responses = new ArrayList<>();
        for (final Map.Entry<String, List<Picking>> pickingEntry : locationPickingMap.entrySet()) {
            final String locationBarcode = pickingEntry.getKey();
            final List<Picking> pickings = pickingEntry.getValue();
            final Map<Outbound, List<Picking>> pickingToteToPickingListMap = pickings.stream()
                    .collect(groupingBy(p -> p.getOutboundProduct().getOutbound()));
            final List<Response.PickingToteResponse> pickingToteResponses = new ArrayList<>();
            for (final Map.Entry<Outbound, List<Picking>> locationPickingPair : pickingToteToPickingListMap.entrySet()) {
                final Outbound outbound = locationPickingPair.getKey();
                final List<Response.PickingToteResponse.PickingResponse> pickingResponses = new ArrayList<>();
                for (final Picking picking : locationPickingPair.getValue()) {
                    final String lpnBarcode = picking.getInventory().getLpn().getLpnBarcode();
                    final Long pickedQuantity = picking.getPickedQuantity();
                    final Long allocatedQuantity = picking.getQuantityRequiredForPick();
                    final Long pickingNo = picking.getPickingNo();
                    if (pickedQuantity == allocatedQuantity) {
                        continue;
                    }
                    final Response.PickingToteResponse.PickingResponse pickingResponse = new Response.PickingToteResponse.PickingResponse(
                            lpnBarcode,
                            pickedQuantity,
                            allocatedQuantity,
                            pickingNo);
                    pickingResponses.add(pickingResponse);
                }
                if (pickingResponses.isEmpty()) {
                    continue;
                }
                final Response.PickingToteResponse pickingToteResponse = new Response.PickingToteResponse(
                        outbound.getOutboundNo(),
                        outbound.getPickingTote().getLocationBarcode(),
                        pickingResponses);
                pickingToteResponses.add(pickingToteResponse);
            }
            if (pickingToteResponses.isEmpty()) {
                continue;
            }
            final Response response = new Response(locationBarcode, pickingToteResponses);
            responses.add(response);
        }
        responses.sort(Comparator.comparing(Response::locationBarcode));
        return responses;
    }

    record Response(
            String locationBarcode,
            List<PickingToteResponse> pickingToteResponses
    ) {
        record PickingToteResponse(
                Long outboundNo,
                String pickingToteBarcode,
                List<PickingResponse> pickingResponses
        ) {
            record PickingResponse(
                    String lpnBarcode,
                    Long pickedQuantity,
                    Long allocatedQuantity,
                    Long pickingId
            ) {
            }
        }
    }

}
