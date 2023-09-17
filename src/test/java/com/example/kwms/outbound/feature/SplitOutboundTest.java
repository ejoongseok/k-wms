package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.feature.command.AddReceive;
import com.example.kwms.inbound.feature.command.CreatePurchaseOrder;
import com.example.kwms.outbound.domain.Order;
import com.example.kwms.outbound.domain.OrderCustomer;
import com.example.kwms.outbound.domain.OrderProduct;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SplitOutboundTest extends ApiTest {

    @Autowired
    private SplitOutbound splitOutbound;
    @MockBean
    private OrderClient orderClient;
    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void splitOutboundSetUp() {
        Scenario.createWarehouse().request();
        Mockito.when(orderClient.getBy(ArgumentMatchers.anyLong()))
                .thenReturn(
                        new Order(
                                1L,
                                new OrderCustomer(
                                        "name",
                                        "email",
                                        "phone",
                                        "zipNo",
                                        "address"
                                ),
                                "배송 요구사항",
                                List.of(
                                        new OrderProduct(
                                                1L,
                                                1L,
                                                1500L),
                                        new OrderProduct(
                                                2L,
                                                1L,
                                                1500L)
                                ))
                );
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 10L;
        Scenario.createLocation().locationBarcode(locationBarcode).request()
                .createPurchaseOrder().products(
                        new CreatePurchaseOrder.Request.Product(
                                1L,
                                2_000L,
                                15_000L,
                                "블랙핑크 1집 앨범[]"
                        ),
                        new CreatePurchaseOrder.Request.Product(
                                2L,
                                2_000L,
                                15_000L,
                                "블랙핑크 2집 앨범[]"
                        )
                ).request()
                .addReceive()
                .receives(
                        new AddReceive.Request.ReceiveRequest(
                                1L,
                                "블랙핑크 1집 앨범[]",
                                10L,
                                0L
                        ),
                        new AddReceive.Request.ReceiveRequest(
                                2L,
                                "블랙핑크 2집 앨범[]",
                                10L,
                                0L
                        ))
                .request()
                .createLPN().purchaseOrderProductNo(1L).lpnBarcode("LPN-001").request()
                .createLPN().purchaseOrderProductNo(2L).lpnBarcode("LPN-002").request();
        Scenario.addManualInventory()
                .locationBarcode(locationBarcode)
                .lpnBarcode(lpnBarcode)
                .quantity(quantity)
                .request();
        Scenario.addManualInventory()
                .locationBarcode(locationBarcode)
                .lpnBarcode("LPN-002")
                .quantity(quantity)
                .request();
        Scenario.
                createPackagingMaterial().request()
                .createOutbound().request();
    }

    @Test
    @DisplayName("출고를 분할한다.")
    @Transactional
    void splitOutbound() {
        final Long outboundNo = 1L;

        Scenario.splitOutbound().outboundNo(outboundNo).request();

        assertSplit(outboundNo);
    }

    private void assertSplit(final Long outboundNo) {
        final Outbound refresh = outboundRepository.getBy(outboundNo);
        assertThat(refresh.getOutboundProducts()).hasSize(1);
        assertThat(refresh.getOutboundProducts().get(0).getProductNo()).isEqualTo(2);
        assertThat(refresh.getRecommendedPackagingMaterial()).isNotNull();
        final Outbound splitted = outboundRepository.getBy(2L);
        assertThat(splitted.getOutboundProducts().get(0).getProductNo()).isEqualTo(1L);
        assertThat(splitted.getOutboundProducts()).hasSize(1);
        assertThat(splitted.getRecommendedPackagingMaterial()).isNotNull();
    }

}
