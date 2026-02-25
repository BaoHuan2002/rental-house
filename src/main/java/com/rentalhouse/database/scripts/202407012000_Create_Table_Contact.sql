CREATE TABLE `contacts`(
    `id` VARCHAR(30) NOT NULL UNIQUE,
    `name` VARCHAR(100) NOT NULL,
    `title` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `phone` VARCHAR(15) NOT NULL,
    `description` LONGTEXT NOT NULL,
    `status` int(1) NOT NULL DEFAULT 2, 
    `is_deleted` int(1) NOT NULL DEFAULT 0, -- 0: UnDeleted, 1: IsDeleted
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);
