CREATE TABLE IF NOT EXISTS storageSchema.storage (
    id BIGINT PRIMARY KEY DEFAULT nextval('storageSchema.storage_id_seq'),
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    rec_cost BIGINT NOT NULL
);