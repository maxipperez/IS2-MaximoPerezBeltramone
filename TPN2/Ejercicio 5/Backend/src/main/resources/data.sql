INSERT IGNORE INTO category (id, title, meta_title, slug, content) VALUES
(1, 'Electronics', 'Electronics', 'electronics', 'Consumer electronics'),
(2, 'Home and Kitchen', 'Home and Kitchen', 'home-kitchen', 'Household products');

INSERT IGNORE INTO sub_category (id, description, categoryid) VALUES
(1, 'Audio', 1),
(2, 'Computers', 1),
(3, 'Kitchen appliances', 2);

INSERT IGNORE INTO product (id, title, summary, created_at, content, subcategoryid, categoryid) VALUES
(1, 'Wireless Headphones', 'Over-ear wireless headphones', '2026-01-10 10:00:00', 'Bluetooth headphones with noise isolation.', 1, 1),
(2, 'Laptop', 'Lightweight laptop for everyday work', '2026-01-11 10:00:00', 'A portable laptop for study and work.', 2, 1),
(3, 'Coffee Maker', 'Automatic drip coffee maker', '2026-01-12 10:00:00', 'Programmable coffee maker for home use.', 3, 2);