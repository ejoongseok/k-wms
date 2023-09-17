insert into purchase_order (description, title, warehouse_no, purchase_order_no)
values ('23년도 블랙핑크 신규 앨범 주문', '블랙핑크 앨범 입고', 1, default)
    insert
into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price,
                             purchase_order_product_no)
values ('블랙핑크 3집 앨범[]', 1, 1, 2000, 15000, default)
-- insert into receive_product (acceptable_quantity,arrived_at,inspected_at,inspection_comment,product_no,purchase_order_no,rejected_quantity,receive_product_no) values (1000,now(),now(),'1000개만 먼저 입고 됨.',1,1,0,default)
insert into purchase_order (description, title, warehouse_no, purchase_order_no)
values ('23년도 BTS 신규 앨범 주문', 'BTS 앨범 입고', 1, default)
insert into purchase_order_product (description, product_no, purchase_order_no, request_quantity, unit_price,
                                    purchase_order_product_no)
values ('BTS 앨범', 2, 2, 2000, 15000, default)