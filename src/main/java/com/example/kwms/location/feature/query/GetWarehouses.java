package com.example.kwms.location.feature.query;

import com.example.kwms.location.domain.Warehouse;
import com.example.kwms.location.domain.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class GetWarehouses {

    private final WarehouseRepository warehouseRepository;

    @GetMapping("/warehouses")
    public List<Response> findAll() {
        final List<Warehouse> warehouses = warehouseRepository.findAll();
        final List<Response> responses = new ArrayList<>();
        for (final Warehouse warehouse : warehouses) {
            final Long warehouseNo = warehouse.getWarehouseNo();
            final String name = warehouse.getName();
            final String address = warehouse.getAddress();
            final String managerName = warehouse.getManagerName();

            final Response response = new Response(
                    warehouseNo,
                    name,
                    address,
                    managerName
            );
            responses.add(response);
        }
        return responses;
    }

    private record Response(Long warehouseNo, String name, String address, String managerName) {
    }

}
