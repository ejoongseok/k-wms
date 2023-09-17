package com.example.kwms.inbound.feature.command;

import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import com.example.kwms.inbound.domain.Receive;
import com.example.kwms.inbound.domain.ReceiveProduct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AddReceive {

    private final PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    @PostMapping("/purchase-orders/{purchaseOrderNo}/receives")
    public void request(
            @PathVariable final Long purchaseOrderNo,
            @RequestBody @Valid final Request request) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);
        final Receive receive = request.toDomain();
        purchaseOrder.addReceive(receive);
    }

    public record Request(
            @NotBlank(message = "입고명은 필수입니다.")
            String name,
            @NotEmpty(message = "입고 상품은 필수입니다.")
            List<ReceiveRequest> receiveRequests) {
        Receive toDomain() {
            return new Receive(
                    name,
                    receiveRequests.stream()
                            .map(ReceiveRequest::toDomain)
                            .toList());
        }

        public record ReceiveRequest(
                @NotNull(message = "상품 번호는 필수입니다.")
                Long productNo,
                @NotBlank(message = "검수 코멘트는 필수입니다.")
                String description,
                @NotNull(message = "정상 수량은 필수입니다.")
                @Min(value = 0, message = "정상 수량은 0개 이상이어야 합니다.")
                Long acceptableQuantity,
                @NotNull(message = "불량 수량은 필수입니다.")
                @Min(value = 0, message = "불량 수량은 0개 이상이어야 합니다.")
                Long rejectedQuantity) {
            public ReceiveProduct toDomain() {
                return new ReceiveProduct(
                        productNo,
                        acceptableQuantity,
                        rejectedQuantity,
                        description,
                        LocalDateTime.now());
            }
        }
    }
}
