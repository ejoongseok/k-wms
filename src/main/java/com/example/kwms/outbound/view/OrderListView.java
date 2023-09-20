package com.example.kwms.outbound.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderListView {

    @GetMapping("/web/orders")
    public String packagingMaterialListView() {
        return "order/list";
    }
}
