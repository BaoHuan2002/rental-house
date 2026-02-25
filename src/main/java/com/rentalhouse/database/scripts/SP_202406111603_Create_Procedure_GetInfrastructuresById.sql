CREATE PROCEDURE GetInfrastructuresById(
  IN id_Selected VARCHAR(30) 
)
BEGIN
  SELECT * FROM infrastructures WHERE id = id_Selected;  
END;