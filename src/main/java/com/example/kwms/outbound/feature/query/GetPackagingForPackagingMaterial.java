package com.example.kwms.outbound.feature.query;

import com.example.kwms.outbound.domain.PackagingMaterial;
import com.example.kwms.outbound.domain.PackagingMaterialDimension;
import com.example.kwms.outbound.domain.PackagingMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPackagingForPackagingMaterial {
    private final PackagingMaterialRepository packagingMaterialRepository;

    @GetMapping("/packaging/packaging-materials/{code}")
    @Transactional(readOnly = true)
    public Response findBy(
            @PathVariable final String code) {
        final PackagingMaterial packagingMaterial = packagingMaterialRepository.getBy(code);

        final PackagingMaterialDimension packagingMaterialDimension = packagingMaterial.getPackagingMaterialDimension();

        final Long width = packagingMaterialDimension.getOuterWidthInMillimeters();
        final Long length = packagingMaterialDimension.getOuterLengthInMillimeters();
        final Long height = packagingMaterialDimension.getOuterHeightInMillimeters();

        return new Response(
                width,
                length,
                height);
    }

    private record Response(Long width, Long length, Long height) {
    }

}
