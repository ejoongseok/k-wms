package com.example.kwms.inbound.view.view;

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
public class AddReceiveView {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/web/purchase-orders/{purchaseOrderNo}/add-receive")
    @Transactional(readOnly = true)
    public String getPurchaseOrder(@PathVariable final Long purchaseOrderNo, final Model model) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);
        model.addAttribute("purchaseOrderNo", purchaseOrderNo);
        model.addAttribute("isReceived", purchaseOrder.isReceived());
        return "purchaseorder/add-receive";
    }
}
