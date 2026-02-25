CREATE PROCEDURE ChangeStatusContact(
  IN currentID VARCHAR(30) 
)
BEGIN
  UPDATE contacts SET status = 1 WHERE id = currentID;
END;