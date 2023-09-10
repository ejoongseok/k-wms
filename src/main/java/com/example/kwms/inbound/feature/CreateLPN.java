package com.example.kwms.inbound.feature;

import com.example.kwms.inbound.domain.Inbound;
import com.example.kwms.inbound.domain.InboundRepository;
import com.example.kwms.inbound.domain.LPNRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class CreateLPN {
    private final InboundRepository inboundRepository;
    private final LPNRepository lpnRepository;

    @PostMapping("/inbounds/{inboundNo}/products/{inboundProductNo}/lpns")
    @Transactional
    public void request(
            @PathVariable final Long inboundNo,
            @PathVariable final Long inboundProductNo,
            @RequestBody @Valid final Request request) {
        validate(request.lpnBarcode);
        final Inbound inbound = inboundRepository.getBy(inboundNo);
        inbound.assignLPN(inboundProductNo, request.lpnBarcode, request.expiringAt);
    }

    private void validate(final String lpnBarcode) {
        lpnRepository.findByLpnBarcode(lpnBarcode)
                .ifPresent(lpn -> {
                    throw new IllegalArgumentException("이미 존재하는 LPN 바코드입니다.");
                });
    }

    public record Request(
            @NotBlank(message = "LPN 바코드는 필수입니다.")
            String lpnBarcode,
            @NotNull(message = "유통기한은 필수입니다.")
            LocalDateTime expiringAt) {
    }
}
