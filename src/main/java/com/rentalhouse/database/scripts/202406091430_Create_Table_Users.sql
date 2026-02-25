CREATE TABLE `users`(
    `id` VARCHAR(30) NOT NULL UNIQUE,
    `name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `phone` VARCHAR(15) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `bank_name` VARCHAR(100),
    `bank_number` VARCHAR(100),
    `bank_QR` LONGTEXT,
    `membership_package` int(1) NOT NULL DEFAULT 1, -- 1: Free trial, 2: VIP, 3: Unlimited
    `membership_expire_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `role` int(1) NOT NULL DEFAULT 3, -- 1: Admin, 2: Lessor, 3: Tenant
    `is_deleted` int(1) NOT NULL DEFAULT 0, -- 0: UnDeleted, 1: IsDeleted
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

