CREATE TABLE `image_storage`
(
    image_storage_id INT AUTO_INCREMENT,
    CONSTRAINT PRIMARY KEY (image_storage_id)
);

ALTER TABLE property_type CHANGE `image` `image_storage_id` INT NOT NULL AFTER `property_type_id`;
ALTER TABLE property_type ADD CONSTRAINT `fk_property_type_image_storage` FOREIGN KEY (image_storage_id) REFERENCES `image_storage` (image_storage_id);

ALTER TABLE room CHANGE `image` `image_storage_id` INT NOT NULL AFTER `room_status_id`;
ALTER TABLE room ADD CONSTRAINT `fk_room_image_storage` FOREIGN KEY (image_storage_id) REFERENCES `image_storage` (image_storage_id);

ALTER TABLE property CHANGE `image` `image_storage_id` INT NOT NULL AFTER `property_type_id`;
ALTER TABLE property ADD CONSTRAINT `fk_property_image_storage` FOREIGN KEY (image_storage_id) REFERENCES `image_storage` (image_storage_id);

CREATE TABLE `image`
(
    image_id  INT AUTO_INCREMENT,
    image_storage_id INT NOT NULL,
    url       TEXT         NOT NULL,
    thumbnail VARCHAR(255) NOT NULL,
    CONSTRAINT PRIMARY KEY (image_id),
    CONSTRAINT `fk_image_image_storage` FOREIGN KEY (image_storage_id) REFERENCES `image_storage` (image_storage_id)
);
