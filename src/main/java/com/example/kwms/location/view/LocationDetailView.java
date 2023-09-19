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
public class LocationDetailView {
    private final LocationRepository locationRepository;

    @GetMapping("/web/locations/{locationNo}")
    @Transactional(readOnly = true)
    public String locationsList(
            @PathVariable final Long locationNo,
            final Model model) {
        final Location location = locationRepository.getBy(locationNo);
        model.addAttribute("locationNo", location.getLocationNo());
        model.addAttribute("locationBarcode", location.getLocationBarcode());
        model.addAttribute("parentLocationNo", null == location.getParent() ? null : location.getParent().getLocationNo());
        model.addAttribute("hasChildren", !location.getChildren().isEmpty());
        // TODO 나중에는 해당로케이션 혹은 하위로케이션에 집품하는 재고가 없는지 확인해야됌.
        model.addAttribute("isUpdatable", true);
        return "location/detail";
    }

}
