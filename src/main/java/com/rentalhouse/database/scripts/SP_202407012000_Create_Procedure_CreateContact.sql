CREATE PROCEDURE CreateContact(
  IN id VARCHAR(30),
  IN name VARCHAR(100),
  IN title VARCHAR(100),
  IN email VARCHAR(100),
  IN phone VARCHAR(15),
  IN description LONGTEXT,
  IN status INT,  
  IN is_deleted INT
)
BEGIN
  INSERT INTO contacts (id, name, title, email, phone, description, status, is_deleted)
  VALUES (id, name, title, email, phone, description, status, is_deleted);
END;