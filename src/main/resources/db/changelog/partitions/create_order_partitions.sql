--liquibase formatted sql
--changeset martin:partition-orders

CREATE TABLE orders_base (
LIKE orders INCLUDING ALL
) PARTITION BY HASH (id);

CREATE TABLE orders_p0 PARTITION OF orders_base FOR VALUES WITH (MODULUS 4, REMAINDER 0);
CREATE TABLE orders_p1 PARTITION OF orders_base FOR VALUES WITH (MODULUS 4, REMAINDER 1);
CREATE TABLE orders_p2 PARTITION OF orders_base FOR VALUES WITH (MODULUS 4, REMAINDER 2);
CREATE TABLE orders_p3 PARTITION OF orders_base FOR VALUES WITH (MODULUS 4, REMAINDER 3);

INSERT INTO orders_base
SELECT * FROM orders;

DROP TABLE orders;
ALTER TABLE orders_base RENAME TO orders;

CREATE INDEX idx_orders_id ON orders (id);
CREATE INDEX idx_orders_user_id ON orders (user_id);
CREATE INDEX idx_orders_service_id ON orders (service_id);
CREATE INDEX idx_orders_status ON orders (status);
CREATE INDEX idx_orders_type ON orders (type);

COMMENT ON TABLE orders IS 'Partitioned orders table by hash of id (4 partitions)';
COMMENT ON INDEX idx_orders_id IS 'Global index on orders id for partition pruning';
