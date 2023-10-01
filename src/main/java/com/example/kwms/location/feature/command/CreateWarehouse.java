package com.example.kwms.location.feature.command;

import com.example.kwms.location.domain.Warehouse;
import com.example.kwms.location.domain.WarehouseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateWarehouse {
    private final WarehouseRepository warehouseRepository;

    @PostMapping("/warehouses")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@RequestBody @Valid final Request request) {
        final Warehouse warehouse = request.toDomain();

        warehouseRepository.save(warehouse);
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
        Warehouse toDomain() {
            return new Warehouse(
                    name,
                    address,
                    telNumber,
                    managerName,
                    managerTelNumber,
                    description
            );
        }
    }
}
