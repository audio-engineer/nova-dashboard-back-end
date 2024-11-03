insert into "order" (business_date, is_demo, is_revenue, order_reference, order_vat_number, price,
                     price_excl_vat, tips, vat, created, order_number,
                     id, order_status, payment_status)
values (current_date, true, true, 1, 1, 22.5, 18, 0, 4.5, current_timestamp, 50,
        '190d7c9e-c71b-4453-999a-0f8afd96d717',
        'ARCHIVED',
        'PAID');

insert into "product" (id, name)
values ('190d7c9e-c71b-4453-999a-0f8afd96d716', 'Test product');

insert into "order_line" (cancelled, price, quantity, system_product, unit_price,
                          unit_price_discounted, order_id, id,
                          product_id, quantity_unit)
values (false, 22.5, 1, true, 22.5, 22.5, (select id from "order" where order_reference = 1),
        gen_random_uuid(), (select id from "product" where name = 'Test product'), null);
