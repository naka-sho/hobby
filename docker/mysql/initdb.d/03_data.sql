USE cryptocurrency;

DELETE FROM `cryptocurrency`.`rule` WHERE (`rule_id` = '1');
INSERT INTO `cryptocurrency`.`rule` (`rule_id`, `crypto_name`, `hash`, `epoch_adjustment`, `private_key`, `network_type`, `mosaic`, `node`) VALUES ('1', 'symbol', '7FCCD304802016BEBBCD342A332F91FF1F3BB5E902988B352697BE245F48E836', '1637848847', '54CB7AA88F46CB140D3B9341835DAF61F6F4B1EC93D6270BE53C53B769072487', 'test', '3A8416DB2D53B6C8', 'https://sym-test.opening-line.jp:3001');
