CREATE PROCEDURE CheckUsers(
    IN email_or_phone_param VARCHAR(255)
    )
BEGIN
    SELECT * FROM users
    WHERE (email = email_or_phone_param OR phone = email_or_phone_param)
    LIMIT 1;
END;
    