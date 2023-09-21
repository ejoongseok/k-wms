package com.example.kwms.common;

import com.example.kwms.inbound.feature.api.AddReceiveApi;
import com.example.kwms.inbound.feature.api.CreateLPNApi;
import com.example.kwms.inbound.feature.api.CreatePurchaseOrderApi;
import com.example.kwms.inbound.feature.api.UpdatePurchaseOrderApi;
import com.example.kwms.location.feature.api.AddInventoryApi;
import com.example.kwms.location.feature.api.AddManualInventoryApi;
import com.example.kwms.location.feature.api.AddWarehouseTransferLocationApi;
import com.example.kwms.location.feature.api.AdjustInventoryApi;
import com.example.kwms.location.feature.api.AppendLocationApi;
import com.example.kwms.location.feature.api.CreateLocationApi;
import com.example.kwms.location.feature.api.CreateWarehouseApi;
import com.example.kwms.location.feature.api.CreateWarehouseTransferApi;
import com.example.kwms.location.feature.api.ReceiveWarehouseTransferApi;
import com.example.kwms.location.feature.api.ShipmentWarehouseTransferApi;
import com.example.kwms.location.feature.api.TransferInventoryApi;
import com.example.kwms.location.feature.api.UpdateLocationApi;
import com.example.kwms.location.feature.api.UpdateLocationBarcodeApi;
import com.example.kwms.location.feature.api.UpdateLocationUsagePurposeApi;
import com.example.kwms.location.feature.api.UpdateWarehouseApi;
import com.example.kwms.location.feature.api.UpdateWarehouseTransferApi;
import com.example.kwms.location.feature.api.UpdateWarehouseTransferProductApi;
import com.example.kwms.outbound.feature.api.AllocatePickerApi;
import com.example.kwms.outbound.feature.api.AllocatePickingApi;
import com.example.kwms.outbound.feature.api.AllocatePickingToteApi;
import com.example.kwms.outbound.feature.api.CancelOutboundApi;
import com.example.kwms.outbound.feature.api.CreateBulkOutboundApi;
import com.example.kwms.outbound.feature.api.CreateOutboundApi;
import com.example.kwms.outbound.feature.api.CreatePackagingMaterialApi;
import com.example.kwms.outbound.feature.api.InspectedBulkOutboundApi;
import com.example.kwms.outbound.feature.api.InspectedOutboundApi;
import com.example.kwms.outbound.feature.api.ManualOutboundApi;
import com.example.kwms.outbound.feature.api.PackedBulkOutboundApi;
import com.example.kwms.outbound.feature.api.PackedOutboundApi;
import com.example.kwms.outbound.feature.api.PickingBulkOutboundApi;
import com.example.kwms.outbound.feature.api.PopBulkOutboundApi;
import com.example.kwms.outbound.feature.api.ResetOutboundApi;
import com.example.kwms.outbound.feature.api.ScanToPickApi;
import com.example.kwms.outbound.feature.api.ScanToPickManualApi;
import com.example.kwms.outbound.feature.api.SplitOutboundApi;
import com.example.kwms.outbound.feature.api.TransferOutboundApi;
import com.example.kwms.outbound.feature.api.UpdatePackagingMaterialApi;

public class Scenario {
    public static CreatePurchaseOrderApi createPurchaseOrder() {
        return new CreatePurchaseOrderApi();
    }

    public static UpdatePurchaseOrderApi updatePurchaseOrder() {
        return new UpdatePurchaseOrderApi();
    }

    public static AddReceiveApi addReceive() {
        return new AddReceiveApi();
    }

    public static CreateLPNApi createLPN() {
        return new CreateLPNApi();
    }

    public static CreateLocationApi createLocation() {
        return new CreateLocationApi();
    }

    public static AppendLocationApi appendLocation() {
        return new AppendLocationApi();
    }

    public static AddInventoryApi addInventory() {
        return new AddInventoryApi();
    }

