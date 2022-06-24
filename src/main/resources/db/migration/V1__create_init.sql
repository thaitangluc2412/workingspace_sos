SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `account`
(
    account_id INT AUTO_INCREMENT,
    user_name  VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    CONSTRAINT PRIMARY KEY (account_id)
);

CREATE TABLE `admin`
(
    admin_id   INT AUTO_INCREMENT,
    account_id INT NOT NULL,
    CONSTRAINT PRIMARY KEY (admin_id),
    CONSTRAINT `fk_admin_account` FOREIGN KEY (account_id) REFERENCES account (account_id)
);

CREATE TABLE `customer`
(
    customer_id   INT AUTO_INCREMENT,
    account_id    INT          NOT NULL,
    email         VARCHAR(255) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    citizen_id    VARCHAR(255) NOT NULL,
    birthday      DATE         NOT NULL,
    nationality   VARCHAR(255) NOT NULL,
    phone_number  VARCHAR(255) NOT NULL,
    CONSTRAINT PRIMARY KEY (customer_id),
    CONSTRAINT `fk_customer_account` FOREIGN KEY (account_id) REFERENCES account (account_id)
);

CREATE TABLE `property`
(
    property_id      INT AUTO_INCREMENT,
    customer_id      INT          NOT NULL,
    property_type_id INT          NOT NULL,
    property_name    VARCHAR(255) NOT NULL,
    address          VARCHAR(255) NOT NULL,
    room_quantity    INT          NOT NULL,
    createDate       DATETIME     NOT NULL,
    description      TEXT,
    rating           DOUBLE,
    CONSTRAINT PRIMARY KEY (property_id),
    CONSTRAINT `fk_property_customer` FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    CONSTRAINT `fk_property_property_type` FOREIGN KEY (property_type_id) REFERENCES property_type (property_type_id)
);

CREATE TABLE `price`
(
    price_id    INT AUTO_INCREMENT,
    hour_price  DOUBLE,
    day_price   DOUBLE,
    week_price  DOUBLE,
    month_price DOUBLE,
    CONSTRAINT PRIMARY KEY (price_id)
);

CREATE TABLE `room`
(
    room_id     INT AUTO_INCREMENT,
    property_id INT          NOT NULL,
    price_id    INT          NOT NULL,
    room_name   VARCHAR(255) NOT NULL,
    size        VARCHAR(255),
    capacity    VARCHAR(255),
    room_status ENUM (''),
    description TEXT,
    CONSTRAINT PRIMARY KEY (room_id),
    CONSTRAINT `fk_room_property` FOREIGN KEY (property_id) REFERENCES property (property_id),
    CONSTRAINT `fk_room_price` FOREIGN KEY (price_id) REFERENCES price (price_id)
);

CREATE TABLE `saved_room`
(
    customer_id INT,
    room_id     INT,
    CONSTRAINT PRIMARY KEY (customer_id, room_id),
    CONSTRAINT `fk_saved_room_customer` FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    CONSTRAINT `fk_saved_room_room` FOREIGN KEY (room_id) REFERENCES room (room_id)
);

CREATE TABLE `property_type`
(
    property_type_id   INT AUTO_INCREMENT,
    property_type_name VARCHAR(255) NOT NULL,
    image              TEXT,
    CONSTRAINT PRIMARY KEY (property_type_id)
);

CREATE TABLE `review`
(
    review_id   INT AUTO_INCREMENT,
    customer_id INT      NOT NULL,
    room_id     INT      NOT NULL,
    content     TEXT,
    createDate DATETIME NOT NULL,
    CONSTRAINT PRIMARY KEY (review_id),
    CONSTRAINT `fk_review_customer` FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    CONSTRAINT `fk_review_room` FOREIGN KEY (room_id) REFERENCES room (room_id)
);

CREATE TABLE `reservation`
(
    reservation_id     INT AUTO_INCREMENT,
    room_id            INT      NOT NULL,
    customer_id        INT      NOT NULL,
    create_date        DATETIME NOT NULL,
    start_date         DATETIME NOT NULL,
    end_date           DATETIME NOT NULL,
    quantity           INT      NOT NULL,
    reservation_status ENUM (''),
    total              DOUBLE   NOT NULL,
    deposit            DOUBLE   NOT NULL,
    CONSTRAINT PRIMARY KEY (reservation_id),
    CONSTRAINT `fk_reservation_room` FOREIGN KEY (room_id) REFERENCES room (room_id),
    CONSTRAINT `fk_reservation_customer` FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);

CREATE TABLE `room_service`
(
    room_service_id    INT AUTO_INCREMENT,
    room_id            INT          NOT NULL,
    room_service_name  VARCHAR(255) NOT NULL,
    quantity_available INT          NOT NULL,
    price              DOUBLE       NOT NULL,
    CONSTRAINT PRIMARY KEY (room_service_id),
    CONSTRAINT `fk_room_service_room` FOREIGN KEY (room_id) REFERENCES room (room_id)
);

CREATE TABLE `reservation_service`
(
    reservation_service_id   INT AUTO_INCREMENT,
    reservation_id           INT          NOT NULL,
    reservation_service_name VARCHAR(255) NOT NULL,
    quantity                 INT          NOT NULL,
    totalPrice DOUBLE NOT NULL,
    CONSTRAINT PRIMARY KEY (reservation_service_id),
    CONSTRAINT `fk_reservation_service_reservation` FOREIGN KEY (reservation_id) REFERENCES reservation(reservation_id)
);

SET FOREIGN_KEY_CHECKS = 1;