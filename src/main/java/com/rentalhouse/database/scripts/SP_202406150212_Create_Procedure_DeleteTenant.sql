CREATE PROCEDURE DeleteTenant(
    IN p_id VARCHAR(30)
)
BEGIN
    UPDATE tenants
    SET status = 3
    WHERE id = p_id;
END;