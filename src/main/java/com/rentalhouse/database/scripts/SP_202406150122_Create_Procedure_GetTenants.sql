CREATE PROCEDURE GetTenants(
    IN status_param INT(1)
)
BEGIN
    SELECT * FROM tenants 
    WHERE status = status_param;
END;
