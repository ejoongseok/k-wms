package com.example.kwms.outbound.feature.query;

import com.example.kwms.outbound.domain.MaterialType;
import com.example.kwms.outbound.domain.PackagingMaterial;
import com.example.kwms.outbound.domain.PackagingMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetPackagingMaterials {
    private final PackagingMaterialRepository packagingMaterialRepository;

    @GetMapping("/packaging-materials")
    public List<Response> findAll() {
        final List<Response> responses = new ArrayList<>();
        final List<PackagingMaterial> packagingMaterials = packagingMaterialRepository.findAll();
        for (final PackagingMaterial packagingMaterial : packagingMaterials) {
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
                    packagingMaterial.getMaxWeightInGrams()

            );
            responses.add(response);
        }
        return responses;
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