    public static AddManualInventoryApi addManualInventory() {
        return new AddManualInventoryApi();
    }

    public static AdjustInventoryApi adjustInventory() {
        return new AdjustInventoryApi();
    }

    public static TransferInventoryApi transferInventory() {
        return new TransferInventoryApi();
    }

    public static CreateWarehouseApi createWarehouse() {
        return new CreateWarehouseApi();
    }

    public static UpdateWarehouseApi updateWarehouse() {
        return new UpdateWarehouseApi();
    }

    public static UpdateLocationApi updateLocation() {
        return new UpdateLocationApi();
    }

    public static CreateWarehouseTransferApi createWarehouseTransfer() {
        return new CreateWarehouseTransferApi();
    }

    public static UpdateWarehouseTransferApi updateWarehouseTransfer() {
        return new UpdateWarehouseTransferApi();
    }

    public static UpdateWarehouseTransferProductApi updateWarehouseTransferProduct() {
        return new UpdateWarehouseTransferProductApi();
    }

    public static AddWarehouseTransferLocationApi addWarehouseTransferLocation() {
        return new AddWarehouseTransferLocationApi();
    }

    public static ShipmentWarehouseTransferApi shipmentWarehouseTransfer() {
        return new ShipmentWarehouseTransferApi();
    }

    public static ReceiveWarehouseTransferApi receiveWarehouseTransfer() {
        return new ReceiveWarehouseTransferApi();
    }

    public static UpdateLocationUsagePurposeApi updateLocationUsagePurpose() {
        return new UpdateLocationUsagePurposeApi();
    }

    public static CreatePackagingMaterialApi createPackagingMaterial() {
        return new CreatePackagingMaterialApi();
    }

    public static UpdatePackagingMaterialApi updatePackagingMaterial() {
        return new UpdatePackagingMaterialApi();
    }

    public static CreateOutboundApi createOutbound() {
        return new CreateOutboundApi();
    }

    public static SplitOutboundApi splitOutbound() {
        return new SplitOutboundApi();
    }

    public static AllocatePickingToteApi allocatePickingTote() {
        return new AllocatePickingToteApi();
    }

    public static CancelOutboundApi cancelOutbound() {
        return new CancelOutboundApi();
    }

    public static TransferOutboundApi transferOutbound() {
        return new TransferOutboundApi();
    }

    public static AllocatePickingApi allocatePicking() {
        return new AllocatePickingApi();
    }

    public static ScanToPickApi scanToPick() {
        return new ScanToPickApi();
    }

    public static ScanToPickManualApi scanToPickManual() {
        return new ScanToPickManualApi();
    }

    public static InspectedOutboundApi inspectedOutbound() {
        return new InspectedOutboundApi();
    }

    public static PackedOutboundApi packedOutbound() {
        return new PackedOutboundApi();
    }

    public static ResetOutboundApi resetOutbound() {
        return new ResetOutboundApi();
    }

    public static CreateBulkOutboundApi createBulkOutbound() {
        return new CreateBulkOutboundApi();
    }

    public static PickingBulkOutboundApi pickingBulkOutbound() {
        return new PickingBulkOutboundApi();
    }

    public static InspectedBulkOutboundApi inspectedBulkOutbound() {
        return new InspectedBulkOutboundApi();
    }

    public static PopBulkOutboundApi popBulkOutbound() {
        return new PopBulkOutboundApi();
    }

    public static PackedBulkOutboundApi packedBulkOutbound() {
        return new PackedBulkOutboundApi();
    }

    public static AllocatePickerApi allocatePicker() {
        return new AllocatePickerApi();
    }

    public static UpdateLocationBarcodeApi updateLocationBarcode() {
        return new UpdateLocationBarcodeApi();
    }

    public static ManualOutboundApi manualOutbound() {
        return new ManualOutboundApi();
    }
}
