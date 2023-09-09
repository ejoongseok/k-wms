package com.example.kwms.inbound.feature;

import com.example.kwms.inbound.domain.Inbound;
import com.example.kwms.inbound.domain.InboundProduct;
import com.example.kwms.inbound.domain.InboundRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 최초 입고 외에 추가 입고를 요청하는 기능
 */
@RestController
@RequiredArgsConstructor
public class AddInboundProduct {
    private final InboundRepository inboundRepository;

    @Transactional
    @PostMapping("/inbounds/{inboundNo}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@PathVariable final Long inboundNo,
                        @RequestBody @Valid final Request request) {
        final Inbound inbound = inboundRepository.getBy(inboundNo);
        final InboundProduct inboundProduct = request.toDomain();

        inbound.addProduct(inboundProduct);
    }

    public record Request(
            @NotNull(message = "상품 번호는 필수입니다.")
            Long productNo,
            @NotNull(message = "상품 입고 요청 수량은 필수입니다.")
            @Min(value = 1, message = "상품 입고 요청 수량은 1개 이상이어야 합니다.")
            Long requestQuantity,
            @NotNull(message = "상품 입고 요청 단가는 필수입니다.")
            @Min(value = 0, message = "상품 입고 요청 단가는 0원 이상이어야 합니다.")
            Long unitPrice,
            String description) {
        InboundProduct toDomain() {
            return new InboundProduct(
                    productNo,
                    requestQuantity,
                    unitPrice,
                    description
            );
        }

        public record Product(
                @NotNull(message = "상품 번호는 필수입니다.")
                Long productNo,
                @NotNull(message = "상품 입고 요청 수량은 필수입니다.")
                @Min(value = 1, message = "상품 입고 요청 수량은 1개 이상이어야 합니다.")
                Long requestQuantity,
                @NotNull(message = "상품 입고 요청 단가는 필수입니다.")
                @Min(value = 0, message = "상품 입고 요청 단가는 0원 이상이어야 합니다.")
                Long unitPrice,
                String description
        ) {
        }
    }
}
