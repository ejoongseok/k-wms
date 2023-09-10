package com.example.kwms.location.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Entity
@Table(name = "warehouse")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("창고 번호")
    @Column(name = "warehouse_no")
    private Long warehouseNo;
    @Comment("창고명")
    @Column(name = "name", nullable = false)
    private String name;
    @Comment("주소")
    @Column(name = "address", nullable = false)
    private String address;
    @Comment("전화번호")
    @Column(name = "tel_number", nullable = false)
    private String telNumber;
    @Comment("담당자명")
    @Column(name = "manager_name", nullable = false)
    private String managerName;
    @Comment("담당자 연락처")
    @Column(name = "manager_tel_number", nullable = false)
    private String managerTelNumber;
    @Comment("비고")
    @Column(name = "description")
    private String description;

    public Warehouse(
            final String name,
            final String address,
            final String telNumber,
            final String managerName,
            final String managerTelNumber,
            final String description) {
        validateWarehouse(name, address, telNumber, managerName, managerTelNumber);
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.managerName = managerName;
        this.managerTelNumber = managerTelNumber;
        this.description = description;
    }

    private void validateWarehouse(
            final String name,
            final String address,
            final String telNumber,
            final String managerName,
            final String managerTelNumber) {
        Assert.hasText(name, "창고명은 필수입니다.");
        Assert.hasText(address, "주소는 필수입니다.");
        Assert.hasText(telNumber, "전화번호는 필수입니다.");
        Assert.hasText(managerName, "담당자명은 필수입니다.");
        Assert.hasText(managerTelNumber, "담당자 연락처는 필수입니다.");
    }
}
