package com.example.kwms.inbound.feature;

import com.example.kwms.inbound.domain.Inbound;
import com.example.kwms.inbound.domain.InboundRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
public class UpdateInbound {
    private final InboundRepository inboundRepository;

    @Transactional
    @PatchMapping("/inbounds")
    public void request(@RequestBody @Valid final Request request) {
        final Inbound inbound = inboundRepository.getBy(request.inboundNo);

        inbound.update(
                request.warehouseNo,
                request.title,
                request.estimatedArrivalAt,
                request.orderRequestedAt,
                request.description);

    }

    public record Request(
            @NotNull(message = "창고 번호는 필수입니다.")
            Long warehouseNo,
            @NotNull(message = "입고 번호는 필수입니다.")
            Long inboundNo,
            @NotBlank(message = "입고 제목은 필수입니다.")
            String title,
            @NotNull(message = "입고 예정일은 필수입니다.")
            LocalDateTime estimatedArrivalAt,
            @NotNull(message = "주문 요청일은 필수입니다.")
            LocalDateTime orderRequestedAt,
            String description) {

    }
}
