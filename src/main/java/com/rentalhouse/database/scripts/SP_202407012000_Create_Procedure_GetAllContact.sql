CREATE PROCEDURE GetAllContact(
  IN isDeleted INT(0) 
)
BEGIN
  SELECT * FROM contacts WHERE is_deleted = isDeleted;  
END;