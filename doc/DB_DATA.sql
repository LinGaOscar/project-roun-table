-- Initial Roles
INSERT INTO sys_role (role, role_name, `function`) VALUES ('ADMIN', 'System Administrator', 'ALL_FUNCTIONS');
INSERT INTO sys_role (role, role_name, `function`) VALUES ('USER', 'Regular User', 'VIEW_AND_ENROLL');

-- Initial Users (Password for all is: 1234)
-- The hash below is verified for Spring Security BCryptPasswordEncoder
INSERT INTO sys_user (account, user_name, password, role, bio, specialty) VALUES 
('admin', 'Administrator', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnu', 'ADMIN', 'Master of the Round Table.', 'System Management'),
('lecturer01', 'Dr. Smith', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnu', 'USER', 'Expert in Software Engineering with 20 years experience.', 'Java Development'),
('user01', 'John Doe', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnu', 'USER', NULL, NULL);

-- Initial Venues
INSERT INTO venue (name, capacity, address, description) VALUES 
('Main Hall', 100, 'Building A, 1F', 'Large conference hall with projector'),
('Lab 01', 30, 'Building B, 3F', 'Computer laboratory with high-end PCs'),
('Meeting Room 2', 12, 'Building A, 2F', 'Small discussion room');

-- Initial Classes
INSERT INTO class_table (title, sub_title, weekly, date, seq_no, venue_id, instructor_id, max_participants) VALUES 
('Spring Boot Advanced', 'Mastering Microservices', 'Monday', '2026-03-02', 1, 2, 2, 25),
('AI & Machine Learning', 'Introduction to Neural Networks', 'Wednesday', '2026-03-04', 2, 1, 2, 50),
('Project Management', 'Agile & Scrum Basics', 'Friday', '2026-03-06', 3, 3, 1, 10);