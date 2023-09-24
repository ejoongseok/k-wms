insert into warehouse (address, description, manager_name, manager_tel_number, name, tel_number, warehouse_no)
values ('인청광역시 원창동', '인천 GDC', '홍길동', '010-1234-1234', '상온 B2C창고', '010-1234-1234', default);
insert into warehouse (address, description, manager_name, manager_tel_number, name, tel_number, warehouse_no)
values ('서울 코엑스', '코엑스 매장', '임꺽정', '010-2345-1234', '코엑스 매장', '010-2345-1234', default);

insert into purchase_order (description, title, warehouse_no, purchase_order_no)
values ('테스트용 상품 발주', '테스트용 발주', 1, default);
insert into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price,
                                    purchase_order_product_no)
values ('테스트[]', 1, 1, 10, 1500, default);
insert into receive (created_at, name, purchase_order_no, receive_no)
values (now(), '테스트 상품 10개 입고', 1, default);
insert into receive_product (acceptable_quantity, inspected_at, inspection_comment, product_no, receive_no,
                             rejected_quantity, receive_product_no)
values (10, now(), '9/18 전체 입고됨', 10, 1, 0, default);
insert into lpn (expiring_at,lpn_barcode,purchase_order_product_no,lpn_no) values ('2057-12-18','BP000001',1,default);
insert into purchase_order (description, title, warehouse_no, purchase_order_no)
values ('테스트용 상품 발주2', '테스트용 발주2', 1, default);
insert into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price,
                                    purchase_order_product_no)
values ('테스트[]', 2, 2, 4, 1500, default);
insert into receive (created_at, name, purchase_order_no, receive_no)
values (now(), '테스트 상품 4개 입고', 2, default);
insert into receive_product (acceptable_quantity, inspected_at, inspection_comment, product_no, receive_no,
                             rejected_quantity, receive_product_no)
values (4, now(), '9/18 전체 입고됨', 2, 2, 0, default);
insert into lpn (expiring_at, lpn_barcode, purchase_order_product_no, lpn_no)
values ('2057-12-18', 'BP000002', 2, default);

insert into purchase_order (description, title, warehouse_no, purchase_order_no)
values ('테스트용 상품 발주3', '테스트용 발주2', 1, default);
insert into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price,
                                    purchase_order_product_no)
values ('테스트[]', 3, 3, 25, 1500, default);
insert into receive (created_at, name, purchase_order_no, receive_no)
values (now(), '테스트 상품 25개 입고', 3, default);
insert into receive_product (acceptable_quantity, inspected_at, inspection_comment, product_no, receive_no,
                             rejected_quantity, receive_product_no)
values (25, now(), '9/18 전체 입고됨', 3, 3, 0, default);
insert into lpn (expiring_at, lpn_barcode, purchase_order_product_no, lpn_no)
values ('2057-12-18', 'BP000003', 3, default);

insert into purchase_order (description, title, warehouse_no, purchase_order_no)
values ('테스트용 상품 발주4', '테스트용 발주4', 1, default);
insert into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price,
                                    purchase_order_product_no)
values ('테스트[]', 4, 4, 13, 1500, default);
insert into receive (created_at, name, purchase_order_no, receive_no)
values (now(), '테스트 상품 13개 입고', 4, default);
insert into receive_product (acceptable_quantity, inspected_at, inspection_comment, product_no, receive_no,
                             rejected_quantity, receive_product_no)
values (13, now(), '9/18 전체 입고됨', 4, 4, 0, default);
insert into lpn (expiring_at, lpn_barcode, purchase_order_product_no, lpn_no)
values ('2057-12-18', 'BP000004', 4, default);

insert into purchase_order (description, title, warehouse_no, purchase_order_no)
values ('테스트용 상품 발주5', '테스트용 발주5', 1, default);
insert into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price,
                                    purchase_order_product_no)
values ('테스트[]', 5, 5, 31, 1500, default);
insert into receive (created_at, name, purchase_order_no, receive_no)
values (now(), '테스트 상품 31개 입고', 5, default);
insert into receive_product (acceptable_quantity, inspected_at, inspection_comment, product_no, receive_no,
                             rejected_quantity, receive_product_no)
values (31, now(), '9/18 전체 입고됨', 5, 5, 0, default);
insert into lpn (expiring_at, lpn_barcode, purchase_order_product_no, lpn_no)
values ('2057-12-18', 'BP000005', 5, default);

insert into purchase_order (description, title, warehouse_no, purchase_order_no)
values ('테스트용 상품 발주6', '테스트용 발주6', 1, default);
insert into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price,
                                    purchase_order_product_no)
