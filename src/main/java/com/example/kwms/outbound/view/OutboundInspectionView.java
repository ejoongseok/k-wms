package com.example.kwms.outbound.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OutboundInspectionView {

    @GetMapping("/web/inspection")
    public String outboundInspectionView() {
        return "outbound/inspection";
    }
}
