CREATE PROCEDURE GetUserMembershipByMonthAndYear(
    IN month_param INT,
    IN year_param INT
)
BEGIN
    SELECT um.*
    FROM `user_membership` um
    JOIN `users` u ON um.user_id = u.id
    WHERE MONTH(um.created_at) = month_param
        AND YEAR(um.created_at) = year_param
        AND u.role != 1
    ORDER BY um.created_at DESC;
END;

