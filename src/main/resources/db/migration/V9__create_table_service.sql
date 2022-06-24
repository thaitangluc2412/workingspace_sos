CREATE TABLE service
(
    service_id   INT AUTO_INCREMENT,
    service_name VARCHAR(255) NOT NULL,
    icon VARCHAR(255) NOT NULL,
    CONSTRAINT PRIMARY KEY (service_id)
);