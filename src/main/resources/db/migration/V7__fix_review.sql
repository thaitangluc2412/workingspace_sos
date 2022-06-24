ALTER TABLE review
    DROP FOREIGN KEY `fk_review_room`;
ALTER TABLE review
    CHANGE room_id property_id INT NOT NULL;
ALTER TABLE review
    ADD CONSTRAINT `fk_review_property` FOREIGN KEY (property_id) REFERENCES property (property_id);
ALTER TABLE review
    ADD COLUMN rating DOUBLE NOT NULL AFTER property_id;
ALTER TABLE review
    CHANGE createDate create_date DATETIME NOT NULL;