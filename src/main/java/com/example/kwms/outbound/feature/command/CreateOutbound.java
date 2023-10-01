package com.example.kwms.outbound.feature.command;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.InventoryRepository;
import com.example.kwms.location.domain.WarehouseRepository;
import com.example.kwms.outbound.domain.ConstructOutbound;
import com.example.kwms.outbound.domain.Order;
import com.example.kwms.outbound.domain.OrderProduct;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import com.example.kwms.outbound.domain.PackagingMaterial;
import com.example.kwms.outbound.domain.PackagingMaterialRepository;
import com.example.kwms.outbound.domain.Product;
import com.example.kwms.outbound.feature.OrderClient;
import com.example.kwms.outbound.feature.ProductClient;
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
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class CreateOutbound {
    private final OrderClient orderClient;
    private final ProductClient productClient;
    private final OutboundRepository outboundRepository;
    private final InventoryRepository inventoryRepository;
    private final PackagingMaterialRepository packagingMaterialRepository;
    private final WarehouseRepository warehouseRepository;
    private final ConstructOutbound constructOutbound = new ConstructOutbound();

    @PostMapping("/outbounds")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        final Order order = orderClient.getBy(request.orderNo);
        final Optional<Outbound> byOrderNo = outboundRepository.getByOrderNo(request.orderNo);
        byOrderNo
                .ifPresent(outbound -> {
                    throw new IllegalArgumentException("이미 출고 요청이 완료된 주문입니다. 출고 번호: %d".formatted(outbound.getOutboundNo()));
                });
        final List<Inventory> inventories = getInventories(request, order.getProductNos());
        final List<PackagingMaterial> packagingMaterials = packagingMaterialRepository.findAll();
        warehouseRepository.findById(request.warehouseNo)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 창고입니다. 창고 번호: %d".formatted(request.warehouseNo)));

        final Outbound outbound = constructOutbound.create(
                request.warehouseNo,
                inventories,
                packagingMaterials,
                order,
                request.isPriorityDelivery,
                request.desiredDeliveryAt,
                calculateTotalWeight(order.getOrderProducts()),
                calculateTotalVolume(order.getOrderProducts()));

        outboundRepository.save(outbound);
    }

    private List<Inventory> getInventories(final Request request, final Set<Long> productNos) {
        return getInventories(request.warehouseNo, productNos);
    }

    private Long calculateTotalWeight(final List<OrderProduct> orderProducts) {
        Long totalWeight = 0L;
        for (final OrderProduct orderProduct : orderProducts) {
            final Product product = productClient.getBy(orderProduct.getProductNo());
            totalWeight += product.getWeightInGrams() * orderProduct.getOrderQuantity();
        }
        return totalWeight;
    }

    private Long calculateTotalVolume(final List<OrderProduct> orderProducts) {
        Long totalVolume = 0L;
        for (final OrderProduct orderProduct : orderProducts) {
            final Product product = productClient.getBy(orderProduct.getProductNo());
            totalVolume += product.volume() * orderProduct.getOrderQuantity();
        }
        return totalVolume;
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
