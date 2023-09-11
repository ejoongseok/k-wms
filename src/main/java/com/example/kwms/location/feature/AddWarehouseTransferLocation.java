package com.example.kwms.location.feature;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddWarehouseTransferLocation {
    private final LocationRepository locationRepository;
    private final WarehouseTransferRepository warehouseTransferRepository;

    @Transactional
    @PostMapping("/warehouse-transfers/{warehouseTransferNo}/locations")
    public void request(
            @PathVariable final Long warehouseTransferNo,
            @RequestBody @Valid final Request request) {
        final Location location = locationRepository.getBy(request.locationBarcode);
        final WarehouseTransfer warehouseTransfer = warehouseTransferRepository.getBy(warehouseTransferNo);

        addLocation(warehouseTransfer, location);

        // OK 해당 창고의 로케이션인지 확인.
        // TODO 로케이션이 집품중인게 있는지 확인.
        // OK 상태변경
        // OK 로케이션에 재고이동할 상품외에 상품이 존재하는지 확인
        // 로케이션에 재고이동할 상품이 있는지 확인.
    }

    void addLocation(final WarehouseTransfer warehouseTransfer, final Location location) {
        warehouseTransfer.addLocation(location);
        location.warehouseTransferred();
    }

    public record Request(
            @NotBlank(message = "로케이션 바코드는 필수입니다.")
            String locationBarcode) {
    }
}