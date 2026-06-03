create database events;
use events;
-- users table creation
create table users(
user_id int auto_increment primary key,
fullname varchar(100) not null,
email varchar(100) unique not null, 
city varchar(100) not null ,
registration_date DATE NOT NULL);
-- events table creation
create table Events(
event_id int auto_increment primary key,
title VARCHAR(200) NOT NULL,
description TEXT,
city VARCHAR(100) NOT NULL,
start_date DATETIME NOT NULL,
end_date DATETIME NOT NULL,
status ENUM ('upcoming','completed','cancelled'),
organizer_id INT,
FOREIGN KEY (organizer_id) REFERENCES users(user_id ));
--  sessions table
create table sessions(
session_id INT PRIMARY KEY  AUTO_INCREMENT,
event_id INT,
title VARCHAR(200) NOT NULL ,
speaker_name VARCHAR(100) NOT NULL ,
start_time DATETIME NOT NULL ,
end_time DATETIME NOT NULL,
FOREIGN KEY(event_id) references Events(event_id));
-- registrations
create table registrations(
registration_id INT PRIMARY KEY AUTO_INCREMENT,
user_id INT  ,
event_id INT ,
registration_date DATE NOT NULL,
FOREIGN KEY (user_id) references Users(user_id),
FOREIGN KEY(event_id ) references Events(event_id)  );
-- feedback table
create table feedback(
feedback_id INT PRIMARY KEY AUTO_INCREMENT ,
user_id INT,  
event_id INT, 
rating INT CHECK (rating BETWEEN 1 AND 5) ,
comments TEXT,
feedback_date DATE NOT NULL,
FOREIGN KEY(user_id ) references  Users(user_id),
FOREIGN KEY(event_id) references Events(event_id) 
);
-- resources table
create table resources(
resource_id INT PRIMARY KEY AUTO_INCREMENT ,
event_id INT, 
resource_type ENUM ('pdf','image','link') ,
resource_url VARCHAR(255) NOT NULL ,
uploaded_at DATETIME NOT NULL,
FOREIGN KEY(event_id) references Events(event_id));
-- insert in users
INSERT INTO users (user_id, fullname, email, city, registration_date) VALUES
(1, 'Alice Johnson', 'alice@example.com', 'New York', '2024-12-01'),
(2, 'Bob Smith', 'bob@example.com', 'Los Angeles', '2024-12-05'),
(3, 'Charlie Lee', 'charlie@example.com', 'Chicago', '2024-12-10'),
(4, 'Diana King', 'diana@example.com', 'New York', '2025-01-15'),
(5, 'Ethan Hunt', 'ethan@example.com', 'Los Angeles', '2025-02-01');
-- insert in Events
INSERT INTO Events
(event_id, title, description, city, start_date, end_date, status, organizer_id)
VALUES
(1, 'Tech Innovators Meetup',
 'A meetup for tech enthusiasts.',
 'New York',
 '2025-06-10 10:00:00',
 '2025-06-10 16:00:00',
 'upcoming', 1),

(2, 'AI & ML Conference',
 'Conference on AI and ML advancements.',
 'Chicago',
 '2025-05-15 09:00:00',
 '2025-05-15 17:00:00',
 'completed', 3),

(3, 'Frontend Development Bootcamp',
 'Hands-on training on frontend tech.',
 'Los Angeles',
 '2025-07-01 10:00:00',
 '2025-07-03 16:00:00',
 'upcoming', 2);
 -- insert in registration
 INSERT INTO registrations
(registration_id, user_id, event_id, registration_date)
VALUES
(1, 1, 1, '2025-05-01'),
(2, 2, 1, '2025-05-02'),
(3, 3, 2, '2025-04-30'),
(4, 4, 2, '2025-04-28'),
(5, 5, 3, '2025-06-15');
-- insert in sessions
INSERT INTO sessions
(session_id, event_id, title, speaker_name, start_time, end_time)
VALUES
(1, 1, 'Opening Keynote', 'Dr. Tech',
 '2025-06-10 10:00:00',
 '2025-06-10 11:00:00'),

