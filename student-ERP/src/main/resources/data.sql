-- DML Sample Data for Student ERP system (PostgreSQL)
-- Using ON CONFLICT DO NOTHING to avoid duplicate key errors on multiple restarts
-- and updating the primary key sequences accordingly.

-- 1. Insert sample data for teacher
INSERT INTO teacher (id, name, email, phone, subject) VALUES
(1, 'John Doe', 'john.doe@school.com', '+1234567890', 'Mathematics'),
(2, 'Jane Smith', 'jane.smith@school.com', '+1987654321', 'Science'),
(3, 'Michael Brown', 'michael.brown@school.com', '+1555019922', 'English'),
(4, 'Emily Davis', 'emily.davis@school.com', '+1555014433', 'History'),
(5, 'David Wilson', 'david.wilson@school.com', '+1555018877', 'Computer Science')
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('teacher', 'id'), COALESCE((SELECT MAX(id) FROM teacher), 1), true);


-- 2. Insert sample data for class_room
INSERT INTO class_room (id, name, section, teacher_id) VALUES
(1, 'Grade 9', 'A', 1),
(2, 'Grade 9', 'B', 2),
(3, 'Grade 10', 'A', 3),
(4, 'Grade 10', 'B', 4),
(5, 'Grade 11', 'A', 5)
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('class_room', 'id'), COALESCE((SELECT MAX(id) FROM class_room), 1), true);


-- 3. Insert sample data for student
INSERT INTO student (id, name, roll_no, dob, parent_contact, address, photo_url, classroom_id) VALUES
(1, 'Alice Green', 'S101', '2010-05-15', '+1555011111', '123 Maple St', 'http://example.com/photos/alice.jpg', 1),
(2, 'Bob Taylor', 'S102', '2010-08-22', '+1555022222', '456 Oak Ave', 'http://example.com/photos/bob.jpg', 1),
(3, 'Charlie Rose', 'S103', '2009-11-05', '+1555033333', '789 Pine Rd', 'http://example.com/photos/charlie.jpg', 2),
(4, 'Daisy Miller', 'S104', '2009-02-14', '+1555044444', '321 Cedar Ln', 'http://example.com/photos/daisy.jpg', 3),
(5, 'Ethan Hunt', 'S105', '2008-07-30', '+1555055555', '654 Birch Dr', 'http://example.com/photos/ethan.jpg', 4)
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('student', 'id'), COALESCE((SELECT MAX(id) FROM student), 1), true);


-- 4. Insert sample data for attendance
INSERT INTO attendance (id, date, status, student_id, classroom_id) VALUES
(1, '2026-06-20', 'PRESENT', 1, 1),
(2, '2026-06-20', 'ABSENT', 2, 1),
(3, '2026-06-20', 'PRESENT', 3, 2),
(4, '2026-06-20', 'PRESENT', 4, 3),
(5, '2026-06-20', 'LATE', 5, 4)
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('attendance', 'id'), COALESCE((SELECT MAX(id) FROM attendance), 1), true);


-- 5. Insert sample data for grade
INSERT INTO grade (id, subject, marks, max_marks, term, student_id) VALUES
(1, 'Mathematics', 85.5, 100.0, 'Midterm', 1),
(2, 'Science', 90.0, 100.0, 'Midterm', 1),
(3, 'English', 78.0, 100.0, 'Midterm', 2),
(4, 'History', 92.5, 100.0, 'Midterm', 3),
(5, 'Computer Science', 88.0, 100.0, 'Midterm', 4)
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('grade', 'id'), COALESCE((SELECT MAX(id) FROM grade), 1), true);


-- 6. Insert sample data for fee
INSERT INTO fee (id, amount, due_date, paid_on, status, student_id) VALUES
(1, 1500.00, '2026-06-01', '2026-05-28', 'PAID', 1),
(2, 1500.00, '2026-06-01', NULL, 'PENDING', 2),
(3, 1600.00, '2026-06-01', '2026-06-02', 'PAID', 3),
(4, 1600.00, '2026-06-01', NULL, 'OVERDUE', 4),
(5, 1700.00, '2026-06-01', '2026-05-30', 'PAID', 5)
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('fee', 'id'), COALESCE((SELECT MAX(id) FROM fee), 1), true);
