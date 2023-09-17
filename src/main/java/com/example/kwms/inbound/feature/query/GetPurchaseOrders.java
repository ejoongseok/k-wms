package com.example.kwms.inbound.feature.query;

import com.example.kwms.inbound.domain.LPN;
import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderProduct;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GetPurchaseOrders {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/purchase-orders/{purchaseOrderNo}/purchase-order-products/{purchaseOrderProductNo}/lpns")
    @ResponseBody
    @Transactional(readOnly = true)
    public List<LPNResponse> findAll(
            @PathVariable final Long purchaseOrderNo,
            @PathVariable final Long purchaseOrderProductNo
    ) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);
        final PurchaseOrderProduct purchaseOrderProduct = purchaseOrder.getPurchaseOrderProduct(purchaseOrderProductNo);
        return purchaseOrderProduct.getLpns().stream()
                .map(LPNResponse::from)
                .toList();
    }

    private record LPNResponse(
            Long lpnNo,
            String lpnBarcode,
            LocalDateTime expiringAt) {
        static LPNResponse from(final LPN lpn) {
            return new LPNResponse(
                    lpn.getLpnNo(),
                    lpn.getLpnBarcode(),
                    lpn.getExpiringAt()
            );
        }
    }
}
