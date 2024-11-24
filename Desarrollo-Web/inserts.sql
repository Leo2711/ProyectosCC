INSERT INTO category(category,code,status) VALUES("Panadería y Tortillería","PANTOR",1);
INSERT INTO category(category,code,status) VALUES("Juguetería y Deportes","JUGDEP",1);
INSERT INTO category(category,code,status) VALUES("Lacteos","LACT",1);
INSERT INTO category(category,code,status) VALUES("Carnes, Pescaos y Mariscos","CAPEMA",1);
INSERT INTO category(category,code,status) VALUES("Higiene y Belleza","HIGBEL",1);
INSERT INTO category(category,code,status) VALUES("Ropa y Zapatería","ROPAZP",1);
INSERT INTO category(category,code,status) VALUES("Artículos para el Hogar y Autos","HOGAUT",1);
INSERT INTO category(category,code,status) VALUES("Bebés","BEBES",1);
INSERT INTO category(category,code,status) VALUES("Mascotas","MASCOT",1);
INSERT INTO category(category,code,status) VALUES("Congelados","CONGEL",1);
INSERT INTO category(category,code,status) VALUES("Bebidas y Licores","BEBLIC",1);
INSERT INTO category(category,code,status) VALUES("Frutas y Verduras","FRUVER",1);
INSERT INTO category(category,code,status) VALUES("Abarrotes","ABARRO",1);
INSERT INTO category(category,code,status) VALUES("Salchichonería","SALCHI",1);

select * from category;

INSERT INTO region(region,code,status) VALUES("Oriente","O",1);
INSERT INTO region(region,code,status) VALUES("Poniente","P",1);

select * from region;

INSERT INTO customer(name,surname,rfc,mail,address,region_id,customer_image_id,status) VALUES("Mónica","Miranda Mijangos","MIMM010523MA9","monmm@ciencias.unam.mx","Ote. 157 3816 Col. Salvador Díaz Mirón 07400",1,1,1);

select * from customer;