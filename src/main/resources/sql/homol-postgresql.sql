CREATE TABLE IF NOT EXISTS table_project (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(255)
);

INSERT INTO table_project (name, category) VALUES
    ('Project1', 'C1'),
    ('Project2', 'C2');
