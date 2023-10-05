package com.example.kwms.inbound.view.view;

import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UpdatePurchaseOrderView {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/web/purchase-orders/{purchaseOrderNo}/update")
    @Transactional(readOnly = true)
    public String assignReceive(@PathVariable final Long purchaseOrderNo) {
        validate(purchaseOrderNo);
        return "purchaseorder/update";
    }

    private void validate(final Long purchaseOrderNo) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);
        if (!purchaseOrder.getReceives().isEmpty()) {
            throw new IllegalStateException("이미 입고가 등록되어 있습니다.");
        }
    }
}
