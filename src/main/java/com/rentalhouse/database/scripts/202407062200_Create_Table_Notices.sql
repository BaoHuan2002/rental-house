CREATE TABLE `notices` (
    `id` INT AUTO_INCREMENT,
    `title` VARCHAR(100) NOT NULL,
    `amount_received` DECIMAL(65,2),
    `remaining_amount` DECIMAL(65,2),
    `invoice_id` VARCHAR(30) NOT NULL,
    `description` VARCHAR(255),
    `status` INT(1) DEFAULT 0,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`id`)
);
