package com.example.kwms.location.view;

import com.example.kwms.location.domain.Warehouse;
import com.example.kwms.location.domain.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class WarehouseDetailView {
    private final WarehouseRepository warehouseRepository;

    @GetMapping("/web/warehouses/{warehouseNo}")
    public String findBy(
            @PathVariable final Long warehouseNo,
            final Model model) {
        final Warehouse warehouse = warehouseRepository.getBy(warehouseNo);
        model.addAttribute("warehouseNo", warehouse.getWarehouseNo());
        return "/warehouse/detail";
    }
}
