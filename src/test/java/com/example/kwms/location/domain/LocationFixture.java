package com.example.kwms.location.domain;

public class LocationFixture {

    private final Long warehouseNo = 1L;
    private String locationBarcode = "TOTE-001";
    private StorageType storageType = StorageType.TOTE;
    private UsagePurpose usagePurpose = UsagePurpose.MOVE;

    public static LocationFixture aLocation() {
        return new LocationFixture();
    }

    public LocationFixture locationBarcode(final String locationBarcode) {
        this.locationBarcode = locationBarcode;
        return this;
    }

    public LocationFixture storageType(final StorageType storageType) {
        this.storageType = storageType;
        return this;
    }

    public LocationFixture usagePurpose(final UsagePurpose usagePurpose) {
        this.usagePurpose = usagePurpose;
        return this;
    }

    public Location build() {
        return new Location(
                warehouseNo,
                locationBarcode,
                storageType,
                usagePurpose
        );
    }
}
