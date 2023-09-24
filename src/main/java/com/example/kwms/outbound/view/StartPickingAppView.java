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
public class StartPickingAppView {
    private final OutboundRepository outboundRepository;

    @GetMapping("/app/start-picking")
    public String startPickingAppView(
            @LoginUserNo final Long userNo,
            final Model model) {
        final List<Outbound> outbounds = outboundRepository.listByPickerNo(userNo);
        model.addAttribute("hasAllocatedPicking", !outbounds.isEmpty());
        return "app/start-picking";
    }
}
