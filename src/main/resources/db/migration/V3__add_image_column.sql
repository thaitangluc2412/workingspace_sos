ALTER TABLE `property`
    ADD image VARCHAR(255);

ALTER TABLE `room`
    ADD image VARCHAR(255) AFTER capacity;