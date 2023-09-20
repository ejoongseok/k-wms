package com.example.kwms.location.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CreateWarehouseTransferView {
    @GetMapping("/web/warehouse-transfers/new")
    public String warehouseTransferListView() {
        return "warehousetransfer/new";
    }
}
