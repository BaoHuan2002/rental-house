CREATE PROCEDURE updatepassword(
    IN p_email VARCHAR(255),
    IN p_newpassword VARCHAR(255)
)
BEGIN
    UPDATE users
    SET password =  p_newpassword
    WHERE email = p_email; 
END;