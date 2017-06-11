SELECT
    AVG(numberOfUsers)
FROM
    SOAPBOX.ONLINE_USERS;
--
--
SELECT
    MAX(numberOfUsers),
    from_unixtime(MAX(id))
FROM
    SOAPBOX.ONLINE_USERS
GROUP BY
    numberOfUsers;
--
--
SELECT
    clock,
    MAX(numberOfUsers)
FROM
    (
        SELECT
            concat(from_unixtime(id,'%Y-%m-%d_%H'),':00') clock,
            numberOfUsers,
            id
        FROM
            SOAPBOX.ONLINE_USERS) alldata group by alldata.clock;
