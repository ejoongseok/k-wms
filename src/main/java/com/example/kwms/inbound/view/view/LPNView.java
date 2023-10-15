package com.example.kwms.inbound.view.view;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderProduct;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LPNView {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/web/purchase-orders/{purchaseOrderNo}/purchase-order-products/{purchaseOrderProductNo}/lpns")
    @Transactional(readOnly = true)
    public String getPurchaseOrder(
            @PathVariable final Long purchaseOrderNo,
            @PathVariable final Long purchaseOrderProductNo,
            final Model model) {
        validate(purchaseOrderNo, purchaseOrderProductNo);

        model.addAttribute("purchaseOrderNo", purchaseOrderNo);
        model.addAttribute("purchaseOrderProductNo", purchaseOrderProductNo);
        return "purchaseorder/lpn-list";
    }

    private void validate(final Long purchaseOrderNo, final Long purchaseOrderProductNo) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);
        final List<PurchaseOrderProduct> purchaseOrderProducts = purchaseOrder.getPurchaseOrderProducts();
        purchaseOrderProducts.stream()
                .filter(product -> product.getProductNo().equals(purchaseOrderProductNo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "발주 상품 번호에 해당하는 발주 상품이 존재하지 않습니다. 상품 번호: %s".formatted(purchaseOrderProductNo)));
    }

}
