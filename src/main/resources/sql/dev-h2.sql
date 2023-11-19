-- Insert sample data for table_course
INSERT INTO table_course (created_at, id, name) VALUES
    ('2023-01-01 10:00:00', 'e3768c84-33c3-11ec-8d3a-0242ac130003', 'Computer Science'),
    ('2023-01-02 11:30:00', 'e3768d8a-33c3-11ec-8d3a-0242ac130004', 'Mathematics');
    -- Add more rows as needed;

-- Insert sample data for table_student
INSERT INTO table_student (created_at, updated_at, id, email, name) VALUES
    ('2023-01-01 10:00:00', '2023-01-05 14:30:00', 'e3768a4e-33c3-11ec-8d3a-0242ac130005', 'student1@example.com', 'John Doe'),
    ('2023-01-02 11:30:00', '2023-01-06 09:45:00', 'e3768b3c-33c3-11ec-8d3a-0242ac130006', 'student2@example.com', 'Jane Smith');
    -- Add more rows as needed;

-- Insert sample data for table_project
INSERT INTO table_project (created_at, id, category, name) VALUES
    ('2023-01-01 10:00:00', 'e3768c84-33c3-11ec-8d3a-0242ac130007', 'Software Development', 'Project A'),
    ('2023-01-02 11:30:00', 'e3768d8a-33c3-11ec-8d3a-0242ac130008', 'Data Analysis', 'Project B');
    -- Add more rows as needed;

-- Insert sample data for table_enrolled_course
INSERT INTO table_enrolled_course (final_grade, finish_date, start_date, id, course_id, student_id, status) VALUES
    (8.5, '2023-02-01', '2023-01-15', 1, 'e3768c84-33c3-11ec-8d3a-0242ac130003', 'e3768a4e-33c3-11ec-8d3a-0242ac130005', 'COMPLETED'),
    (7.0, '2023-02-28', '2023-01-20', 2, 'e3768d8a-33c3-11ec-8d3a-0242ac130004', 'e3768b3c-33c3-11ec-8d3a-0242ac130006', 'ACTIVE');
    -- Add more rows as needed;

-- Insert sample data for table_enrolled_project
INSERT INTO table_enrolled_project (finish_date, start_date, id, project_id, student_id, status) VALUES
    ('2023-02-15', '2023-02-01', 1, 'e3768c84-33c3-11ec-8d3a-0242ac130007', 'e3768a4e-33c3-11ec-8d3a-0242ac130005', 'COMPLETED'),
    ('2023-03-15', '2023-03-01', 2, 'e3768d8a-33c3-11ec-8d3a-0242ac130008', 'e3768b3c-33c3-11ec-8d3a-0242ac130006', 'ACTIVE');
    -- Add more rows as needed;

