package com.example.kwms.outbound.feature.query;

import com.example.kwms.common.auth.LoginUserNo;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppPickingForOutbounds {
    private final OutboundRepository outboundRepository;

    @GetMapping("/app/outbounds/picking-for-outbounds")
    public List<Response> request(@LoginUserNo final Long userNo) {
        final List<Outbound> outbounds = outboundRepository.listByPickerNo(userNo);
        final List<Response> responses = new ArrayList<>();
        for (final Outbound outbound : outbounds) {
            final Response response = new Response(outbound.getOutboundNo());
            responses.add(response);
        }
        return responses;
    }

    record Response(
            Long outboundNo) {
    }

}
