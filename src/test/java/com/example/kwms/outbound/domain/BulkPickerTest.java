package com.example.kwms.outbound.domain;

import com.example.kwms.location.domain.Inventory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.kwms.location.domain.InventoryFixture.anInventory;
import static com.example.kwms.outbound.domain.OutboundFixture.anOutbound;
import static org.assertj.core.api.Assertions.assertThat;

class BulkPickerTest {

    @Test
    void picking() {
        final BulkPicker bulkPicker = new BulkPicker();
        final List<Outbound> outbounds = new ArrayList<>();
        final Outbound outbound = anOutbound().outboundNo(1L).build();
        final Outbound outbound1 = anOutbound().outboundNo(2L).build();
        outbounds.add(outbound);
        outbounds.add(outbound1);
        final BulkOutbound bulkOutbound = new BulkOutbound(outbounds);
        final Inventory inventory = anInventory().quantity(2L).build();
        final DecreaseTarget decreaseTarget = new DecreaseTarget(inventory, 1L);

        bulkPicker.picking(bulkOutbound, List.of(decreaseTarget));
        bulkPicker.picking(bulkOutbound, List.of(decreaseTarget));

        assertThat(outbound.isPicked()).isEqualTo(true);
        assertThat(outbound1.isPicked()).isEqualTo(true);
        assertThat(inventory.getQuantity()).isEqualTo(0L);
        assertThat(bulkOutbound.getPickedAt()).isNotNull();
    }
}