package com.example.kwms.outbound.feature;

import com.example.kwms.outbound.domain.BulkOutbound;
import com.example.kwms.outbound.domain.BulkOutboundRepository;
import com.example.kwms.outbound.domain.PackagingMaterial;
import com.example.kwms.outbound.domain.PackagingMaterialRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class BulkPackedOutbound {
    private final BulkOutboundRepository bulkOutboundRepository;
    private final PackagingMaterialRepository packagingMaterialRepository;

    @PostMapping("/bulk-outbounds/{bulkOutboundNo}/packed")
    @Transactional
    public void request(
            @PathVariable final Long bulkOutboundNo,
            @RequestBody @Valid final Request request) {
        final BulkOutbound bulkOutbound = bulkOutboundRepository.getBy(bulkOutboundNo);
        final PackagingMaterial packagingMaterial = packagingMaterialRepository.getBy(request.packagingMaterialCode);
        bulkOutbound.packed(
                packagingMaterial,
                request.weightInGrams,
                request.boxWidthInMillimeters,
                request.boxLengthInMillimeters,
                request.boxHeightInMillimeters
        );
    }

    public record Request(
            @NotBlank(message = "포장자재 바코드는 필수입니다.")
            String packagingMaterialCode,
            @NotNull(message = "포장자재 무게는 필수입니다.")
            @Min(value = 1, message = "포장자재 무게는 1g 이상이어야 합니다.")
            Long weightInGrams,
            @NotNull(message = "포장자재 가로는 필수입니다.")
            @Min(value = 1, message = "포장자재 가로는 1mm 이상이어야 합니다.")
            Long boxWidthInMillimeters,
            @NotNull(message = "포장자재 세로는 필수입니다.")
            @Min(value = 1, message = "포장자재 세로는 1mm 이상이어야 합니다.")
            Long boxLengthInMillimeters,
            @NotNull(message = "포장자재 높이는 필수입니다.")
            @Min(value = 1, message = "포장자재 높이는 1mm 이상이어야 합니다.")
            Long boxHeightInMillimeters) {
    }
}
