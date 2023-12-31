package com.example.kwms.outbound.view;

import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class OutboundDetailView {
    private final OutboundRepository outboundRepository;

    @GetMapping("/web/outbounds/{outboundNo}")
    @Transactional(readOnly = true)
    public String outboundListView(
            @PathVariable final Long outboundNo,
            final Model model) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);
        model.addAttribute("outboundNo", outbound.getOutboundNo());
        model.addAttribute("isCanceled", outbound.isCanceled());
        model.addAttribute("isManualOutbound", outbound.isManualOutbound());
        model.addAttribute("hasPickings", outbound.hasPickings());
        model.addAttribute("hasPickingTote", null != outbound.getPickingTote());
        model.addAttribute("hasPicker", null != outbound.getPickerNo());
        return "outbound/detail";
    }
}
