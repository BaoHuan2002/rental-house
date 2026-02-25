CREATE PROCEDURE GetImageContactByID(
  IN currentID VARCHAR(30)  
)
BEGIN
  SELECT * FROM image_contact WHERE contact_id = currentID;  
END;