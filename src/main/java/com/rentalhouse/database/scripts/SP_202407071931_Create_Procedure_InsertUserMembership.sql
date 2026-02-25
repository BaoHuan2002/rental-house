CREATE PROCEDURE InsertUserMembership(
    IN user_id_param VARCHAR(30),
    IN membership_package_param INT,
    IN price_param DECIMAL(65, 2)
)
BEGIN
    INSERT INTO user_membership(user_id, membership_package, price)
    VALUES (user_id_param, membership_package_param, price_param);
END;