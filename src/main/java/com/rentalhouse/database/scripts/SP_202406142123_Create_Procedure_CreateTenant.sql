CREATE PROCEDURE CreateTenant(
    IN p_id VARCHAR(30),
    IN p_infrastructure_id VARCHAR(30),
    IN p_name VARCHAR(100),
    IN p_phone VARCHAR(15),
    IN p_address VARCHAR(255),
    IN p_id_number VARCHAR(20),
    IN p_gender INT(1),
    IN p_image_3x4 LONGTEXT,
    IN p_image_backside_id_card LONGTEXT,
    IN p_image_front_id_card LONGTEXT,
    IN p_status INT
)
BEGIN
    INSERT INTO tenants (id, infrastructure_id, name, phone, address, id_number, gender, image_3x4, image_backside_id_card, image_front_id_card, status)
    VALUES (p_id, p_infrastructure_id, p_name, p_phone, p_address, p_id_number, p_gender, p_image_3x4, p_image_backside_id_card, p_image_front_id_card, p_status);
END;