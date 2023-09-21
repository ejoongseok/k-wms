package com.example.kwms.outbound.view;

import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class OutboundCancelView {
    private final OutboundRepository outboundRepository;

    @GetMapping("/web/outbounds/{outboundNo}/cancel")
    public String outboundListView(
            @PathVariable final Long outboundNo,
            final Model model) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);
        model.addAttribute("outboundNo", outbound.getOutboundNo());
        return "outbound/outbound-cancel";
    }
}
