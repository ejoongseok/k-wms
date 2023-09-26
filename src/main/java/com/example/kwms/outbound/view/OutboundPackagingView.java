package com.example.kwms.outbound.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OutboundPackagingView {

    @GetMapping("/web/packaging")
    public String outboundPackagingView() {
        return "outbound/packaging";
    }
}
