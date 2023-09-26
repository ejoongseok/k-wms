package com.example.kwms.outbound.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BulkOutboundListView {

    @GetMapping("/web/outbounds/bulk")
    public String show() {
        return "outbound/bulk-list";
    }
}
