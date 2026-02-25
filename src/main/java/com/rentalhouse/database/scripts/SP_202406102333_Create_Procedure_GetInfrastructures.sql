CREATE PROCEDURE GetInfrastructures(
  IN id VARCHAR(30)
)
BEGIN
  SELECT * FROM infrastructures WHERE user_id = id
                                ORDER BY status ASC, name ASC;
END;