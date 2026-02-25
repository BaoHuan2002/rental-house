CREATE TABLE `invoices` (
	`id` VARCHAR(30) NOT NULL UNIQUE,
	`infrastructure_id` VARCHAR(30),
	`price` DECIMAL(65,2),
	`water_price` DECIMAL(65,2),
	`electricity_price` DECIMAL(65,2),
	`old_electricity_number` BIGINT,
	`new_electricity_number` BIGINT,
	`old_water_number` BIGINT,
	`new_water_number` BIGINT,
	`total_price` DECIMAL(65,2),
	`status` INT(1) DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`),
  FOREIGN KEY(`infrastructure_id`) REFERENCES `infrastructures`(`id`)
);