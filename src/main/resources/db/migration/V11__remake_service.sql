DROP TABLE reservation_service;

DROP TABLE room_service;

CREATE TABLE room_service
(
    service_id INT,
    room_id INT,
    CONSTRAINT PRIMARY KEY (service_id, room_id),
    CONSTRAINT fk_RoomService_Service FOREIGN KEY (service_id) REFERENCES service (service_id),
    CONSTRAINT fk_RoomService_Room FOREIGN KEY (room_id) REFERENCES room (room_id)
);