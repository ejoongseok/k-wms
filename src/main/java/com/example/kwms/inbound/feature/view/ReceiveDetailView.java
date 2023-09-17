package com.example.kwms.inbound.feature.view;

import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import com.example.kwms.inbound.domain.Receive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ReceiveDetailView {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/web/purchase-orders/{purchaseOrderNo}/receives/{receiveNo}")
    @Transactional(readOnly = true)
    public String getPurchaseOrder(
            @PathVariable final Long purchaseOrderNo,
            @PathVariable final Long receiveNo,
            final Model model) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);
        final Receive receive = purchaseOrder.getReceive(receiveNo);
        model.addAttribute("purchaseOrderNo", purchaseOrder.getPurchaseOrderNo());
        model.addAttribute("receiveNo", receive.getReceiveNo());
        return "purchaseorder/receive-detail";
    }

}
