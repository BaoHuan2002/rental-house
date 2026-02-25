CREATE PROCEDURE DeleteUser(
  IN currentID VARCHAR(30) 
)
BEGIN
  UPDATE users SET is_deleted = 1 WHERE id = currentID;
END;