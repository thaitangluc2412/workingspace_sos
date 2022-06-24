SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `room_status`
(
    room_status_id   INT AUTO_INCREMENT,
    room_status_name VARCHAR(255) NOT NULL,
    CONSTRAINT PRIMARY KEY (room_status_id)
);

CREATE TABLE `reservation_status`
(
    reservation_status_id   INT AUTO_INCREMENT,
    reservation_status_name VARCHAR(255) NOT NULL,
    CONSTRAINT PRIMARY KEY (reservation_status_id)
);

ALTER TABLE `room`
    DROP COLUMN room_status;

ALTER TABLE `room`
    ADD room_status_id INT NOT NULL AFTER price_id;

ALTER TABLE `room`
    ADD CONSTRAINT `fk_room_room_status` FOREIGN KEY (room_status_id) REFERENCES `room_status` (room_status_id);

ALTER TABLE `reservation`
    DROP COLUMN reservation_status;

ALTER TABLE `reservation`
    ADD reservation_status_id INT NOT NULL AFTER customer_id;

ALTER TABLE `reservation`
    ADD CONSTRAINT `fk_reservation_reservation_status` FOREIGN KEY (reservation_status_id) REFERENCES `reservation_status` (reservation_status_id);

SET FOREIGN_KEY_CHECKS = 1;