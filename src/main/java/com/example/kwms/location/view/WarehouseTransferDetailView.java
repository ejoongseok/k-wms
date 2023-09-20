package com.example.kwms.location.view;

import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class WarehouseTransferDetailView {
    private final WarehouseTransferRepository warehouseTransferRepository;

    @GetMapping("/web/warehouse-transfers/{warehouseTransferNo}")
    @Transactional(readOnly = true)
    public String warehouseTransferDetailView(
            @PathVariable final Long warehouseTransferNo,
            final Model model) {
        final WarehouseTransfer warehouseTransfer = warehouseTransferRepository.getBy(warehouseTransferNo);
        model.addAttribute("warehouseTransferNo", warehouseTransferNo);
        model.addAttribute("isUpdatable", warehouseTransfer.isUpdatable());
        model.addAttribute("hasLocations", warehouseTransfer.hasLocations());
        model.addAttribute("isShipped", warehouseTransfer.isShipped());
        model.addAttribute("isReceived", warehouseTransfer.isReceived());
        return "warehousetransfer/detail";
    }
}
