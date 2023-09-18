package com.example.kwms.location.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LocationListView {
    @GetMapping("/web/locations")
    public String locationsList() {
        return "location/list";
    }
}
