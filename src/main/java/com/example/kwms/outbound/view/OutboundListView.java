package com.example.kwms.outbound.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OutboundListView {

    @GetMapping("/web/outbounds")
    public String outboundListView() {
        return "outbound/list";
    }
}
