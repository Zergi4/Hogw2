-- liquibase formatted sql

--changeset skolmakov:1

CREATE INDEX student_name_index ON student (name);

--changeset skolmakov:1

CREATE INDEX faculty_name_color_index ON faculty (name, color);
