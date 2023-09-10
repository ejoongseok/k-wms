package com.example.kwms.location.feature;

import com.example.kwms.location.domain.Warehouse;
import com.example.kwms.location.domain.WarehouseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateWarehouse {
    private final WarehouseRepository warehouseRepository;

    @PatchMapping("/warehouses/{warehouseNo}")
    @Transactional
    public void request(
            @PathVariable final Long warehouseNo,
            @RequestBody @Valid final Request request) {
        final Warehouse warehouse = warehouseRepository.getBy(warehouseNo);

        warehouse.update(
                request.name(),
                request.address(),
                request.telNumber(),
                request.managerName(),
                request.managerTelNumber(),
                request.description()
        );
    }

    public record Request(
            @NotBlank(message = "창고명은 필수입니다.")
            String name,
            @NotBlank(message = "주소는 필수입니다.")
            String address,
            @NotBlank(message = "전화번호는 필수입니다.")
            String telNumber,
            @NotBlank(message = "담당자명은 필수입니다.")
            String managerName,
            @NotBlank(message = "담당자 연락처는 필수입니다.")
            String managerTelNumber,
            String description) {
    }
}
