DROP SCHEMA IF EXISTS `working_space`;

CREATE SCHEMA `working_space`;

USE `working_space`;

SELECT * FROM PRICE p
WHERE day_price > 12 AND week_price > 1;
