CREATE TABLE `User_membership` (
	`id` INT AUTO_INCREMENT,
	`user_id` VARCHAR(100),
	`membership_package` int(1) NOT NULL DEFAULT 1, -- 1: Free trial, 2: One year, 3: Three years, 5: Five years
	`price` DECIMAL(65,2),
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`),
    FOREIGN KEY(`user_id`) REFERENCES `Users`(`id`)
);