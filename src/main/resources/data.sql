insert into purchase_order (description, title, warehouse_no, purchase_order_no) values ('23년도 블랙핑크 신규 앨범 주문', '블랙핑크 앨범 입고', 1, default);
insert into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price, purchase_order_product_no) values ('블랙핑크 3집 앨범[]', 1, 1, 2000, 15000, default);
insert into purchase_order (description, title, warehouse_no, purchase_order_no) values ('23년도 BTS 신규 앨범 주문', 'BTS 앨범 입고', 1, default);
insert into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price, purchase_order_product_no) values ('BTS 앨범', 2, 2, 2000, 15000, default);
insert into receive (created_at,name,purchase_order_no,receive_no) values (now(),'부분 입고 500개',1,default);
insert into receive_product (acceptable_quantity,inspected_at,inspection_comment,product_no,receive_no,rejected_quantity,receive_product_no) values (500,now(),'9/18 일부 입고됨',1,1,0,default);
insert into lpn (expiring_at,lpn_barcode,purchase_order_product_no,lpn_no) values ('2057-12-18','BP000001',1,default);
insert into receive (created_at,name,purchase_order_no,receive_no) values (now(),'9/18 정상 입고',2,default);
insert into receive_product (acceptable_quantity,inspected_at,inspection_comment,product_no,receive_no,rejected_quantity,receive_product_no) values (2000,now(),'정상 입고 됨.',2,2,0,default);