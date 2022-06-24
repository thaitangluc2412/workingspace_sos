CREATE USER 'workingspace'@'localhost' IDENTIFIED BY 'workingspace';
GRANT ALL PRIVILEGES ON * . * TO 'workingspace'@'localhost';
FLUSH PRIVILEGES;
