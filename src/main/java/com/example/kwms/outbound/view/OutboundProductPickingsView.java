package com.example.kwms.outbound.view;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundProduct;
import com.example.kwms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class OutboundProductPickingsView {
    private final OutboundRepository outboundRepository;

    @GetMapping("/web/outbounds/{outboundNo}/outbound-products/{outboundProductNo}/pickings")
    @Transactional(readOnly = true)
    public String outboundListView(
            @PathVariable final Long outboundNo,
            @PathVariable final Long outboundProductNo,
            final Model model) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);
        final OutboundProduct outboundProduct = outbound.getOutboundProducts().stream()
                .filter(op -> op.getOutboundProductNo().equals(outboundProductNo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("해당 출고 상품이 없습니다. 출고상품번호: " + outboundProductNo));
        model.addAttribute("outboundNo", outbound.getOutboundNo());
        model.addAttribute("outboundProductNo", outboundProduct.getOutboundProductNo());
        model.addAttribute("isPicked", outboundProduct.isPicked());
        model.addAttribute("hasPickingTote", null != outbound.getPickingTote());
        return "outbound/outbound-product-pickings";
    }
}
