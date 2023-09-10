package com.example.kwms.location.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateWarehouseTest {

    private CreateWarehouse createWarehouse;

    @BeforeEach
    void setUp() {
        createWarehouse = new CreateWarehouse();
    }

    @Test
    @DisplayName("창고를 생성한다.")
    void createWarehouse() {
        final CreateWarehouse.Request request = new CreateWarehouse.Request();
        createWarehouse.request(request);
    }

    private class CreateWarehouse {
        public void request(final Request request) {
            throw new UnsupportedOperationException("Unsupported request");
        }

        public record Request() {
        }
    }
}
