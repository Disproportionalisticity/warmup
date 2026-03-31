CREATE TABLE school_classes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    class_id BIGINT,
    CONSTRAINT fk_school_class FOREIGN KEY (class_id) REFERENCES school_classes(id) ON DELETE CASCADE
);