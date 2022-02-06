USE cryptocurrency;

DROP TABLE IF EXISTS rule;

CREATE TABLE rule
(
    rule_id INT(10) unsigned NOT NULL AUTO_INCREMENT,
    crypto_name VARCHAR(1024) not null ,
    hash VARCHAR(1024) not null ,
    epoch_adjustment VARCHAR(1024) not null ,
    private_key VARCHAR(1024) not null ,
    network_type VARCHAR(1024) not null ,
    mosaic VARCHAR(1024) not null ,
    node VARCHAR(1024) not null ,
    point_sum INT(1) not null ,
    point_add_count INT(1) not null ,
    created_at datetime not null default current_timestamp,
    PRIMARY KEY (`rule_id`)
);
