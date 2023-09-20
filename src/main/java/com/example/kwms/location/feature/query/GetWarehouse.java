package com.example.kwms.location.feature.query;

import com.example.kwms.location.domain.Warehouse;
import com.example.kwms.location.domain.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GetWarehouse {

    private final WarehouseRepository warehouseRepository;

    @GetMapping("/warehouses/{warehouseNo}")
    public Response findBy(
            @PathVariable final Long warehouseNo) {
        final Warehouse warehouse = warehouseRepository.getBy(warehouseNo);
        return new Response(
                warehouseNo,
                warehouse.getName(),
                warehouse.getAddress(),
                warehouse.getTelNumber(),
                warehouse.getManagerName(),
                warehouse.getManagerTelNumber(),
                warehouse.getDescription());
    }

    private record Response(
            Long warehouseNo,
            String name,
            String address,
            String telNumber,
            String managerName,
            String managerTelNumber,
            String description) {
    }

}
