package com.example.kwms.inbound.feature.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CreatePurchaseOrderView {

    @GetMapping("/web/purchase-orders/new")
    public String getPurchaseOrder() {
        return "purchaseorder/new";
    }

}
