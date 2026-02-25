CREATE TABLE `infrastructures` (
	`id` VARCHAR(30) NOT NULL UNIQUE,
	`name` VARCHAR(100),
	`price` DECIMAL(65,2),
	`category` INT(1),
	`tenant_quantity` INT(2) DEFAULT 0,
	`user_id` VARCHAR(30),
	`electricity_price` DECIMAL(65,2),
	`water_price` DECIMAL(65,2),
	`electricity_number` BIGINT,
	`new_electricity_number` BIGINT,
	`water_number` BIGINT,
	`new_water_number` BIGINT,
	`status` INT(1), -- 1: Active, 2: Inactive, 3: Deleted
	`rental_at` DATETIME,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`),
    FOREIGN KEY(`user_id`) REFERENCES `Users`(`id`)
);