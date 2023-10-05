package com.example.kwms.inbound.feature.query;

import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderProduct;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import com.example.kwms.inbound.domain.Receive;
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
public class GetPurchaseOrderDetail {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductClient productClient;

    @GetMapping("/purchase-orders/{purchaseOrderNo}")
    @ResponseBody
    @Transactional(readOnly = true)
    public PurchaseOrderResponseDetail getPurchaseOrder(@PathVariable final Long purchaseOrderNo) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);
        return new PurchaseOrderResponseDetail(
                purchaseOrder.getPurchaseOrderNo(),
                purchaseOrder.getTitle(),
                purchaseOrder.getDescription(),
                createPurchaseOrderProductResponses(purchaseOrder),
                createPurchaseOrderReceiveResponses(purchaseOrder));
    }

    private List<PurchaseOrderProductResponse> createPurchaseOrderProductResponses(final PurchaseOrder purchaseOrder) {
        final List<PurchaseOrderProductResponse> purchaseOrderProductResponses = new ArrayList<>();
        for (final PurchaseOrderProduct purchaseOrderProduct : purchaseOrder.getPurchaseOrderProducts()) {
            final Product product = productClient.getBy(purchaseOrderProduct.getProductNo());
            final PurchaseOrderProductResponse productResponse = new PurchaseOrderProductResponse(
                    purchaseOrderProduct.getPurchaseOrderProductNo(),
                    product.getProductNo(),
                    product.getProductName(),
                    purchaseOrderProduct.getUnitPrice(),
                    purchaseOrderProduct.getRequestQuantity(),
                    purchaseOrderProduct.getDescription());
            purchaseOrderProductResponses.add(productResponse);
        }
        return purchaseOrderProductResponses;
    }

    private List<PurchaseOrderReceiveResponse> createPurchaseOrderReceiveResponses(final PurchaseOrder purchaseOrder) {
        final List<PurchaseOrderReceiveResponse> purchaseOrderReceiveResponses = new ArrayList<>();
        for (final Receive receive : purchaseOrder.getReceives()) {
            final PurchaseOrderReceiveResponse receiveResponse = new PurchaseOrderReceiveResponse(
                    receive.getReceiveNo(),
                    receive.getName(),
                    receive.getCreatedAt());
            purchaseOrderReceiveResponses.add(receiveResponse);
        }
        return purchaseOrderReceiveResponses;
    }

    private record PurchaseOrderResponseDetail(
            Long purchaseOrderNo,
            String title,
            String description,
            List<PurchaseOrderProductResponse> products,
            List<PurchaseOrderReceiveResponse> receives) {
    }

    private record PurchaseOrderProductResponse(
            Long purchaseOrderProductNo,
            Long productNo,
            String productName,
            Long unitPrice,
            Long quantity,
            String description) {
    }

    private record PurchaseOrderReceiveResponse(
            Long receiveNo,
            String name,
            LocalDateTime createdAt) {
    }
}
