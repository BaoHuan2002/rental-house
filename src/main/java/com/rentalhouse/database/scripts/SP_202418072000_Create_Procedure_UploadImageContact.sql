CREATE PROCEDURE UploadImageContact(
  IN id VARCHAR(30),
  IN images LONGTEXT,
  IN contact_id VARCHAR(30)
)
BEGIN
  INSERT INTO image_contact (id, images, contact_id)
  VALUES (id, images, contact_id);
END;