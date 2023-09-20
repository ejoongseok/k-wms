package com.example.kwms.location.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WarehouseListView {

    @GetMapping("/web/warehouses")
    public String findAll() {
        return "/warehouse/list";
    }
}
