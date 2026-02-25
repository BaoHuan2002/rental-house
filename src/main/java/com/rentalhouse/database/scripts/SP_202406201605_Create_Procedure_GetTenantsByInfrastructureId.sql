CREATE PROCEDURE GetTenantsByInfrastructureId(
    IN status_param INT(1),
    IN infrastructure_id_param VARCHAR(30)
)
BEGIN
    SELECT * FROM tenants 
    WHERE status != status_param AND infrastructure_id = infrastructure_id_param;
END;
