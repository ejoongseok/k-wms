package com.example.kwms.inbound.view.view;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import com.example.kwms.inbound.domain.Receive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
        final List<Receive> receives = purchaseOrder.getReceives();
        receives.stream()
                .filter(r -> r.getReceiveNo().equals(receiveNo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "입고 번호에 해당하는 입고가 존재하지 않습니다. 입고 번호: %s".formatted(receiveNo)));
    }
}
