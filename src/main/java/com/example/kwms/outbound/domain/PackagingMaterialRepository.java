package com.example.kwms.outbound.domain;

import com.example.kwms.common.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PackagingMaterialRepository extends JpaRepository<PackagingMaterial, Long> {
    default PackagingMaterial getBy(final Long packagingMaterialNo) {
        return findById(packagingMaterialNo)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 포장재입니다. 포장재번호 :%d".formatted(packagingMaterialNo)));
    }

    @Query("select pm from PackagingMaterial pm where pm.code = :code")
    Optional<PackagingMaterial> findByCode(String code);

    default PackagingMaterial getBy(final String packagingMaterialCode) {
        return findByCode(packagingMaterialCode)
                .orElseThrow(() -> new NotFoundException(
                        "존재하지 않는 포장재입니다. 포장재 바코드 :%s"
                                .formatted(packagingMaterialCode)));
    }
}
