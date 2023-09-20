package com.example.kwms.outbound.feature.query;

import com.example.kwms.outbound.domain.MaterialType;
import com.example.kwms.outbound.domain.PackagingMaterial;
import com.example.kwms.outbound.domain.PackagingMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPackagingMaterial {
    private final PackagingMaterialRepository packagingMaterialRepository;

    @GetMapping("/packaging-materials/{packagingMaterialNo}")
    public Response findAll(
            @PathVariable final Long packagingMaterialNo) {
        final PackagingMaterial packagingMaterial = packagingMaterialRepository.getBy(packagingMaterialNo);
        final Response response = new Response(
                packagingMaterial.getPackagingMaterialNo(),
                packagingMaterial.getName(),
                packagingMaterial.getCode(),
                packagingMaterial.getMaterialType().getDescription(),
                packagingMaterial.getMaterialType(),
                packagingMaterial.getPackagingMaterialDimension().getInnerWidthInMillimeters(),
                packagingMaterial.getPackagingMaterialDimension().getInnerHeightInMillimeters(),
                packagingMaterial.getPackagingMaterialDimension().getInnerLengthInMillimeters(),
                packagingMaterial.getPackagingMaterialDimension().getOuterWidthInMillimeters(),
                packagingMaterial.getPackagingMaterialDimension().getOuterHeightInMillimeters(),
                packagingMaterial.getPackagingMaterialDimension().getOuterLengthInMillimeters(),
                packagingMaterial.getWeightInGrams(),
                packagingMaterial.getMaxWeightInGrams());
        return response;
    }

    private record Response(
            Long packagingMaterialNo,
            String name,
            String code,
            String typeDescription,
            MaterialType materialType,
            Long innerWidthInMillimeters,
            Long innerHeightInMillimeters,
            Long innerLengthInMillimeters,
            Long outerWidthInMillimeters,
            Long outerHeightInMillimeters,
            Long outerLengthInMillimeters,
            Long weightInGrams,
            Long maxWeightInGrams) {

    }
}
