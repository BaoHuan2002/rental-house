CREATE PROCEDURE DeleteContact(
  IN currentID VARCHAR(30) 
)
BEGIN
  UPDATE contacts SET is_deleted = 1 WHERE id = currentID;
END;