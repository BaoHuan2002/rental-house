CREATE PROCEDURE GetUsers(
  IN isDeleted INT(0) 
)
BEGIN
  SELECT * FROM users WHERE is_deleted = isDeleted;  
END;