values ('테스트[]', 6, 6, 35, 1500, default);
insert into receive (created_at, name, purchase_order_no, receive_no)
values (now(), '테스트 상품 35개 입고', 6, default);
insert into receive_product (acceptable_quantity, inspected_at, inspection_comment, product_no, receive_no,
                             rejected_quantity, receive_product_no)
values (35, now(), '9/18 전체 입고됨', 6, 6, 0, default);
insert into lpn (expiring_at, lpn_barcode, purchase_order_product_no, lpn_no)
values ('2057-12-18', 'BP000006', 6, default);

insert into purchase_order (description, title, warehouse_no, purchase_order_no)
values ('테스트용 상품 발주7', '테스트용 발주7', 1, default);
insert into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price,
                                    purchase_order_product_no)
values ('테스트[]', 7, 7, 30, 1500, default);
insert into receive (created_at, name, purchase_order_no, receive_no)
values (now(), '테스트 상품 30개 입고', 7, default);
insert into receive_product (acceptable_quantity, inspected_at, inspection_comment, product_no, receive_no,
                             rejected_quantity, receive_product_no)
values (30, now(), '9/18 전체 입고됨', 7, 7, 0, default);
insert into lpn (expiring_at, lpn_barcode, purchase_order_product_no, lpn_no)
values ('2057-12-18', 'BP000007', 7, default);

insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('TOTE-001',NULL,'TOTE','MOVE',1,1);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('TOTE-002',NULL,'TOTE','MOVE',1,2);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('TOTE-003',NULL,'TOTE','MOVE',1,3);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('TOTE-004',NULL,'TOTE','MOVE',1,4);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('TOTE-005',NULL,'TOTE','MOVE',1,5);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('TOTE-006',NULL,'TOTE','MOVE',1,6);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('TOTE-007',NULL,'TOTE','MOVE',1,7);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('TOTE-008',NULL,'TOTE','MOVE',1,8);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('TOTE-009',NULL,'TOTE','MOVE',1,9);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('TOTE-010',NULL,'TOTE','MOVE',1,10);

insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('PALLET-001',NULL,'PALLET','MOVE',1,11);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('PALLET-002',NULL,'PALLET','MOVE',1,12);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('PALLET-003',NULL,'PALLET','MOVE',1,13);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('PALLET-004',NULL,'PALLET','MOVE',1,14);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('PALLET-005',NULL,'PALLET','MOVE',1,15);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('PALLET-006',NULL,'PALLET','MOVE',1,16);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('PALLET-007',NULL,'PALLET','MOVE',1,17);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('PALLET-008',NULL,'PALLET','MOVE',1,18);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('PALLET-009',NULL,'PALLET','MOVE',1,19);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('PALLET-010',NULL,'PALLET','MOVE',1,20);

insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A1', NULL, 'RACK', 'DISPLAY', 1, 21);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A2', NULL, 'RACK', 'DISPLAY', 1, 22);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A3', NULL, 'RACK', 'DISPLAY', 1, 23);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A4', NULL, 'RACK', 'DISPLAY', 1, 24);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A5', NULL, 'RACK', 'DISPLAY', 1, 25);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-B1', NULL, 'RACK', 'DISPLAY', 1, 26);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-B2', NULL, 'RACK', 'DISPLAY', 1, 27);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-B3', NULL, 'RACK', 'DISPLAY', 1, 28);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-B4', NULL, 'RACK', 'DISPLAY', 1, 29);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-B5', NULL, 'RACK', 'DISPLAY', 1, 30);

insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A1-1', 21, 'SHELF', 'DISPLAY', 1, 31);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A2-2', 21, 'SHELF', 'DISPLAY', 1, 32);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A3-3', 21, 'SHELF', 'DISPLAY', 1, 33);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A4-4', 21, 'SHELF', 'DISPLAY', 1, 34);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A5-5', 21, 'SHELF', 'DISPLAY', 1, 35);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-B1-1', 26, 'SHELF', 'DISPLAY', 1, 36);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-B2-2', 26, 'SHELF', 'DISPLAY', 1, 37);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-B3-3', 26, 'SHELF', 'DISPLAY', 1, 38);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-B4-4', 26, 'SHELF', 'DISPLAY', 1, 39);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-B5-5', 26, 'SHELF', 'DISPLAY', 1, 40);

insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A1-1-1', 31, 'BIN', 'DISPLAY', 1, 41);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A1-1-2', 31, 'BIN', 'DISPLAY', 1, 42);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A1-1-3', 31, 'BIN', 'DISPLAY', 1, 43);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A1-1-4', 31, 'BIN', 'DISPLAY', 1, 44);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A1-1-5', 31, 'BIN', 'DISPLAY', 1, 45);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A1-1-6', 31, 'BIN', 'DISPLAY', 1, 46);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A1-1-7', 31, 'BIN', 'DISPLAY', 1, 47);
insert into location (location_barcode, parent_location_no, storage_type, usage_purpose, warehouse_no, location_no)
values ('GDC-A1-1-8', 31, 'BIN', 'DISPLAY', 1, 48);

ALTER TABLE location ALTER COLUMN location_no RESTART WITH 50;

insert into inventory (location_no, lpn_no, product_no, quantity, warehouse_no, inventory_no)
values (45, 1, 1, 10, 1, default);
insert into inventory (location_no, lpn_no, product_no, quantity, warehouse_no, inventory_no)
values (46, 2, 2, 4, 1, default);
insert into inventory (location_no, lpn_no, product_no, quantity, warehouse_no, inventory_no)
values (47, 3, 3, 25, 1, default);
insert into inventory (location_no, lpn_no, product_no, quantity, warehouse_no, inventory_no)
values (48, 4, 4, 13, 1, default);
insert into inventory (location_no, lpn_no, product_no, quantity, warehouse_no, inventory_no)
values (48, 5, 5, 31, 1, default);
insert into inventory (location_no, lpn_no, product_no, quantity, warehouse_no, inventory_no)
values (48, 6, 6, 35, 1, default);
insert into inventory (location_no, lpn_no, product_no, quantity, warehouse_no, inventory_no)
values (48, 7, 7, 30, 1, default);

insert into warehouse_transfer (barcode, from_warehouse_no, received_at, shipped_at, to_warehouse_no,
                                warehouse_transfer_no)
values ('GB202303070100001', 1, NULL, NULL, 2, default);
insert into warehouse_transfer_product (product_no, quantity, warehouse_transfer_no, warehouse_transfer_product_no)
values (1, 1, 1, default);

insert into packaging_material (code, material_type, max_weight_in_grams, name, inner_height, inner_length, inner_width,
                                outer_height, outer_length, outer_width, weight_in_grams, packaging_material_no)
values ('N330023080', 'CORRUGATED_BOX', 5000, '3호', 220, 70, 290, 230, 80, 300, 250, default);
insert into packaging_material (code, material_type, max_weight_in_grams, name, inner_height, inner_length, inner_width,
                                outer_height, outer_length, outer_width, weight_in_grams, packaging_material_no)
values ('N5300230150', 'CORRUGATED_BOX', 5000, '5호', 220, 140, 290, 230, 150, 300, 250, default);
insert into packaging_material (code, material_type, max_weight_in_grams, name, inner_height, inner_length, inner_width,
                                outer_height, outer_length, outer_width, weight_in_grams, packaging_material_no)
values ('NO.13', 'CORRUGATED_BOX', 5000, 'J7', 215, 145, 615, 225, 155, 625, 390, default);
insert into outbound (box_height_in_millimeters, box_length_in_millimeters, box_width_in_millimeters, bulk_outbound_no,
                      cancel_reason, canceled_at, desired_delivery_at, inspected_at, is_priority_delivery, order_no,
                      packaged_weight_in_grams, packed_at, picked_at, picker_no, picking_tote_no,
                      real_packaging_material_no, packaging_material_no, tracking_number, warehouse_no, outbound_no,
                      is_manual_outbound)
values (NULL, NULL, NULL, NULL, NULL, NULL, '2021-09-30', NULL, true, 6, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 1,
        default, false);
insert into outbound (box_height_in_millimeters, box_length_in_millimeters, box_width_in_millimeters, bulk_outbound_no,
                      cancel_reason, canceled_at, desired_delivery_at, inspected_at, is_priority_delivery, order_no,
                      packaged_weight_in_grams, packed_at, picked_at, picker_no, picking_tote_no,
                      real_packaging_material_no, packaging_material_no, tracking_number, warehouse_no, outbound_no,
                      is_manual_outbound)
values (NULL, NULL, NULL, NULL, NULL, NULL, '2021-09-30', NULL, true, 4, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 1,
        default, false);
insert into outbound (box_height_in_millimeters, box_length_in_millimeters, box_width_in_millimeters, bulk_outbound_no,
                      cancel_reason, canceled_at, desired_delivery_at, inspected_at, is_priority_delivery, order_no,
                      packaged_weight_in_grams, packed_at, picked_at, picker_no, picking_tote_no,
                      real_packaging_material_no, packaging_material_no, tracking_number, warehouse_no, outbound_no,
                      is_manual_outbound)
