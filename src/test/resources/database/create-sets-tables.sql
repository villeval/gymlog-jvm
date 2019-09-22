CREATE TABLE IF NOT EXISTS gymlog_db.SETS (
    SET_ID varchar(100),
    USER_ID varchar(100),
    EXERCISE varchar(100),
    WEIGHT decimal(10,1),
    REPETITIONS integer,
    CREATED_DATE timestamp
);

INSERT INTO gymlog_db.SETS VALUES ('set id 1', 'user id 1', 'Squat', 102.5, 10, '2019-01-01 00:00:00');