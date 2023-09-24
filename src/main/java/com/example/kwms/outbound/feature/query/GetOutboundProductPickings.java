package com.example.kwms.outbound.feature.query;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.StorageType;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundProduct;
import com.example.kwms.outbound.domain.OutboundRepository;
import com.example.kwms.outbound.domain.Picking;
import com.example.kwms.outbound.domain.Product;
import com.example.kwms.outbound.feature.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetOutboundProductPickings {
    private final OutboundRepository outboundRepository;
    private final ProductClient productClient;

    @GetMapping("/outbounds/{outboundNo}/outbound-products/{outboundProductNo}/pickings")
    @Transactional(readOnly = true)
    public Response findBy(
            @PathVariable final Long outboundNo,
            @PathVariable final Long outboundProductNo) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);
        final OutboundProduct outboundProduct = getOutboundProduct(outboundProductNo, outbound);
        final Long productNo = outboundProduct.getProductNo();
        final Product product = productClient.getBy(productNo);
        final String productName = product.getProductName();
        final String status = outboundProduct.isPicked() ? "집품 완료" : "집품 중";

        final List<Picking> pickings = outboundProduct.getPickings();
        final List<Response.Product> products = new ArrayList<>();
        for (final Picking picking : pickings) {
            final Inventory inventory = picking.getInventory();
            final String locationBarcode = inventory.getLocationBarcode();
            final Location location = inventory.getLocation();
            final StorageType locationStorageType = location.getStorageType();
            final String lpnBarcode = inventory.getLpn().getLpnBarcode();
            final Long quantityRequiredForPick = picking.getQuantityRequiredForPick();
            final Long pickedQuantity = picking.getPickedQuantity();
            final Response.Product product1 = new Response.Product(
                    locationBarcode,
                    locationStorageType,
                    lpnBarcode,
                    quantityRequiredForPick,
                    pickedQuantity);
            products.add(product1);
        }
        return new Response(
                outboundNo,
                outboundProductNo,
                productNo,
                productName,
                status,
                products);
    }

    private OutboundProduct getOutboundProduct(final Long outboundProductNo, final Outbound outbound) {
        return outbound.getOutboundProducts().stream()
                .filter(op -> op.getOutboundProductNo().equals(outboundProductNo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("해당 출고 상품이 없습니다. 출고상품번호: " + outboundProductNo));
    }

    private record Response(Long outboundNo, Long outboundProductNo, Long productNo, String productName, String status,
                            List<Product> products) {
        record Product(String locationBarcode, StorageType locationStorageType, String lpnBarcode,
                       Long quantityRequiredForPick, Long pickedQuantity) {

        }
    }

}
