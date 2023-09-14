package com.example.kwms.outbound.feature;

import com.example.kwms.location.domain.LocationFixture;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundProduct;
import com.example.kwms.outbound.domain.OutboundRepository;
import jakarta.validation.constraints.NotEmpty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.kwms.outbound.domain.OutboundFixture.anOutbound;

public class CreateBulkOutboundTest {

    private CreateBulkOutbound createBulkOutbound;

    @BeforeEach
    void setUp() {
        createBulkOutbound = new CreateBulkOutbound();
    }

    @Test
    @DisplayName("대량의 출고를 생성한다.")
    void createBulkOutbound() {
        final CreateBulkOutbound.Request request = new CreateBulkOutbound.Request(
                List.of(1L, 2L)
        );
        createBulkOutbound.request(request);
    }

    @Test
    void name() {
        final List<Outbound> build = List.of(anOutbound().build(), anOutbound().pickingTote(LocationFixture.aLocation()).build());
        createBulkOutbound.validate(build);
    }

    private class CreateBulkOutbound {
        private OutboundRepository outboundRepository;

        public void request(final Request request) {
            final List<Outbound> outbounds = outboundRepository.findAllById(request.outboundNos);
            validate(outbounds);

        }

        public void validate(final List<Outbound> outbounds) {
            Assert.notEmpty(outbounds, "출고가 존재하지 않습니다.");
            final Set<String> uniqueOutboundProducts = outbounds.stream()
                    .map(this::formatOutboundProducts)
                    .collect(Collectors.toSet());

            if (1 < uniqueOutboundProducts.size()) {
                throw new IllegalStateException("동일한 출고건이 아닙니다.");
            }

            // 출고 목록이 전부 출고 대기 중인 목록인지 확인
            final Set<Outbound> outboundsToBeShipped = outbounds.stream()
                    .filter(outbound -> !outbound.isReady())
                    .collect(Collectors.toUnmodifiableSet());

            if (!outboundsToBeShipped.isEmpty()) {
                throw new IllegalStateException("출고 대기 중인 출고건이 아닙니다.");
            }
        }

        private String formatOutboundProducts(final Outbound outbound) {
            final List<OutboundProduct> sortedOutboundProducts = outbound.getOutboundProducts()
                    .stream()
                    .sorted(Comparator.comparing(OutboundProduct::getProductNo))
                    .toList();
            return sortedOutboundProducts.stream()
                    .map(outboundProduct -> "%d:%d".formatted(outboundProduct.getProductNo(), outboundProduct.getQuantity()))
                    .collect(Collectors.joining("/"));
        }

        public record Request(
                @NotEmpty(message = "출고 번호는 필수입니다.")
                List<Long> outboundNos) {
        }
    }
}
