delete from stores;
delete from products;
delete from stocks;
delete from products_stores;

insert into stores (store_id, name) values (1, 'Store 1');
insert into stores (store_id, name) values (2, 'Store 2');
insert into stores (store_id, name) values (3, 'Store 3');

insert into products (product_id, name, description, sku, price) values(1, 'Banana', 'Banana', 'SKU1', 0.19);
insert into products (product_id, name, description, sku, price) values(2, 'Bread', 'Bread', 'SKU2', 1.99);
insert into products (product_id, name, description, sku, price) values(3, 'Cereal', 'Cereal', 'SKU3', 2.50);
insert into products (product_id, name, description, sku, price) values(4, 'Milk', 'Milk', 'SKU4', 4.99);

insert into products_stores(product_id, store_id) values (1, 1);

insert into stocks (stock_id, store_id, product_id, count) values(1, 1, 1, 1);
insert into stocks (stock_id, store_id, product_id, count) values(2, 1, 2, 1);
insert into stocks (stock_id, store_id, product_id, count) values(3, 1, 3, 1);
insert into stocks (stock_id, store_id, product_id, count) values(4, 2, 2, 1);
insert into stocks (stock_id, store_id, product_id, count) values(5, 2, 3, 1);
insert into stocks (stock_id, store_id, product_id, count) values(6, 3, 2, 1);
insert into stocks (stock_id, store_id, product_id, count) values(7, 3, 3, 1);
insert into stocks (stock_id, store_id, product_id, count) values(7, 3, 4, 1);