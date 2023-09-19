package com.example.kwms.location.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AddInventoryView {

    @GetMapping("/web/locations/add-inventory")
    public String createLocations() {
        return "location/add-inventory";
    }

}
