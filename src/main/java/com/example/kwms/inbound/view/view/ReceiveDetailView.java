package com.example.kwms.inbound.view.view;

import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import com.example.kwms.inbound.feature.query.PurchaseOrderPresenter;
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
        validate(purchaseOrderNo, receiveNo);

        model.addAttribute("purchaseOrderNo", purchaseOrderNo);
        model.addAttribute("receiveNo", receiveNo);
        return "purchaseorder/receive-detail";
    }

    private void validate(final Long purchaseOrderNo, final Long receiveNo) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);
        new PurchaseOrderPresenter(purchaseOrder).getReceive(receiveNo);
    }
}
