package com.example.kwms.inbound.feature.view;

import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PurchaseOrderDetailView {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/web/purchase-orders/{purchaseOrderNo}")
    @Transactional(readOnly = true)
    public String getPurchaseOrder(@PathVariable final Long purchaseOrderNo, final Model model) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);
        model.addAttribute("purchaseOrderNo", purchaseOrderNo);
        model.addAttribute("isReceived", purchaseOrder.isReceived());
        model.addAttribute("isReceiveCompleted", purchaseOrder.isReceiveCompleted());
        return "purchaseorder/detail";
    }

}
