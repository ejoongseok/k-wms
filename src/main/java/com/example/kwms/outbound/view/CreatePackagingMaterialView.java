package com.example.kwms.outbound.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreatePackagingMaterialView {

    @GetMapping("/web/packaging-materials/new")
    public String packagingMaterialListView() {
        return "packagingmaterial/new";
    }
}
