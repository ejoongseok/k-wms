package com.example.kwms.outbound.feature;

import com.example.kwms.outbound.domain.BulkOutbound;
import com.example.kwms.outbound.domain.BulkOutboundRepository;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CreateBulkOutbound {
    private final OutboundRepository outboundRepository;
    private final BulkOutboundRepository bulkOutboundRepository;

    @PostMapping("/outbounds/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        final List<Outbound> outbounds = outboundRepository.findAllById(request.outboundNos);
        final BulkOutbound bulkOutbound = new BulkOutbound(outbounds);

        bulkOutbound.validateAndAssign();

        bulkOutboundRepository.save(bulkOutbound);
    }

    public record Request(
            @NotEmpty(message = "출고 번호는 필수입니다.")
            List<Long> outboundNos) {
    }
}
