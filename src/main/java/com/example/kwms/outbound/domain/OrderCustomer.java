package com.example.kwms.outbound.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCustomer {
    private String name;
    private String email;
    private String phone;
    private String zipNo;
    private String address;

    public OrderCustomer(
            final String name,
            final String email,
            final String phone,
            final String zipNo,
            final String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.zipNo = zipNo;
        this.address = address;
    }
}
