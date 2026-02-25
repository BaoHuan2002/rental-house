CREATE PROCEDURE EmailExist(
   IN p_email VARCHAR(255)
)
BEGIN
    SELECT email
    FROM users
    WHERE email = p_email
    LIMIT 1;
END;



    