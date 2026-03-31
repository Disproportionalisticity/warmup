ALTER TABLE students 
ADD COLUMN average_grade NUMERIC(4, 2),
ADD COLUMN birth_date DATE;

ALTER TABLE school_classes
ADD COLUMN room VARCHAR(4);