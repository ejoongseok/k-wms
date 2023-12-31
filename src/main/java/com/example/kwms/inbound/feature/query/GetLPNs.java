package com.example.kwms.inbound.feature.query;

import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
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
                    new PurchaseOrderPresenter(purchaseOrder).getPurchaseOrderStatus());
        }

    }
}
