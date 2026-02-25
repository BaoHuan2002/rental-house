CREATE PROCEDURE Register(
    IN userId_param VARCHAR(30),
    IN name_param VARCHAR(100),
    IN email_param VARCHAR(100),
    IN phone_param VARCHAR(15),
    IN password_param VARCHAR(100),
    IN role_param INT
)
BEGIN
    DECLARE expire_at DATETIME;
    SET expire_at = DATE_ADD(NOW(), INTERVAL 90 DAY);
    
    INSERT INTO Users (id, name, email, phone, password, membership_expire_at, role)
    VALUES (userId_param, name_param, email_param, phone_param, password_param, expire_at, role_param);
END;  


