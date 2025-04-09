USE `wishlistdb`;



INSERT INTO `users` (`userName`, `email`, `password`) VALUES 
('ChristianV', 'christian.vinther@example.dk', 'password123'),
('SofieM', 'sofie.moller@example.dk', 'password456'),
('MortenH', 'morten.hansen@example.dk', 'password789'),
('AnneK', 'anne.kristensen@example.dk', 'password321'),
('LarsP', 'lars.pedersen@example.dk', 'password654'),
('MariaJ', 'maria.jensen@example.dk', 'password987');


INSERT INTO `wishList` (`wishListName`, `userId`) VALUES
('Fødselsdag Ønskeliste', 1),
('Jul Ønskeliste', 1),
('Bryllupsønsker', 2),
('Konfirmation Ønskeliste', 3);



INSERT INTO `items` (`itemName`, `itemDescription`, `itemPrice`, `itemLink`, `wishListId`) VALUES
('Nike Air Force 1', 'Hvide sneakers i str. 42', 899.99, 'https://example.dk/nike-sko', 1),
('Monstera Plante', 'Stor grøn stueplante', 299.99, 'https://example.dk/planter', 1),
('Rejse til Paris', 'Weekend ophold for to personer', 4999.99, 'https://example.dk/paris', 2),
('Royal Copenhagen Kop', 'Mega Mussel kaffekopper, 2 stk', 799.99, 'https://example.dk/royal', 2),
('KitchenAid Mixer', 'Røremaskine i hvid', 3499.99, 'https://example.dk/kitchenaid', 3),
('Georg Jensen Vase', 'Sølv vase, 20 cm høj', 1499.99, 'https://example.dk/georg-jensen', 3),
('Bang & Olufsen Højtaler', 'Trådløs højtaler i sort', 2999.99, 'https://example.dk/bo', 4),
('LEGO Architecture', 'København Skyline', 449.99, 'https://example.dk/lego', 4);
