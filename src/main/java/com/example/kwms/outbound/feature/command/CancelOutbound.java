package com.example.kwms.outbound.feature.command;

import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CancelOutbound {
    private final OutboundRepository outboundRepository;

    @PostMapping("/outbounds/{outboundNo}/cancel")
    @Transactional
    public void request(
            @PathVariable final Long outboundNo,
            @RequestBody @Valid final Request request) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);

        outbound.canceled(request.cancelReason);
        //TOOD 집품이 할당되어있는 상태에서 집품하지 않은 수량만큼은 할당된 재고를 롤백한다.(Optional)
    }

    public record Request(
            @NotBlank(message = "취소 사유는 필수입니다.")
            String cancelReason
    ) {
    }
}
