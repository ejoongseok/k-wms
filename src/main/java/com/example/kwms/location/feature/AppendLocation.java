package com.example.kwms.location.feature;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.outbound.domain.Picking;
import com.example.kwms.outbound.domain.PickingRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AppendLocation {

    private final LocationRepository locationRepository;
    private final PickingRepository pickingRepository;

    @PostMapping("/locations/append")
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        final Location current = locationRepository.getBy(request.currentLocationBarcode);
        final Location target = locationRepository.getBy(request.targetLocationBarcode);
        validate(current.getAllInventories());

        target.appendLocation(current);
    }

    private void validate(final List<Inventory> allInventories) {
        final Set<Long> inventoryNos = allInventories.stream()
                .map(Inventory::getInventoryNo)
                .collect(Collectors.toUnmodifiableSet());
        final List<Picking> pickings = pickingRepository.listByInventoryNos(inventoryNos);
        pickings.stream()
                .filter(p -> !p.isPicked())
                .findFirst()
                .ifPresent(p -> {
                    throw new IllegalStateException("집품중인 상품이 있습니다. 집품 번호: %d".formatted(p.getPickingNo()));
                });
    }

    public record Request(
            @NotBlank(message = "현재 로케이션 바코드는 필수입니다.")
            String currentLocationBarcode,
            @NotBlank(message = "대상 로케이션 바코드는 필수입니다.")
            String targetLocationBarcode) {
    }
}
