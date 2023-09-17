package com.example.kwms.inbound.feature.query;

import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import com.example.kwms.inbound.domain.Receive;
import com.example.kwms.inbound.domain.ReceiveProduct;
import com.example.kwms.outbound.domain.Product;
import com.example.kwms.outbound.feature.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GetReceiveDetail {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductClient productClient;

    @GetMapping("/purchase-orders/{purchaseOrderNo}/receives/{receiveNo}")
    @ResponseBody
    @Transactional(readOnly = true)
    public ReceiveResponse getReceive(
            @PathVariable final Long purchaseOrderNo,
            @PathVariable final Long receiveNo) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);
        final List<ReceiveProductResponse> purchaseOrderReceiveResponses = new ArrayList<>();
        final Receive receive = purchaseOrder.getReceive(receiveNo);
        for (final ReceiveProduct receiveProduct : receive.getReceiveProducts()) {
            final Product product = productClient.getBy(receiveProduct.getProductNo());
            final ReceiveProductResponse receiveResponse = new ReceiveProductResponse(
                    product.getProductNo(),
                    product.getProductName(),
                    null == receiveProduct.getAcceptableQuantity() ? 0 : receiveProduct.getAcceptableQuantity(),
                    null == receiveProduct.getRejectedQuantity() ? 0 : receiveProduct.getRejectedQuantity(),
                    receiveProduct.getInspectionComment());
            purchaseOrderReceiveResponses.add(receiveResponse);
        }
        return new ReceiveResponse(
                receive.getReceiveNo(),
                receive.getName(),
                receive.getCreatedAt(),
                purchaseOrderReceiveResponses);
    }

    private record ReceiveResponse(
            Long receiveNo,
            String name,
            LocalDateTime createdAt,
            List<ReceiveProductResponse> receiveProducts) {
    }

    private record ReceiveProductResponse(
            Long productNo,
            String productName,
            Long acceptableQuantity,
            Long rejectedQuantity,
            String inspectionComment) {
    }
}
