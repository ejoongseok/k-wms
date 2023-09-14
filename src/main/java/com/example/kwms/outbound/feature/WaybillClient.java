package com.example.kwms.outbound.feature;

import com.example.kwms.outbound.domain.Outbound;
import org.springframework.stereotype.Component;

@Component
class WaybillClient {
    public WaybillResponse request(final Outbound outbound) {
        validate(outbound);
        return new WaybillResponse(
                "trackingNumber",
                "image"
        );
    }

    private void validate(final Outbound outbound) {
        if (!outbound.isPacked()) {
            // TODO 나중에 운송사에 따라서 조건을 분기할 수도 있음.(선발행)
            throw new IllegalStateException("포장 완료한 출고건만 운송장 발행이 가능합니다.");
        }
    }
}
