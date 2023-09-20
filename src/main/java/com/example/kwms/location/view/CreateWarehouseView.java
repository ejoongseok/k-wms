package com.example.kwms.location.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreateWarehouseView {

    @GetMapping("/web/warehouses/new")
    public String findAll() {
        return "/warehouse/new";
    }
}
