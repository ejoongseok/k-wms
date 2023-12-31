package com.example.kwms.outbound.feature.command;

import com.example.kwms.outbound.domain.MaterialType;
import com.example.kwms.outbound.domain.PackagingMaterial;
import com.example.kwms.outbound.domain.PackagingMaterialDimension;
import com.example.kwms.outbound.domain.PackagingMaterialRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdatePackagingMaterial {

    private final PackagingMaterialRepository packagingMaterialRepository;

    @PutMapping("/packaging-materials/{packagingMaterialNo}")
    @Transactional
    public void request(
            @PathVariable final Long packagingMaterialNo,
            @RequestBody @Valid final Request request) {
        final PackagingMaterial packagingMaterial = packagingMaterialRepository.getBy(packagingMaterialNo);
        if (!packagingMaterial.getCode().equals(request.code)) {
            packagingMaterialRepository.findByCode(request.code)
                    .ifPresent(it -> {
                        throw new IllegalArgumentException("이미 존재하는 포장재 코드입니다.");
                    });
        }
        packagingMaterial.update(
                request.name(),
                request.code(),
                new PackagingMaterialDimension(
                        request.innerWidthInMillimeters(),
                        request.innerHeightInMillimeters(),
                        request.innerLengthInMillimeters(),
                        request.outerWidthInMillimeters(),
                        request.outerHeightInMillimeters(),
                        request.outerLengthInMillimeters()
                ),
                request.weightInGrams(),
                request.maxWeightInGrams(),
                request.materialType());
    }

    public record Request(
            @NotBlank(message = "포장재 이름은 필수입니다.")
            String name,
            @NotBlank(message = "포장재 코드는 필수입니다.")
            String code,
            @NotNull(message = "내부 폭은 필수입니다.")
            @Min(value = 1, message = "내부 폭은 1mm 이상이어야 합니다.")
            Long innerWidthInMillimeters,
            @NotNull(message = "내부 높이는 필수입니다.")
            @Min(value = 1, message = "내부 높이는 1mm 이상이어야 합니다.")
            Long innerHeightInMillimeters,
            @NotNull(message = "내부 길이는 필수입니다.")
            @Min(value = 1, message = "내부 길이는 1mm 이상이어야 합니다.")
            Long innerLengthInMillimeters,
            @NotNull(message = "외부 폭은 필수입니다.")
            @Min(value = 1, message = "외부 폭은 1mm 이상이어야 합니다.")
            Long outerWidthInMillimeters,
            @NotNull(message = "외부 높이는 필수입니다.")
            @Min(value = 1, message = "외부 높이는 1mm 이상이어야 합니다.")
            Long outerHeightInMillimeters,
            @NotNull(message = "외부 길이는 필수입니다.")
            @Min(value = 1, message = "외부 길이는 1mm 이상이어야 합니다.")
            Long outerLengthInMillimeters,
            @NotNull(message = "무게는 필수입니다.")
            @Min(value = 1, message = "무게는 1g 이상이어야 합니다.")
            Long weightInGrams,
            @NotNull(message = "최대 무게는 필수입니다.")
            @Min(value = 1, message = "최대 무게는 1g 이상이어야 합니다.")
            Long maxWeightInGrams,
            @NotNull(message = "포장재 타입은 필수입니다.")
            MaterialType materialType
    ) {
        public PackagingMaterial toDomain() {
            return new PackagingMaterial(
                    name,
                    code,
                    new PackagingMaterialDimension(
                            innerWidthInMillimeters,
                            innerHeightInMillimeters,
                            innerLengthInMillimeters,
                            outerWidthInMillimeters,
                            outerHeightInMillimeters,
                            outerLengthInMillimeters
                    ),
                    weightInGrams,
                    maxWeightInGrams,
                    materialType
            );
        }
    }
}
