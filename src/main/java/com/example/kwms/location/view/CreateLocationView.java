package com.example.kwms.location.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CreateLocationView {

    @GetMapping("/web/locations/new")
    public String createLocations() {
        return "location/new";
    }

}
