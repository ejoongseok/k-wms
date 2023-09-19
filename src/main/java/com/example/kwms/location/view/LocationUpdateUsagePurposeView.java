package com.example.kwms.location.view;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class LocationUpdateUsagePurposeView {
    private final LocationRepository locationRepository;

    @GetMapping("/web/locations/{locationNo}/update-usage-purpose")
    @Transactional(readOnly = true)
    public String updatePurposeUsage(
            @PathVariable final Long locationNo,
            final Model model) {
        final Location location = locationRepository.getBy(locationNo);
        model.addAttribute("locationNo", location.getLocationNo());
        return "location/update-usage-purpose";
    }

}
