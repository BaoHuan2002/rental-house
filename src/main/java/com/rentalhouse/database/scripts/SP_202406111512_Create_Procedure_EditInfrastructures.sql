CREATE PROCEDURE EditInfrastructures(
  IN id_param VARCHAR(30),
  IN name_param VARCHAR(100),
  IN price_param DECIMAL(65,2),
  IN category_param INT(1),
  IN user_id_param VARCHAR(30),
  IN electricity_price_param DECIMAL(65,2),
  IN water_price_param DECIMAL(65,2),
  IN electricity_number_param BIGINT,
  IN new_electricity_number_param BIGINT,
  IN water_number_param BIGINT,
  IN new_water_number_param BIGINT,
  IN status_param INT(1),
  IN tenant_quantity_param INT(2),
  IN rental_at_param DATETIME
)
BEGIN
    UPDATE infrastructures
    SET
    name = name_param,
    price = price_param,
    category = category_param,
    user_id = user_id_param,
    electricity_price = electricity_price_param,
    water_price = water_price_param,
    electricity_number = electricity_number_param,
    new_electricity_number = new_electricity_number_param,
    water_number = water_number_param,
    new_water_number = new_water_number_param,
    status = status_param,
    tenant_quantity = tenant_quantity_param,
    rental_at = rental_at_param
    WHERE id = id_param;
END;