package com.example.kwms.inbound.view.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PurchaseOrdersView {

    @GetMapping("/web/purchase-orders")
    public String purchaseOrderList() {
        return "purchaseorder/list";
    }

}
