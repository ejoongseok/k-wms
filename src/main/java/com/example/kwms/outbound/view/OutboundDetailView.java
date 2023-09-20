package com.example.kwms.outbound.view;

import com.example.kwms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class OutboundDetailView {
    private final OutboundRepository outboundRepository;

    @GetMapping("/web/outbounds/{outboundNo}")
    public String outboundListView(
            @PathVariable final Long outboundNo,
            final Model model) {
        model.addAttribute("outboundNo", outboundRepository.getBy(outboundNo).getOutboundNo());
        return "outbound/detail";
    }
}
