package com.example.kwms.location.view;

import com.example.kwms.location.domain.WarehouseTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class WarehouseTransferAddLocationView {
    private final WarehouseTransferRepository warehouseTransferRepository;

    @GetMapping("/web/warehouse-transfers/{warehouseTransferNo}/add-location")
    public String warehouseTransferAddLocationView(
            @PathVariable final Long warehouseTransferNo,
            final Model model) {
        final var warehouseTransfer = warehouseTransferRepository.getBy(warehouseTransferNo);
        model.addAttribute("warehouseTransferNo", warehouseTransferNo);
        return "warehousetransfer/add-location";
    }
}
