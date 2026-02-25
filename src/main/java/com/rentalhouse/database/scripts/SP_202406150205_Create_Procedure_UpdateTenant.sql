CREATE PROCEDURE UpdateTenant(
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
    UPDATE tenants SET
        infrastructure_id = p_infrastructure_id,
        name = p_name,
        phone = p_phone,
        address = p_address,
        id_number = p_id_number,
        gender = p_gender,
        image_3x4 = p_image_3x4,
        image_backside_id_card = p_image_backside_id_card,
        image_front_id_card = p_image_front_id_card,
        status = p_status
    WHERE id = p_id;
END;