(2, 1, 'Future of Web Dev', 'Alice Johnson',
 '2025-06-10 11:15:00',
 '2025-06-10 12:30:00'),

(3, 2, 'AI in Healthcare', 'Charlie Lee',
 '2025-05-15 09:30:00',
 '2025-05-15 11:00:00'),

(4, 3, 'Intro to HTML5', 'Bob Smith',
 '2025-07-01 10:00:00',
 '2025-07-01 12:00:00');
 -- insert in feedback
 INSERT INTO feedback
(feedback_id, user_id, event_id, rating, comments, feedback_date)
VALUES
(1, 3, 2, 4, 'Great insights!', '2025-05-16'),
(2, 4, 2, 5, 'Very informative.', '2025-05-16'),
(3, 2, 1, 3, 'Could be better.', '2025-06-11');
-- insert in resources
INSERT INTO resources
(resource_id, event_id, resource_type, resource_url, uploaded_at)
VALUES
(1, 1, 'pdf',
 'https://portal.com/resources/tech_meetup_agenda.pdf',
 '2025-05-01 10:00:00'),

(2, 2, 'image',
 'https://portal.com/resources/ai_poster.jpg',
 '2025-04-20 09:00:00'),

(3, 3, 'link',
 'https://portal.com/resources/html5_docs',
 '2025-06-25 15:00:00');
-- 1. User Upcoming Events
SELECT u.fullname, e.title, e.city, e.start_date
FROM users u
JOIN registrations r ON u.user_id = r.user_id
JOIN events e ON r.event_id = e.event_id
WHERE e.status = 'upcoming'
AND u.city = e.city
ORDER BY e.start_date;
-- 2. Top Rated Events
SELECT e.event_id, e.title, AVG(f.rating) AS avg_rating
FROM events e
JOIN feedback f ON e.event_id = f.event_id
GROUP BY e.event_id, e.title
HAVING COUNT(f.feedback_id) >= 10
ORDER BY avg_rating DESC;
-- 3. Inactive Users
SELECT *
FROM users
WHERE user_id NOT IN (
    SELECT DISTINCT user_id
    FROM registrations
    WHERE registration_date >= CURDATE() - INTERVAL 90 DAY
);
-- 4. Peak Session Hours
SELECT event_id, COUNT(*) AS session_count
FROM sessions
WHERE TIME(start_time) BETWEEN '10:00:00' AND '12:00:00'
GROUP BY event_id;
-- 5. Most Active Cities
SELECT u.city, COUNT(DISTINCT r.user_id) AS registrations
FROM users u
JOIN registrations r ON u.user_id = r.user_id
GROUP BY u.city
ORDER BY registrations DESC
LIMIT 5;
-- 6. Event Resource Summary
SELECT e.title,
       COUNT(r.resource_id) AS total_resources
FROM events e
LEFT JOIN resources r
ON e.event_id = r.event_id
GROUP BY e.event_id, e.title;
-- 7. Low Feedback Alerts
SELECT u.fullname,
       e.title,
       f.rating,
       f.comments
FROM feedback f
JOIN users u ON f.user_id = u.user_id
JOIN events e ON f.event_id = e.event_id
WHERE f.rating < 3;
-- 8. Sessions per Upcoming Event
SELECT e.title,
       COUNT(s.session_id) AS session_count
FROM events e
LEFT JOIN sessions s
ON e.event_id = s.event_id
WHERE e.status='upcoming'
GROUP BY e.event_id, e.title;
-- 9. Organizer Event Summary
SELECT organizer_id,
       status,
       COUNT(*) AS total_events
FROM events
GROUP BY organizer_id, status;
-- 10. Feedback Gap
SELECT e.event_id, e.title
FROM events e
JOIN registrations r
ON e.event_id = r.event_id
LEFT JOIN feedback f
ON e.event_id = f.event_id
WHERE f.feedback_id IS NULL
GROUP BY e.event_id, e.title;
-- 11. Daily New User Count
SELECT registration_date,
       COUNT(*) AS new_users
