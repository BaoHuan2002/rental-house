CREATE PROCEDURE GetContactByID(
  IN currentID VARCHAR(30) 
)
BEGIN
  SELECT * FROM contacts WHERE id = currentID;
END;