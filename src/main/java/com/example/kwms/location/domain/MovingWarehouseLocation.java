package com.example.kwms.location.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.util.List;

//@Entity
//@Table(name = "moving_warehouse_location")
@Comment("창고간 로케이션 이동")
public class MovingWarehouseLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moving_warehouse_location_no")
    @Comment("창고간 로케이션 이동 번호")
    private Long movingWarehouseLocationNo;
    @Column(name = "warehouse_no", nullable = false)
    @Comment("창고 번호")
    private final Long warehouseNo;

    @OneToMany(mappedBy = "movingWarehouseLocation")
    private final List<Location> locations;

    public MovingWarehouseLocation(
            final Long warehouseNo,
            final List<Location> locations) {
        Assert.notNull(warehouseNo, "창고 번호는 필수입니다.");
        Assert.notEmpty(locations, "이동할 로케이션은 필수입니다.");
        this.warehouseNo = warehouseNo;
        this.locations = locations;
    }
}
