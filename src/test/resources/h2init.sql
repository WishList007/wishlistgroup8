DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS wishList;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE wishList (
    wishListId INT AUTO_INCREMENT PRIMARY KEY,
    wishListName VARCHAR(100) NOT NULL,
    userId INT,
    FOREIGN KEY (userId) REFERENCES users(userId)
);

CREATE TABLE items (
    itemId INT AUTO_INCREMENT PRIMARY KEY,
    itemName VARCHAR(100) NOT NULL,
    itemDescription TEXT,
    itemPrice DOUBLE,
    itemLink VARCHAR(255),
    wishListId INT,
    FOREIGN KEY (wishListId) REFERENCES wishList(wishListId)
);

INSERT INTO users (username, email, password) VALUES
('ChristianV', 'christian.vinther@example.dk', 'password123'),
('SofieM', 'sofie.moller@example.dk', 'password456');

INSERT INTO wishList (wishListName, userId) VALUES
('Fødselsdag Ønskeliste', (SELECT userId FROM users WHERE username = 'ChristianV')),
('Jul Ønskeliste', (SELECT userId FROM users WHERE username = 'ChristianV')),
('Bryllupsønsker', (SELECT userId FROM users WHERE username = 'SofieM'));

INSERT INTO items (itemName, itemDescription, itemPrice, itemLink, wishListId) VALUES
('Nike Air Force 1', 'Hvide sneakers i str. 42', 899.99, 'https://example.dk/nike-sko', (SELECT wishListId FROM wishList WHERE wishListName = 'Fødselsdag Ønskeliste')),
('Monstera Plante', 'Stor grøn stueplante', 299.99, 'https://example.dk/planter', (SELECT wishListId FROM wishList WHERE wishListName = 'Fødselsdag Ønskeliste')),
('Rejse til Paris', 'Weekend ophold for to personer', 4999.99, 'https://example.dk/paris', (SELECT wishListId FROM wishList WHERE wishListName = 'Jul Ønskeliste')),
('Royal Copenhagen Kop', 'Mega Mussel kaffekopper, 2 stk', 799.99, 'https://example.dk/royal', (SELECT wishListId FROM wishList WHERE wishListName = 'Jul Ønskeliste')),
('KitchenAid Mixer', 'Røremaskine i hvid', 3499.99, 'https://example.dk/kitchenaid', (SELECT wishListId FROM wishList WHERE wishListName = 'Bryllupsønsker'));
