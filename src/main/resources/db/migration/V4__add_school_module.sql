CREATE TABLE schools (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

ALTER TABLE student ADD COLUMN school_id UUID;

ALTER TABLE student
    ADD CONSTRAINT fk_student_school
    FOREIGN KEY (school_id)
    REFERENCES schools (id);
