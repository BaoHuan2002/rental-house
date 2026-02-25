CREATE PROCEDURE SearchInfrastructure(
    IN infrastructure_name VARCHAR(30)
)
BEGIN
    SELECT * FROM infrastructures WHERE name LIKE CONCAT('%', infrastructure_name, '%');
END;