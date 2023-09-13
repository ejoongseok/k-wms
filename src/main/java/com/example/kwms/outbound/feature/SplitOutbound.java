package com.example.kwms.outbound.feature;

import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundProduct;
import com.example.kwms.outbound.domain.OutboundRepository;
import com.example.kwms.outbound.domain.OutboundSplitter;
import com.example.kwms.outbound.domain.PackagingMaterial;
import com.example.kwms.outbound.domain.PackagingMaterialRepository;
import com.example.kwms.outbound.domain.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SplitOutbound {
    private final OutboundSplitter outboundSplitter = new OutboundSplitter();
    private final OutboundRepository outboundRepository;
    private final ProductClient productClient;
    private final PackagingMaterialRepository packagingMaterialRepository;

    @PostMapping("/outbounds/{outboundNo}/split")
    @Transactional
    public void request(
            @PathVariable final Long outboundNo,
            @RequestBody @Valid final Request request) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);
        final List<PackagingMaterial> packagingMaterials = packagingMaterialRepository.findAll();
        final List<OutboundProduct> targets = createTargetProducts(outbound, request.targets());
        final List<Product> products = getProducts(outbound.getOutboundProducts());

        final Outbound split = outboundSplitter.split(outbound, targets, packagingMaterials, products);

        outboundRepository.save(split);
    }

    private List<OutboundProduct> createTargetProducts(final Outbound outbound, final List<Request.Product> targets) {
        return targets.stream()
                .map(target -> outbound.createOutboundProductForSplit(target.productNo(), target.quantity()))
                .toList();
    }

    private List<Product> getProducts(final List<OutboundProduct> outboundProducts) {
        return outboundProducts.stream()
                .map(OutboundProduct::getProductNo)
                .map(productClient::getBy)
                .collect(Collectors.toList());
    }

    public record Request(
            @NotEmpty(message = "상품이 없습니다.")
            List<Request.Product> targets) {
        public record Product(
                @NotNull(message = "상품번호가 없습니다.")
                Long productNo,
                @NotNull(message = "수량이 없습니다.")
                @Min(value = 1, message = "수량이 1보다 작습니다.")
                Long quantity) {
        }
    }
}
