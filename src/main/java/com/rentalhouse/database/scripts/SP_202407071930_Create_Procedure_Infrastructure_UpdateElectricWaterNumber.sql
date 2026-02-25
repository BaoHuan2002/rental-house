CREATE PROCEDURE UpdateElectricityWaterNumber(
    IN label_electricityNumber BIGINT,
    IN label_waterNumber BIGINT,
    IN infrastructureID VARCHAR(30)
)
BEGIN
    UPDATE Infrastructures
    SET
    electricity_number = label_electricityNumber,
    new_electricity_number = label_electricityNumber,
    water_number = label_waterNumber,
    new_water_number = label_waterNumber
    WHERE id = infrastructureID;
END;