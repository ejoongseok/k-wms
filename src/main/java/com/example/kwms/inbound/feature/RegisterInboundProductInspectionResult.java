package com.example.kwms.inbound.feature;

import com.example.kwms.inbound.domain.Inbound;
import com.example.kwms.inbound.domain.InboundRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
public class RegisterInboundProductInspectionResult {

    private final InboundRepository inboundRepository;

    @Transactional
    @PostMapping("/inbounds/{inboundNo}/products/{inboundProductNo}/inspection-result")
    public void request(
            @PathVariable final Long inboundNo,
            @PathVariable final Long inboundProductNo,
            @RequestBody @Valid final Request request) {
        final Inbound inbound = inboundRepository.getBy(inboundNo);

        inbound.registerInspectionResult(
                inboundProductNo,
                request.inspectedAt,
                request.arrivedAt,
                request.inspectionComment,
                request.acceptableQuantity,
                request.rejectedQuantity);
    }

    public record Request(
            @NotNull(message = "검수 일시는 필수입니다.")
            LocalDateTime inspectedAt,
            @NotNull(message = "입고 일시는 필수입니다.")
            LocalDateTime arrivedAt,
            @NotBlank(message = "검수 코멘트는 필수입니다.")
            String inspectionComment,
            @NotNull(message = "정상 수량은 필수입니다.")
            @Min(value = 0, message = "정상 수량은 0개 이상이어야 합니다.")
            Long acceptableQuantity,
            @NotNull(message = "불량 수량은 필수입니다.")
            @Min(value = 0, message = "불량 수량은 0개 이상이어야 합니다.")
            Long rejectedQuantity) {
    }
}
