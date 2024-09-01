CREATE TABLE IF NOT EXISTS customer (
    customer_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT UNSIGNED NOT NULL,
    dob DATE NOT NULL,
    address VARCHAR(200),
    gender VARCHAR(2),
    PRIMARY KEY (customer_id),
    INDEX idx_customer_name (name)
);

CREATE TABLE IF NOT EXISTS audit (
    audit_id BIGINT NOT NULL AUTO_INCREMENT,
    action VARCHAR(20) NOT NULL,
    customer_id BIGINT NOT NULL,
    creation_date DATETIME NOT NULL,
    request VARCHAR(2000),
    status VARCHAR(20),
    PRIMARY KEY (audit_id),
    INDEX idx_audit_creation_date (creation_date)
);