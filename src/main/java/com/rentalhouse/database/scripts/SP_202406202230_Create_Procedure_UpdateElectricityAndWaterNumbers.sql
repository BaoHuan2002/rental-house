CREATE PROCEDURE UpdateElectricityAndWaterNumbers(
    IN p_id VARCHAR(30),
    IN p_new_electricity_number BIGINT(20),
    IN p_new_water_number BIGINT(20)
)
BEGIN
    UPDATE infrastructures 
    SET new_electricity_number = p_new_electricity_number,
        new_water_number = p_new_water_number
    WHERE id = p_id;
END;
