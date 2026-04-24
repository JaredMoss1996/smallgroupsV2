



---------------- Required for Spring Security ----------------
-- Roles
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('LEADER');
INSERT INTO roles (name) VALUES ('CHURCH_ADMIN');
INSERT INTO roles (name) VALUES ('SUPER_ADMIN');

INSERT INTO permissions (name) VALUES ('JOIN_LEAVE_GROUP');
INSERT INTO permissions (name) VALUES ('MANAGE_GROUPS');
INSERT INTO permissions (name) VALUES ('MANAGE_LEADERS');

INSERT INTO ages (name) VALUES ('All Adults');
INSERT INTO ages (name) VALUES ('20s');
INSERT INTO ages (name) VALUES ('30s');
INSERT INTO ages (name) VALUES ('40s');
INSERT INTO ages (name) VALUES ('50s');
INSERT INTO ages (name) VALUES ('60s');
INSERT INTO ages (name) VALUES ('70s and Up');

INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 2);
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 2);
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 3);


INSERT INTO category (name) VALUES ('Bible Study');
INSERT INTO category (name) VALUES ('Community Service');
INSERT INTO category (name) VALUES ('Young Adults');
INSERT INTO category (name) VALUES ('Families');
INSERT INTO category (name) VALUES ('Seniors');

INSERT INTO genders (name) VALUES ('MALE');
INSERT INTO genders (name) VALUES ('FEMALE');
INSERT INTO genders (name) VALUES ('BOTH');
--
--
---------------- Test Data ----------------

INSERT INTO churches (name, address, contact_info) VALUES
('Grace Community Church', '123 Main St, Springfield, IL 62701', 'office@gracecommunity.org | (217) 555-0110'),
('Riverstone Fellowship', '845 Oak Ave, Springfield, IL 62703', 'hello@riverstonefellowship.org | (217) 555-0134'),
('New Hope Church', '2200 Lincoln Blvd, Springfield, IL 62704', 'connect@newhopechurch.org | (217) 555-0178');

