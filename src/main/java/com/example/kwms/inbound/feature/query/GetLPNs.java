package com.example.kwms.inbound.feature.query;

import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderProduct;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import com.example.kwms.inbound.domain.ReceiveProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GetLPNs {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/purchase-orders")
    @ResponseBody
    @Transactional(readOnly = true)
    public List<PurchaseOrderResponse> findAll() {
        return purchaseOrderRepository.findAll().stream()
                .map(PurchaseOrderResponse::from)
                .toList();
    }

    private record PurchaseOrderResponse(Long purchaseOrderNo, String name, String description, String status) {
        static PurchaseOrderResponse from(final PurchaseOrder purchaseOrder) {
            return new PurchaseOrderResponse(
                    purchaseOrder.getPurchaseOrderNo(),
                    purchaseOrder.getTitle(),
                    purchaseOrder.getDescription(),
                    determineStatus(purchaseOrder));
        }

        private static String determineStatus(final PurchaseOrder purchaseOrder) {
            String status = "발주";
            if (isAllReceived(purchaseOrder)) {
                status = "입고 완료";
            } else if (!purchaseOrder.getReceives().isEmpty()) {
                status = "입고 중";
            }
            return status;
        }

        private static boolean isAllReceived(final PurchaseOrder purchaseOrder) {
            final long totalRequestedQuantity = purchaseOrder.getPurchaseOrderProducts().stream()
                    .mapToLong(PurchaseOrderProduct::getRequestQuantity)
                    .sum();
            final long totalReceivedQuantity = purchaseOrder.getReceives().stream()
                    .flatMap(r -> r.getReceiveProducts().stream())
                    .mapToLong(ReceiveProduct::totalQuantity)
                    .sum();
            return totalRequestedQuantity == totalReceivedQuantity;
        }
    }
}
