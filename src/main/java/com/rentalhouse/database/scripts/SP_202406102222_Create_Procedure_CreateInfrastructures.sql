CREATE PROCEDURE CreateInfrastructures(
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
  IN rental_at_param DATETIME
)
BEGIN
  INSERT INTO infrastructures(
    id, 
    name, 
    price, 
    category, 
    user_id, 
    electricity_price, 
    water_price, 
    electricity_number, 
    new_electricity_number, 
    water_number, 
    new_water_number, 
    status,
    rental_at
  )
  VALUES (
    id_param, 
    name_param, 
    price_param, 
    category_param, 
    user_id_param, 
    electricity_price_param, 
    water_price_param, 
    electricity_number_param, 
    new_electricity_number_param, 
    water_number_param, 
    new_water_number_param, 
    status_param,
    rental_at_param
  );
END;