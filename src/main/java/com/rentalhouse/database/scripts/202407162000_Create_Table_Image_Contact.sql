CREATE TABLE `image_contact` (
    `id` VARCHAR(30) NOT NULL,
    `images` LONGTEXT,
    `contact_id` VARCHAR(30) NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`contact_id`) REFERENCES `contacts`(`id`)
);