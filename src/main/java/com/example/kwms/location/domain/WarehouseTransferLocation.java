package com.example.kwms.location.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Entity
@Table(name = "warehouse_transfer_location")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class WarehouseTransferLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_transfer_location_no")
    @Comment("재고이동 로케이션 번호")
    private Long workTransferLocationNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_transfer_no", nullable = false)
    @Comment("창고간 재고이동 번호")
    private WarehouseTransfer warehouseTransfer;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_no", nullable = false)
    @Comment("로케이션 번호")
    private Location location;
    @Enumerated(EnumType.STRING)
    @Column(name = "before_usage_purpose", nullable = false)
    @Comment("이전 로케이션 용도")
    private UsagePurpose beforeUsagePurpose;

    WarehouseTransferLocation(
            final WarehouseTransfer warehouseTransfer,
            final Location location,
            final UsagePurpose usagePurpose) {
        Assert.notNull(warehouseTransfer, "창고간 재고이동은 필수입니다.");
        Assert.notNull(location, "로케이션은 필수입니다.");
        Assert.notNull(usagePurpose, "로케이션 용도는 필수입니다.");
        this.warehouseTransfer = warehouseTransfer;
        this.location = location;
        beforeUsagePurpose = usagePurpose;
    }

}
