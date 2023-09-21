package com.example.kwms.outbound.feature.query;

import com.example.kwms.location.domain.WarehouseRepository;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundProduct;
import com.example.kwms.outbound.domain.OutboundRepository;
import com.example.kwms.outbound.domain.PackagingMaterial;
import com.example.kwms.outbound.domain.Product;
import com.example.kwms.outbound.feature.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetOutbound {
    private final OutboundRepository outboundRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductClient productClient;

    @GetMapping("/outbounds/{outboundNo}")
    @Transactional(readOnly = true)
    public Response findBy(
            @PathVariable final Long outboundNo) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);
            final Long orderNo = outbound.getOrderNo();
            final LocalDate desiredDeliveryAt = outbound.getDesiredDeliveryAt();
            final String warehouseName = warehouseRepository.getBy(outbound.getWarehouseNo()).getName();
            final String trackingNumber = StringUtils.hasText(outbound.getTrackingNumber()) ? outbound.getTrackingNumber() : "송장 발행전";
            final String priorityType = outbound.getIsPriorityDelivery() ? "긴급" : "일반";
        final PackagingMaterial recommendedPackagingMaterial = outbound.getRecommendedPackagingMaterial();
        final String recommendedPackagingMaterialName = null == recommendedPackagingMaterial ? "없음" : recommendedPackagingMaterial.getName();
        final PackagingMaterial realPackagingMaterial = outbound.getRealPackagingMaterial();
        final String realPackagingMaterialName = null == realPackagingMaterial ? "없음" : realPackagingMaterial.getName();
        final LocalDateTime pickedAt = outbound.getPickedAt();
        final LocalDateTime inspectedAt = outbound.getInspectedAt();
        final LocalDateTime packedAt = outbound.getPackedAt();
        final LocalDateTime canceledAt = outbound.getCanceledAt();
        final String cancelReason = outbound.getCancelReason();

        final String status = getStatus(outbound);
        final List<OutboundProduct> outboundProducts = outbound.getOutboundProducts();
        final List<Response.Product> products = new ArrayList<>();
        for (final OutboundProduct outboundProduct : outboundProducts) {
            final Long outboundProductNo = outboundProduct.getOutboundProductNo();
            final Product product = productClient.getBy(outboundProduct.getProductNo());
            final Long productNo = product.getProductNo();
            final String productName = product.getProductName();
            final Long quantity = outboundProduct.getQuantity();
            final Response.Product responseProduct = new Response.Product(
                    outboundProductNo,
                    productNo,
                    productName,
                    quantity
            );
            products.add(responseProduct);
        }
        final int skuQuantity = outboundProducts.size();
        final Long orderQuantity = outboundProducts.stream()
                    .mapToLong(outboundProduct -> outboundProduct.getQuantity())
                    .sum();
            final Response response = new Response(
                    outboundNo,
                    orderNo,
                    desiredDeliveryAt,
                    warehouseName,
                    trackingNumber,
                    priorityType,
                    status,
                    (long) skuQuantity,
                    orderQuantity,
                    recommendedPackagingMaterialName,
                    realPackagingMaterialName,
                    pickedAt,
                    inspectedAt,
                    packedAt,
                    canceledAt,
                    cancelReason,
                    products
            );
        return response;
    }

    private String getStatus(final Outbound outbound) {
        String status = "";
        if (outbound.isReady()) {
            status = "대기";
        } else if (!outbound.getPickings().isEmpty() && !outbound.isCanceled() && !outbound.isInspected() && !outbound.isPacked()) {
            status = "집품 중";
        } else if (outbound.isPicked() && !outbound.isCanceled() && !outbound.isInspected() && !outbound.isPacked()) {
            status = "집품 완료";
        } else if (outbound.isCanceled()) {
            status = "출고 중지";
        } else if (!outbound.isCanceled() && outbound.isInspected() && !outbound.isPacked()) {
            status = "검수 완료";
        } else if (!outbound.isCanceled() && outbound.isPacked() && null == outbound.getTrackingNumber()) {
            status = "포장 완료(송장 발행전)";
        } else if (!outbound.isCanceled() && outbound.isPacked() && null != outbound.getTrackingNumber()) {
            status = "포장 완료(송장 발행 완료)";
        }
        return status;
    }

    private record Response(
            Long outboundNo,
            Long orderNo,
            LocalDate desiredDeliveryAt,
            String warehouseName,
            String trackingNumber,
            String priorityType,
            String status,
            Long skuQuantity,
            Long orderQuantity,
            String recommendedPackagingMaterialName,
            String realPackagingMaterialName,
            LocalDateTime pickedAt,
            LocalDateTime inspectedAt,
            LocalDateTime packedAt,
            LocalDateTime canceledAt,
            String cancelReason,
            List<Product> products) {

        public record Product(
                Long outboundProductNo,
                Long productNo,
                String productName,
                Long quantity) {
        }
    }
}
