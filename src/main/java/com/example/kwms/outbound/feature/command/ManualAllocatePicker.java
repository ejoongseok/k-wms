package com.example.kwms.outbound.feature.command;

import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManualAllocatePicker {
    private final OutboundRepository outboundRepository;

    @PostMapping("/outbounds/{outboundNo}/allocate-picker")
    @Transactional
    public void request(
            @PathVariable final Long outboundNo,
            @RequestBody @Valid final Request request) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);
        outbound.allocatePicker(request.pickerNo());
    }

    public record Request(
            @NotNull(message = "집품 작업자 번호는 필수입니다..")
            Long pickerNo
    ) {
    }
}