FROM users
WHERE registration_date >= CURDATE() - INTERVAL 7 DAY
GROUP BY registration_date;
-- 12. Event with Maximum Sessions
SELECT e.title, COUNT(s.session_id) AS total_sessions
FROM events e
JOIN sessions s
ON e.event_id = s.event_id
GROUP BY e.event_id, e.title
HAVING COUNT(s.session_id)=(
    SELECT MAX(session_count)
    FROM(
        SELECT COUNT(*) session_count
        FROM sessions
        GROUP BY event_id
    ) x
);
-- 13. Average Rating per City
SELECT e.city,
       AVG(f.rating) AS avg_rating
FROM events e
JOIN feedback f
ON e.event_id = f.event_id
GROUP BY e.city;
-- 14. Most Registered Events
SELECT e.title,
       COUNT(r.registration_id) AS total_registrations
FROM events e
JOIN registrations r
ON e.event_id = r.event_id
GROUP BY e.event_id, e.title
ORDER BY total_registrations DESC
LIMIT 3;
-- 15. Event Session Time Conflict
SELECT s1.event_id,
       s1.title AS session1,
       s2.title AS session2
FROM sessions s1
JOIN sessions s2
ON s1.event_id = s2.event_id
AND s1.session_id < s2.session_id
AND s1.start_time < s2.end_time
AND s1.end_time > s2.start_time;
-- 16. Unregistered Active Users
SELECT *
FROM users u
WHERE u.registration_date >= CURDATE() - INTERVAL 30 DAY
AND NOT EXISTS (
    SELECT 1
    FROM registrations r
    WHERE r.user_id = u.user_id
);
-- 17. Multi-Session Speakers
SELECT speaker_name,
       COUNT(*) AS total_sessions
FROM sessions
GROUP BY speaker_name
HAVING COUNT(*) > 1;
-- 18. Resource Availability Check
SELECT e.event_id,
       e.title
FROM events e
LEFT JOIN resources r
ON e.event_id = r.event_id
WHERE r.resource_id IS NULL;
-- 19. Completed Events with Feedback Summary
SELECT e.title,
       COUNT(DISTINCT r.registration_id) AS total_registrations,
       AVG(f.rating) AS avg_rating
FROM events e
LEFT JOIN registrations r
ON e.event_id = r.event_id
LEFT JOIN feedback f
ON e.event_id = f.event_id
WHERE e.status='completed'
GROUP BY e.event_id, e.title;
-- 20. User Engagement Index
SELECT u.user_id,
       u.fullname,
       COUNT(DISTINCT r.event_id) AS events_attended,
       COUNT(DISTINCT f.feedback_id) AS feedback_submitted
FROM users u
LEFT JOIN registrations r
ON u.user_id = r.user_id
LEFT JOIN feedback f
ON u.user_id = f.user_id
GROUP BY u.user_id, u.fullname;
-- 21. Top Feedback Providers
SELECT u.fullname,
       COUNT(f.feedback_id) AS total_feedbacks
FROM users u
JOIN feedback f
ON u.user_id = f.user_id
GROUP BY u.user_id, u.fullname
ORDER BY total_feedbacks DESC
LIMIT 5;
-- 22. Duplicate Registrations Check
SELECT user_id,
       event_id,
       COUNT(*) AS duplicates
FROM registrations
GROUP BY user_id, event_id
HAVING COUNT(*) > 1;
-- 23. Registration Trends
SELECT YEAR(registration_date) AS year,
       MONTH(registration_date) AS month,
       COUNT(*) AS registrations
FROM registrations
WHERE registration_date >= CURDATE() - INTERVAL 12 MONTH
GROUP BY YEAR(registration_date),
         MONTH(registration_date)
ORDER BY year, month;
-- 24. Average Session Duration per Event
SELECT e.title,
       AVG(
         TIMESTAMPDIFF(
            MINUTE,
            s.start_time,
            s.end_time
         )
       ) AS avg_duration_minutes
FROM events e
JOIN sessions s
ON e.event_id = s.event_id
GROUP BY e.event_id, e.title;
-- 25. Events Without Sessions
SELECT e.event_id,
       e.title
FROM events e
LEFT JOIN sessions s
ON e.event_id = s.event_id
WHERE s.session_id IS NULL;