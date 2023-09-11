package com.example.kwms.outbound.feature;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.InventoryRepository;
import com.example.kwms.outbound.domain.ConstructOutbound;
import com.example.kwms.outbound.domain.Order;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import com.example.kwms.outbound.domain.PackagingMaterial;
import com.example.kwms.outbound.domain.PackagingMaterialRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
class CreateOutbound {
    private final OrderClient orderClient;
    private final OutboundRepository outboundRepository;
    private final InventoryRepository inventoryRepository;
    private final PackagingMaterialRepository packagingMaterialRepository;
    private final ConstructOutbound constructOutbound = new ConstructOutbound();

    @PostMapping("/outbounds")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        //주문 정보를 가져오고.
        final Order order = orderClient.getBy(request.orderNo);
        final Set<Long> productNos = order.getProductNos();
        final List<Inventory> inventories = getInventories(request.warehouseNo, productNos);
        final List<PackagingMaterial> packagingMaterials = packagingMaterialRepository.findAll();
        // ProductClient에서 가져온 정보를 사용하도록 수정
        final Long totalWeight = 100L;
        final Long totalVolume = 100L;

        final Outbound outbound = constructOutbound.create(
                inventories,
                packagingMaterials,
                order,
                request.isPriorityDelivery,
                request.desiredDeliveryAt,
                totalWeight,
                totalVolume);

        outboundRepository.save(outbound);
    }

    private List<Inventory> getInventories(
            final Long warehouseNo,
            final Set<Long> productNos) {
        return productNos.stream()
                .flatMap(productNo -> inventoryRepository.listBy(warehouseNo, productNo).stream())
                .toList();
    }

    public record Request(
            @NotNull(message = "창고번호는 필수입니다.")
            Long warehouseNo,
            @NotNull(message = "주문번호는 필수입니다.")
            Long orderNo,
            @NotNull(message = "우선출고여부는 필수입니다.")
            Boolean isPriorityDelivery,
            @NotNull(message = "희망배송일은 필수입니다.")
            LocalDate desiredDeliveryAt
    ) {
    }
}
