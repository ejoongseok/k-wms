package com.example.kwms.outbound.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreateBulkOutboundView {

    @GetMapping("/web/outbounds/bulk/new")
    public String show() {
        return "outbound/bulk-new";
    }
}