-- password 1234
INSERT INTO app_user (email, password, role_id) VALUES ('user@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 1);
INSERT INTO app_user (email, password, role_id) VALUES ('leader@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 2);
INSERT INTO app_user (email, password, role_id) VALUES ('churchadmin@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 3);
INSERT INTO app_user (email, password, role_id) VALUES ('emma.johnson@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 1);
INSERT INTO app_user (email, password, role_id) VALUES ('noah.williams@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 1);
INSERT INTO app_user (email, password, role_id) VALUES ('olivia.brown@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 1);
INSERT INTO app_user (email, password, role_id) VALUES ('liam.martinez@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 2);
INSERT INTO app_user (email, password, role_id) VALUES ('ava.thomas@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 1);
INSERT INTO app_user (email, password, role_id) VALUES ('mason.jackson@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 1);
INSERT INTO app_user (email, password, role_id) VALUES ('sophia.white@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 2);
INSERT INTO app_user (email, password, role_id) VALUES ('ethan.harris@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 1);
INSERT INTO app_user (email, password, role_id) VALUES ('mia.clark@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 1);
INSERT INTO app_user (email, password, role_id) VALUES ('james.lewis@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 3);
INSERT INTO app_user (email, password, role_id) VALUES ('isabella.walker@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 1);
INSERT INTO app_user (email, password, role_id) VALUES ('benjamin.hall@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 2);
INSERT INTO app_user (email, password, role_id) VALUES ('charlotte.allen@gmail.com', '$2a$10$Z/oGzoxN3zNcgdg59y0Pbe/PMZvkkbjUpeE5HtwBEuHxLdJUe4MPO', 1);

INSERT INTO members (first_name, last_name, email, home_phone, mobile_phone, church_id, app_user_id) VALUES
('Alice', 'Smith', 'alice.smith@gmail.com', '(217) 555-1001', '(217) 555-2001', 1, 1),
('John', 'Carter', 'leader@gmail.com', '(217) 555-1002', '(217) 555-2002', 1, 2),
('Samuel', 'Ortiz', 'churchadmin@gmail.com', '(217) 555-1003', '(217) 555-2003', 1, 3),
('Emma', 'Johnson', 'emma.johnson@gmail.com', '(217) 555-1004', '(217) 555-2004', 1, 4),
('Noah', 'Williams', 'noah.williams@gmail.com', '(217) 555-1005', '(217) 555-2005', 1, 5),
('Olivia', 'Brown', 'olivia.brown@gmail.com', '(217) 555-1006', '(217) 555-2006', 1, 6),
('Liam', 'Martinez', 'liam.martinez@gmail.com', '(217) 555-1007', '(217) 555-2007', 1, 7),
('Ava', 'Thomas', 'ava.thomas@gmail.com', '(217) 555-1008', '(217) 555-2008', 1, 8),
('Mason', 'Jackson', 'mason.jackson@gmail.com', '(217) 555-1009', '(217) 555-2009', 2, 9),
('Sophia', 'White', 'sophia.white@gmail.com', '(217) 555-1010', '(217) 555-2010', 2, 10),
('Ethan', 'Harris', 'ethan.harris@gmail.com', '(217) 555-1011', '(217) 555-2011', 2, 11),
('Mia', 'Clark', 'mia.clark@gmail.com', '(217) 555-1012', '(217) 555-2012', 2, 12),
('James', 'Lewis', 'james.lewis@gmail.com', '(217) 555-1013', '(217) 555-2013', 3, 13),
('Isabella', 'Walker', 'isabella.walker@gmail.com', '(217) 555-1014', '(217) 555-2014', 3, 14),
('Benjamin', 'Hall', 'benjamin.hall@gmail.com', '(217) 555-1015', '(217) 555-2015', 3, 15),
('Charlotte', 'Allen', 'charlotte.allen@gmail.com', '(217) 555-1016', '(217) 555-2016', 3, 16);

INSERT INTO groups (title, description, schedule, location, address, frequency, gender_id, church_id) VALUES
('Downtown Bible Study', 'Verse-by-verse Bible study for adults who work downtown.', 'Thursdays 7:00 PM', 'Grace Community Church - Room 204', '123 Main St, Springfield, IL 62701', 'WEEKLY', 3, 1),
('Mens Discipleship Breakfast', 'Prayer, accountability, and practical discipleship over breakfast.', 'Saturdays 7:30 AM', 'Grace Community Church Fellowship Hall', '123 Main St, Springfield, IL 62701', 'WEEKLY', 1, 1),
('Young Adults Connect', 'Conversation-based study for college students and young professionals.', 'Wednesdays 6:30 PM', 'The Lantern Cafe', '410 Market St, Springfield, IL 62702', 'WEEKLY', 3, 1),
('Family Fellowship Night', 'A small group for families with shared dinner and discussion.', 'Sundays 5:00 PM', 'Johnson Home', '1780 Maple Ridge Dr, Springfield, IL 62703', 'BIWEEKLY', 3, 1),
('Riverstone Moms Prayer', 'Support and prayer for moms in all stages of parenting.', 'Tuesdays 9:30 AM', 'Riverstone Fellowship - Classroom B', '845 Oak Ave, Springfield, IL 62703', 'WEEKLY', 2, 2),
('Saturday Community Outreach', 'Serve local shelters and food pantry partners across the city.', 'Saturdays 9:00 AM', 'Riverstone Fellowship Lobby', '845 Oak Ave, Springfield, IL 62703', 'MONTHLY', 3, 2),
('New Hope Seniors Circle', 'Coffee, devotional reading, and encouragement for senior adults.', 'Fridays 10:00 AM', 'New Hope Church - Senior Center', '2200 Lincoln Blvd, Springfield, IL 62704', 'WEEKLY', 3, 3),
('Worship and Prayer Night', 'Extended worship and intercessory prayer for the whole church.', 'First Monday 6:45 PM', 'New Hope Church Sanctuary', '2200 Lincoln Blvd, Springfield, IL 62704', 'MONTHLY', 3, 3);

INSERT INTO group_ages (group_id, age_id) VALUES (1, 1);
INSERT INTO group_ages (group_id, age_id) VALUES (1, 2);
INSERT INTO group_ages (group_id, age_id) VALUES (1, 3);
INSERT INTO group_ages (group_id, age_id) VALUES (2, 3);
INSERT INTO group_ages (group_id, age_id) VALUES (2, 4);
INSERT INTO group_ages (group_id, age_id) VALUES (2, 5);
INSERT INTO group_ages (group_id, age_id) VALUES (3, 2);
INSERT INTO group_ages (group_id, age_id) VALUES (3, 3);
INSERT INTO group_ages (group_id, age_id) VALUES (4, 1);
INSERT INTO group_ages (group_id, age_id) VALUES (4, 2);
INSERT INTO group_ages (group_id, age_id) VALUES (4, 3);
INSERT INTO group_ages (group_id, age_id) VALUES (4, 4);
INSERT INTO group_ages (group_id, age_id) VALUES (5, 3);
INSERT INTO group_ages (group_id, age_id) VALUES (5, 4);
INSERT INTO group_ages (group_id, age_id) VALUES (6, 1);
INSERT INTO group_ages (group_id, age_id) VALUES (6, 2);
INSERT INTO group_ages (group_id, age_id) VALUES (6, 3);
INSERT INTO group_ages (group_id, age_id) VALUES (7, 6);
INSERT INTO group_ages (group_id, age_id) VALUES (7, 7);
INSERT INTO group_ages (group_id, age_id) VALUES (8, 1);
INSERT INTO group_ages (group_id, age_id) VALUES (8, 2);
INSERT INTO group_ages (group_id, age_id) VALUES (8, 3);
INSERT INTO group_ages (group_id, age_id) VALUES (8, 4);
INSERT INTO group_ages (group_id, age_id) VALUES (8, 5);

INSERT INTO group_leaders (group_id, member_id) VALUES (1, 2);
INSERT INTO group_leaders (group_id, member_id) VALUES (2, 2);
INSERT INTO group_leaders (group_id, member_id) VALUES (3, 7);
INSERT INTO group_leaders (group_id, member_id) VALUES (3, 3);
INSERT INTO group_leaders (group_id, member_id) VALUES (4, 3);
INSERT INTO group_leaders (group_id, member_id) VALUES (5, 10);
INSERT INTO group_leaders (group_id, member_id) VALUES (6, 10);
INSERT INTO group_leaders (group_id, member_id) VALUES (7, 15);
INSERT INTO group_leaders (group_id, member_id) VALUES (8, 15);

INSERT INTO group_members (group_id, member_id) VALUES (1, 1);
INSERT INTO group_members (group_id, member_id) VALUES (1, 4);
INSERT INTO group_members (group_id, member_id) VALUES (1, 5);
INSERT INTO group_members (group_id, member_id) VALUES (1, 6);
INSERT INTO group_members (group_id, member_id) VALUES (1, 8);
INSERT INTO group_members (group_id, member_id) VALUES (2, 2);
INSERT INTO group_members (group_id, member_id) VALUES (2, 5);
INSERT INTO group_members (group_id, member_id) VALUES (2, 7);
INSERT INTO group_members (group_id, member_id) VALUES (3, 3);
INSERT INTO group_members (group_id, member_id) VALUES (3, 4);
INSERT INTO group_members (group_id, member_id) VALUES (3, 7);
INSERT INTO group_members (group_id, member_id) VALUES (3, 8);
INSERT INTO group_members (group_id, member_id) VALUES (4, 3);
INSERT INTO group_members (group_id, member_id) VALUES (4, 4);
INSERT INTO group_members (group_id, member_id) VALUES (4, 5);
INSERT INTO group_members (group_id, member_id) VALUES (4, 6);
INSERT INTO group_members (group_id, member_id) VALUES (5, 9);
INSERT INTO group_members (group_id, member_id) VALUES (5, 10);
INSERT INTO group_members (group_id, member_id) VALUES (5, 11);
INSERT INTO group_members (group_id, member_id) VALUES (5, 12);
INSERT INTO group_members (group_id, member_id) VALUES (6, 9);
INSERT INTO group_members (group_id, member_id) VALUES (6, 10);
INSERT INTO group_members (group_id, member_id) VALUES (6, 11);
INSERT INTO group_members (group_id, member_id) VALUES (6, 12);
INSERT INTO group_members (group_id, member_id) VALUES (7, 13);
INSERT INTO group_members (group_id, member_id) VALUES (7, 14);
INSERT INTO group_members (group_id, member_id) VALUES (7, 15);
INSERT INTO group_members (group_id, member_id) VALUES (7, 16);
INSERT INTO group_members (group_id, member_id) VALUES (8, 13);
INSERT INTO group_members (group_id, member_id) VALUES (8, 14);
INSERT INTO group_members (group_id, member_id) VALUES (8, 15);
INSERT INTO group_members (group_id, member_id) VALUES (8, 16);

INSERT INTO group_categories (group_id, category_id) VALUES (1, 1);
INSERT INTO group_categories (group_id, category_id) VALUES (2, 1);
INSERT INTO group_categories (group_id, category_id) VALUES (3, 1);
INSERT INTO group_categories (group_id, category_id) VALUES (3, 3);
INSERT INTO group_categories (group_id, category_id) VALUES (4, 1);
INSERT INTO group_categories (group_id, category_id) VALUES (4, 4);
INSERT INTO group_categories (group_id, category_id) VALUES (5, 4);
INSERT INTO group_categories (group_id, category_id) VALUES (6, 2);
INSERT INTO group_categories (group_id, category_id) VALUES (7, 1);
INSERT INTO group_categories (group_id, category_id) VALUES (7, 5);
INSERT INTO group_categories (group_id, category_id) VALUES (8, 1);
INSERT INTO group_categories (group_id, category_id) VALUES (8, 3);
