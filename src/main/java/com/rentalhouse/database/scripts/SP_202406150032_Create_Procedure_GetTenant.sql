CREATE PROCEDURE GetTenant(
    IN id_param VARCHAR(255)
    )
BEGIN
    SELECT * FROM tenants
    WHERE id = id_param
    LIMIT 1;
END;
    