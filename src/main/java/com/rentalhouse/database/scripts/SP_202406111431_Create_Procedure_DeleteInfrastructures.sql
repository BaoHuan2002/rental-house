CREATE PROCEDURE DeleteInfrastructures(
  IN infrastructure_id VARCHAR(30)
)
BEGIN
  UPDATE infrastructures SET status = 3 WHERE id = infrastructure_id;
END;