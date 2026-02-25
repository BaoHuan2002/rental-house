CREATE TABLE `tenants` (
	`id` VARCHAR(30) NOT NULL UNIQUE,
	`infrastructure_id` VARCHAR(30) NOT NULL,
	`name` VARCHAR(100),
	`phone` VARCHAR(15),
	`address` VARCHAR(255),
	`id_number` VARCHAR(20),
	`gender` INT(1),
	`image_3x4` LONGTEXT,
	`image_backside_id_card` LONGTEXT,
	`image_front_id_card` LONGTEXT,
	`status` INT,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`),
    FOREIGN KEY(`infrastructure_id`) REFERENCES `infrastructures`(`id`)
);