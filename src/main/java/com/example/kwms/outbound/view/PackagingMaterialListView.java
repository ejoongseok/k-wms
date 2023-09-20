package com.example.kwms.outbound.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PackagingMaterialListView {

    @GetMapping("/web/packaging-materials")
    public String packagingMaterialListView() {
        return "packagingmaterial/list";
    }
}
