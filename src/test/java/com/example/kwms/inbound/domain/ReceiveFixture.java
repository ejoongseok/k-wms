package com.example.kwms.inbound.domain;

import java.util.Arrays;
import java.util.List;

import static com.example.kwms.inbound.domain.ReceiveProductFixture.aReceiveProduct;

public class ReceiveFixture {

    private String name = "입고 명";
    private List<ReceiveProductFixture> receiveProducts = List.of(aReceiveProduct());

    static ReceiveFixture aReceive() {
        return new ReceiveFixture();
    }

    public ReceiveFixture name(final String name) {
        this.name = name;
        return this;
    }

    public ReceiveFixture receiveProducts(final ReceiveProductFixture... receiveProductList) {
        receiveProducts = Arrays.asList(receiveProductList);
        return this;
    }

    public Receive build() {
        return new Receive(
                name,
                buildReceiveProducts());
    }

    private List<ReceiveProduct> buildReceiveProducts() {
        return receiveProducts.stream()
                .map(ReceiveProductFixture::build)
                .toList();
    }


}