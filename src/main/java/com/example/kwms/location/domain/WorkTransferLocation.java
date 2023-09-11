package com.example.kwms.location.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Entity
@Table(name = "work_transfer_location")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class WorkTransferLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_transfer_location_no")
    @Comment("작업 재고이동 로케이션 번호")
    private Long workTransferLocationNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_transfer_no", nullable = false)
    @Comment("창고간 재고이동 번호")
    private WarehouseTransfer warehouseTransfer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_no", nullable = false)
    @Comment("로케이션 번호")
    private Location location;

    WorkTransferLocation(
            final WarehouseTransfer warehouseTransfer,
            final Location location) {
        Assert.notNull(warehouseTransfer, "창고간 재고이동은 필수입니다.");
        Assert.notNull(location, "로케이션은 필수입니다.");
        this.warehouseTransfer = warehouseTransfer;
        this.location = location;
    }
}
