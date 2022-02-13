USE cryptocurrency;

DROP TABLE IF EXISTS rule;

CREATE TABLE rule
(
    rule_id bigint unsigned NOT NULL AUTO_INCREMENT,
    crypto_name VARCHAR(1024) not null ,
    hash VARCHAR(1024) not null ,
    epoch_adjustment VARCHAR(1024) not null ,
    private_key VARCHAR(1024) not null ,
    network_type VARCHAR(1024) not null ,
    mosaic VARCHAR(1024) not null ,
    node VARCHAR(1024) not null ,
    created_at datetime not null default current_timestamp,
    PRIMARY KEY (`rule_id`)
);

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    user_id bigint unsigned NOT NULL AUTO_INCREMENT,
    address VARCHAR(1024) not null ,
    created_at datetime not null default current_timestamp,
    PRIMARY KEY (`user_id`),
    INDEX idx_address(address)
);

DROP TABLE IF EXISTS queue;

CREATE TABLE queue
(
    queue_id bigint unsigned NOT NULL AUTO_INCREMENT,
    address VARCHAR(1024) not null ,
    transaction VARCHAR(1024) not null ,
    price bigint not null ,
    url VARCHAR(1024) not null ,
    created_at datetime not null default current_timestamp,
    PRIMARY KEY (`queue_id`)
);

DROP TABLE IF EXISTS send_log;

CREATE TABLE send_log
(
    send_log_id bigint unsigned NOT NULL AUTO_INCREMENT,
    address VARCHAR(1024) not null ,
    transaction VARCHAR(1024) not null ,
    price bigint not null ,
    url VARCHAR(1024) not null ,
    created_at datetime not null default current_timestamp,
    PRIMARY KEY (`send_log_id`)
);
