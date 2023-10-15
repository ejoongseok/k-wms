package com.example.kwms.inbound.view.view;

import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderProduct;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import com.example.kwms.inbound.domain.Receive;
import com.example.kwms.inbound.domain.ReceiveProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurchaseOrderDetailView {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/web/purchase-orders/{purchaseOrderNo}")
    @Transactional(readOnly = true)
    public String getPurchaseOrder(@PathVariable final Long purchaseOrderNo, final Model model) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);
        model.addAttribute("purchaseOrderNo", purchaseOrderNo);
        model.addAttribute("hasReceive", !purchaseOrder.getReceives().isEmpty());
        model.addAttribute("isAllReceived", isAllReceived(purchaseOrder));
        return "purchaseorder/detail";
    }

    private boolean isAllReceived(final PurchaseOrder purchaseOrder) {
        final List<PurchaseOrderProduct> purchaseOrderProducts = purchaseOrder.getPurchaseOrderProducts();
        final long totalRequestedQuantity = purchaseOrderProducts.stream()
                .mapToLong(PurchaseOrderProduct::getRequestQuantity)
                .sum();
        final List<Receive> receives = purchaseOrder.getReceives();
        final long totalReceivedQuantity = receives.stream()
                .flatMap(r -> r.getReceiveProducts().stream())
                .mapToLong(ReceiveProduct::totalQuantity)
                .sum();
        return totalRequestedQuantity == totalReceivedQuantity;
    }

}
