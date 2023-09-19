insert into purchase_order (description, title, warehouse_no, purchase_order_no) values ('23년도 블랙핑크 신규 앨범 주문', '블랙핑크 앨범 입고', 1, default);
insert into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price, purchase_order_product_no) values ('블랙핑크 3집 앨범[]', 1, 1, 2000, 15000, default);
insert into purchase_order (description, title, warehouse_no, purchase_order_no) values ('23년도 BTS 신규 앨범 주문', 'BTS 앨범 입고', 1, default);
insert into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price, purchase_order_product_no) values ('BTS 앨범', 2, 2, 2000, 15000, default);
insert into receive (created_at,name,purchase_order_no,receive_no) values (now(),'부분 입고 500개',1,default);
insert into receive_product (acceptable_quantity,inspected_at,inspection_comment,product_no,receive_no,rejected_quantity,receive_product_no) values (500,now(),'9/18 일부 입고됨',1,1,0,default);
insert into lpn (expiring_at,lpn_barcode,purchase_order_product_no,lpn_no) values ('2057-12-18','BP000001',1,default);
insert into receive (created_at,name,purchase_order_no,receive_no) values (now(),'9/18 정상 입고',2,default);
insert into receive_product (acceptable_quantity,inspected_at,inspection_comment,product_no,receive_no,rejected_quantity,receive_product_no) values (2000,now(),'정상 입고 됨.',2,2,0,default);

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

insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A1',NULL,'RACK','FILL',1,21);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A2',NULL,'RACK','FILL',1,22);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A3',NULL,'RACK','FILL',1,23);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A4',NULL,'RACK','FILL',1,24);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A5',NULL,'RACK','FILL',1,25);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-B1',NULL,'RACK','FILL',1,26);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-B2',NULL,'RACK','FILL',1,27);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-B3',NULL,'RACK','FILL',1,28);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-B4',NULL,'RACK','FILL',1,29);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-B5',NULL,'RACK','FILL',1,30);

insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A1-1',21,'SHELF','FILL',1,31);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A2-2',21,'SHELF','FILL',1,32);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A3-3',21,'SHELF','FILL',1,33);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A4-4',21,'SHELF','FILL',1,34);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A5-5',21,'SHELF','FILL',1,35);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-B1-1',26,'SHELF','FILL',1,36);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-B2-2',26,'SHELF','FILL',1,37);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-B3-3',26,'SHELF','FILL',1,38);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-B4-4',26,'SHELF','FILL',1,39);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-B5-5',26,'SHELF','FILL',1,40);

insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A1-1-1',31,'BIN','FILL',1,41);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A1-1-2',31,'BIN','FILL',1,42);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A1-1-3',31,'BIN','FILL',1,43);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A1-1-4',31,'BIN','FILL',1,44);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A1-1-5',31,'BIN','FILL',1,45);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A1-1-6',31,'BIN','FILL',1,46);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A1-1-7',31,'BIN','FILL',1,47);
insert into location (location_barcode,parent_location_no,storage_type,usage_purpose,warehouse_no,location_no) values ('GDC-A1-1-8',31,'BIN','FILL',1,48);

ALTER TABLE location ALTER COLUMN location_no RESTART WITH 50;

insert into inventory (location_no, lpn_no, product_no, quantity, warehouse_no, inventory_no) values (48, 1, 1, 1, 1, default);