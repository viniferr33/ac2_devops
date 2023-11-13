CREATE TABLE IF NOT EXISTS table_project (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS table_course (
    id int PRIMARY KEY,
    name NOT NULL VARCHAR(255) UNIQUE
)

INSERT INTO table_project (name, category) VALUES
    ('Project1', 'C1'),
    ('Project2', 'C2');

INSERT INTO table_course (id, name) VALUES
    (0, 'Course1'),
    (1, 'Course2'),
    (2, 'Course3'),
    (3, 'Course4'),
    (4, 'Course5'),
    (5, 'Course6'),
    (6, 'Course7'),
    (7, 'Course8'),
    (8, 'Course9');
