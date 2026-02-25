CREATE PROCEDURE UpdateUser(
  IN currentID VARCHAR(30),
  IN newName VARCHAR(100),
  IN newEmail VARCHAR(100),
  IN newPhone VARCHAR(15),
  IN newPassword VARCHAR(100),
  IN newBankName VARCHAR(100),
  IN newBankNumber VARCHAR(100),
  IN newBankQR LONGTEXT,
  IN newMembershipPackage INT(1),
  IN newMembershipExpireAt VARCHAR(100),
  IN newRole INT(1),
  IN is_deleted INT
)
BEGIN
  UPDATE users 
  SET 
    name = newName,
    email = newEmail,
    phone = newPhone,
    password = newPassword,
    membership_package = newMembershipPackage,
    bank_name = newBankName,
    bank_number = newBankNumber,
    bank_QR = newBankQR,
    role = newRole,
    membership_expire_at = newMembershipExpireAt,
    is_deleted = 0
  WHERE id = currentID;
END;