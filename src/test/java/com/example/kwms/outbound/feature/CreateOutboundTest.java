package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateOutboundTest extends ApiTest {

    @Autowired
    private CreateOutbound createOutbound;

    @Autowired
    private OutboundRepository outboundRepository;


    @Test
    @DisplayName("출고를 생성한다.")
    void createOutbound() {
        final Long orderNo = 1L;
        final Long warehouseNo = 1L;
        final boolean isPriorityDelivery = false;
        final LocalDate desiredDeliveryAt = LocalDate.now();
        final CreateOutbound.Request request = new CreateOutbound.Request(
                warehouseNo,
                orderNo,
                isPriorityDelivery,
                desiredDeliveryAt
        );

        createOutbound.request(request);

        assertThat(outboundRepository.findAll()).hasSize(1);
    }

}
