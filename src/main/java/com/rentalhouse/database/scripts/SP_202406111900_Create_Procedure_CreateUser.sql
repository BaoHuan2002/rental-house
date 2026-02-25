CREATE PROCEDURE CreateUser(
  IN id VARCHAR(30),
  IN name VARCHAR(100),
  IN email VARCHAR(100),
  IN phone VARCHAR(15),
  IN password VARCHAR(100),
  IN bank_name VARCHAR(100),
  IN bank_number VARCHAR(100),
  IN bank_QR LONGTEXT,
  IN membership_package INT, 
  IN role INT, 
  IN is_deleted INT
)
BEGIN
  INSERT INTO users (id, name, email, phone, password, bank_name, bank_number, bank_QR, membership_package, role, is_deleted)
  VALUES (id, name, email, phone, password, bank_name, bank_number, bank_QR, membership_package, role, is_deleted);
END;