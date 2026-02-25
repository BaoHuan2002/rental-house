CREATE PROCEDURE Login(
    IN email_or_phone_param VARCHAR(100)
)
BEGIN
    SELECT * FROM Users
    WHERE (email = email_or_phone_param OR phone = email_or_phone_param);
END;  