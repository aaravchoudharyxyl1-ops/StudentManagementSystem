-- Run in MySQL terminal as an administrator: mysql -u root -p < sql/setup.sql
-- Change the example password below BEFORE executing this file.
CREATE DATABASE IF NOT EXISTS student_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'student_app'@'localhost' IDENTIFIED BY 'CHANGE_THIS_PASSWORD';
GRANT SELECT, INSERT, UPDATE, DELETE ON student_management.* TO 'student_app'@'localhost';
USE student_management;
CREATE TABLE IF NOT EXISTS students (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    age INT NOT NULL,
    CONSTRAINT chk_students_age CHECK (age BETWEEN 1 AND 120),
    CONSTRAINT chk_students_name CHECK (CHAR_LENGTH(TRIM(name)) > 0)
);
