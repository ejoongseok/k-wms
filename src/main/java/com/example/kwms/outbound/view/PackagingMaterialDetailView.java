package com.example.kwms.outbound.view;

import com.example.kwms.outbound.domain.PackagingMaterial;
import com.example.kwms.outbound.domain.PackagingMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PackagingMaterialDetailView {
    private final PackagingMaterialRepository packagingMaterialRepository;

    @GetMapping("/web/packaging-materials/{packagingMaterialNo}")
    public String packagingMaterialListView(
            @PathVariable final Long packagingMaterialNo,
            final Model model) {
        final PackagingMaterial packagingMaterial = packagingMaterialRepository.getBy(packagingMaterialNo);
        model.addAttribute("packagingMaterial", packagingMaterial);
        return "packagingmaterial/detail";
    }
}
