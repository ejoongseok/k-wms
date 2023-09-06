package com.example.kwms.inbound;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class CreateInboundTest {

    private CreateInbound createInbound;

    @BeforeEach
    void setUp() {
        createInbound = new CreateInbound();
    }

    @Test
    @DisplayName("입고 생성")
    void createInbound() {
        final CreateInbound.Request.Product product = new CreateInbound.Request.Product(
                1L,
                2_000L,
                15_000L,
                "블랙핑크 3집 앨범[]"
        );

        final CreateInbound.Request request = new CreateInbound.Request(
                "블랙핑크 앨범 입고",
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now(),
                "23년도 블랙핑크 신규 앨범 주문",
                List.of(product));


        createInbound.request(request);

        // inboundRepository.findById(1L).asn();
    }

    private class CreateInbound {
        public void request(final Request request) {
            throw new UnsupportedOperationException("Unsupported request");
        }

        public record Request(
                @NotBlank(message = "입고 제목은 필수입니다.")
                String title,
                @NotNull(message = "입고 예정일은 필수입니다.")
                LocalDateTime estimatedArrivalAt,
                @NotNull(message = "주문 요청일은 필수입니다.")
                LocalDateTime orderRequestedAt,
                String description,
                List<Product> inboundProducts) {
            public record Product(
                    @NotNull(message = "상품 번호는 필수입니다.")
                    Long productNo,
                    @NotNull(message = "상품 입고 요청 수량은 필수입니다.")
                    @Min(value = 1, message = "상품 입고 요청 수량은 1개 이상이어야 합니다.")
                    Long requestQuantity,
                    @NotNull(message = "상품 입고 요청 단가는 필수입니다.")
                    @Min(value = 0, message = "상품 입고 요청 단가는 0원 이상이어야 합니다.")
                    Long unitPrice,
                    String description) {
            }
        }
    }
}
