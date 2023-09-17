package com.example.kwms.inbound.feature.view;

import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderProduct;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CreateLPNView {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/web/purchase-orders/{purchaseOrderNo}/purchase-order-products/{purchaseOrderProductNo}/create-lpn")
    @Transactional(readOnly = true)
    public String createLPN(
            @PathVariable final Long purchaseOrderNo,
            @PathVariable final Long purchaseOrderProductNo,
            final Model model
    ) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);
        model.addAttribute("purchaseOrderNo", purchaseOrder.getPurchaseOrderNo());
        final PurchaseOrderProduct purchaseOrderProduct = purchaseOrder.getPurchaseOrderProduct(purchaseOrderProductNo);
        model.addAttribute("purchaseOrderProductNo", purchaseOrderProduct.getProductNo());
        return "purchaseorder/lpn-new";
    }

}
