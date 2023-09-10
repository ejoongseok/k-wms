package com.example.kwms.location.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Entity
@Table(name = "location")
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_no")
    @Comment("로케이션 번호")
    private Long locationNo;
    @Column(name = "location_barcode", nullable = false, unique = true)
    @Comment("로케이션 바코드")
    private String locationBarcode;
    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", nullable = false)
    @Comment("로케이션 유형")
    private StorageType storageType;
    @Enumerated(EnumType.STRING)
    @Column(name = "usage_purpose", nullable = false)
    @Comment("로케이션 용도")
    private UsagePurpose usagePurpose;

    public Location(
            final String locationBarcode,
            final StorageType storageType,
            final UsagePurpose usagePurpose) {
        Assert.hasText(locationBarcode, "로케이션 바코드는 필수입니다.");
        Assert.notNull(storageType, "로케이션 유형은 필수입니다.");
        Assert.notNull(usagePurpose, "로케이션 용도는 필수입니다.");
        this.locationBarcode = locationBarcode;
        this.storageType = storageType;
        this.usagePurpose = usagePurpose;

    }
}
