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
        final List<Outbound> outbounds = outboundRepository.listByPickerNo(userNo);
        model.addAttribute("hasAllocatedPicking", !outbounds.isEmpty());
        final boolean hasPickingToteAssigned = outbounds.stream()
                .anyMatch(outbound -> null != outbound.getPickingTote());
        model.addAttribute("hasPickingToteAssigned", hasPickingToteAssigned);
        return "app/pickings";
    }
}
