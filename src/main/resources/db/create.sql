CREATE DATABASE IF NOT EXISTS `wishlistdb`;
USE `wishlistdb`;

DROP TABLE IF EXISTS `items`;
DROP TABLE IF EXISTS `wishList`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
    `userId` INT NOT NULL AUTO_INCREMENT,
    `userName` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`userId`)
);

CREATE TABLE `wishList` (
    `wishListId` INT NOT NULL AUTO_INCREMENT,
    `wishListName` VARCHAR(100) NOT NULL,
    `userId` INT,
    PRIMARY KEY (`wishListId`),
    FOREIGN KEY (`userId`) REFERENCES `users`(`userId`)
);

CREATE TABLE `items` (
    `itemId` INT NOT NULL AUTO_INCREMENT,
    `itemName` VARCHAR(100) NOT NULL,
    `itemDescription` VARCHAR(255),
    `itemPrice` DOUBLE,
    `itemLink` VARCHAR(255),
    `wishListId` INT,
    PRIMARY KEY (`itemId`),
    FOREIGN KEY (`wishListId`) REFERENCES `wishList`(`wishListId`)
);