values (NULL, NULL, NULL, NULL, NULL, NULL, '2021-09-30', NULL, true, 3, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 1,
        default, false);
insert into outbound (box_height_in_millimeters, box_length_in_millimeters, box_width_in_millimeters, bulk_outbound_no,
                      cancel_reason, canceled_at, desired_delivery_at, inspected_at, is_priority_delivery, order_no,
                      packaged_weight_in_grams, packed_at, picked_at, picker_no, picking_tote_no,
                      real_packaging_material_no, packaging_material_no, tracking_number, warehouse_no, outbound_no,
                      is_manual_outbound)
values (NULL, NULL, NULL, NULL, NULL, NULL, '2021-09-30', NULL, true, 2, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 1,
        default, false);
insert into outbound (box_height_in_millimeters, box_length_in_millimeters, box_width_in_millimeters, bulk_outbound_no,
                      cancel_reason, canceled_at, desired_delivery_at, inspected_at, is_priority_delivery, order_no,
                      packaged_weight_in_grams, packed_at, picked_at, picker_no, picking_tote_no,
                      real_packaging_material_no, packaging_material_no, tracking_number, warehouse_no, outbound_no,
                      is_manual_outbound)
values (NULL, NULL, NULL, NULL, NULL, NULL, '2021-09-30', NULL, true, 1, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 1,
        default, false);
insert into outbound (box_height_in_millimeters, box_length_in_millimeters, box_width_in_millimeters, bulk_outbound_no,
                      cancel_reason, canceled_at, desired_delivery_at, inspected_at, is_priority_delivery, order_no,
                      packaged_weight_in_grams, packed_at, picked_at, picker_no, picking_tote_no,
                      real_packaging_material_no, packaging_material_no, tracking_number, warehouse_no, outbound_no,
                      is_manual_outbound)
values (NULL, NULL, NULL, NULL, NULL, NULL, '2021-09-30', NULL, true, 5, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 1,
        default, false);
insert into outbound_product (outbound_no, picked_at, product_no, quantity, unit_price, outbound_product_no)
values (1, NULL, 6, 1, 1500, default);
insert into outbound_product (outbound_no, picked_at, product_no, quantity, unit_price, outbound_product_no)
values (5, NULL, 3, 1, 1500, default);
insert into outbound_product (outbound_no, picked_at, product_no, quantity, unit_price, outbound_product_no)
values (3, NULL, 5, 1, 1500, default);
insert into outbound_product (outbound_no, picked_at, product_no, quantity, unit_price, outbound_product_no)
values (6, NULL, 1, 1, 1500, default);
insert into outbound_product (outbound_no, picked_at, product_no, quantity, unit_price, outbound_product_no)
values (2, NULL, 4, 1, 1500, default);
insert into outbound_product (outbound_no, picked_at, product_no, quantity, unit_price, outbound_product_no)
values (4, NULL, 2, 1, 1500, default);
insert into outbound (box_height_in_millimeters, box_length_in_millimeters, box_width_in_millimeters, bulk_outbound_no,
                      cancel_reason, canceled_at, desired_delivery_at, inspected_at, is_priority_delivery, order_no,
                      packaged_weight_in_grams, packed_at, picked_at, picker_no, picking_tote_no,
                      real_packaging_material_no, packaging_material_no, tracking_number, warehouse_no, outbound_no,
                      is_manual_outbound)
values (NULL, NULL, NULL, NULL, NULL, NULL, '2021-09-30', NULL, true, 7, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 1,
        default, false);
insert into outbound_product (outbound_no, picked_at, product_no, quantity, unit_price, outbound_product_no)
values (7, NULL, 7, 1, 1500, default);
insert into outbound (box_height_in_millimeters, box_length_in_millimeters, box_width_in_millimeters, bulk_outbound_no,
                      cancel_reason, canceled_at, desired_delivery_at, inspected_at, is_priority_delivery, order_no,
                      packaged_weight_in_grams, packed_at, picked_at, picker_no, picking_tote_no,
                      real_packaging_material_no, packaging_material_no, tracking_number, warehouse_no, outbound_no,
                      is_manual_outbound)
values (NULL, NULL, NULL, NULL, NULL, NULL, '2021-09-30', NULL, true, 8, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 1,
        default, false);
insert into outbound_product (outbound_no, picked_at, product_no, quantity, unit_price, outbound_product_no)
values (8, NULL, 6, 2, 1500, default);
insert into outbound_product (outbound_no, picked_at, product_no, quantity, unit_price, outbound_product_no)
values (8, NULL, 7, 2, 1500, default);
