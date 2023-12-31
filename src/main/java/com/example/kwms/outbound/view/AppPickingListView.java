package com.example.kwms.outbound.view;

import com.example.kwms.common.auth.LoginUserNo;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppPickingListView {
    private final OutboundRepository outboundRepository;

    @GetMapping("/app/pickings")
    public String pickingListView(
            @LoginUserNo final Long userNo,
            final Model model) {
        final List<Outbound> outbounds = outboundRepository.listByPickerNo(userNo).stream()
                .filter(o -> !o.isPicked())
                .filter(o -> !o.isCanceled())
                .toList();
        model.addAttribute("hasAllocatedPicking", !outbounds.isEmpty());
        final boolean hasPickingToteAssigned = outbounds.stream()
                .anyMatch(outbound -> null != outbound.getPickingTote());
        model.addAttribute("hasPickingToteAssigned", hasPickingToteAssigned);
        final long count = outboundRepository.findAll().stream()
                .filter(o -> !o.isPicked())
                .filter(o -> !o.isCanceled())
                .filter(o -> null == o.getPickerNo())
                .count();
        model.addAttribute("hasUnassignedPicking", 0 < count);
        return "app/pickings";
    }
}
