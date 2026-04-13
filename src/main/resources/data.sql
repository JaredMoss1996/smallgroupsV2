



---------------- Required for Spring Security ----------------
-- Roles
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('LEADER');
INSERT INTO roles (name) VALUES ('CHURCH_ADMIN');
INSERT INTO roles (name) VALUES ('SUPER_ADMIN');

INSERT INTO permissions (name) VALUES ('GROUP_JOIN');
INSERT INTO permissions (name) VALUES ('GROUP_CREATE');
INSERT INTO permissions (name) VALUES ('GROUP_EDIT');
INSERT INTO permissions (name) VALUES ('GROUP_DELETE');
INSERT INTO permissions (name) VALUES ('LEADER_ASSIGN');
INSERT INTO permissions (name) VALUES ('LEADER_REMOVE');
INSERT INTO permissions (name) VALUES ('CHURCH_EDIT');
INSERT INTO permissions (name) VALUES ('CHURCH_ADD');
INSERT INTO permissions (name) VALUES ('CHURCH_REMOVE');

INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1); -- USER can join groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 1); -- LEADER can join groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 2); -- LEADER can create groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 3); -- LEADER can edit groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 4); -- LEADER can delete groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 1); -- CHURCH_ADMIN can join groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 2); -- CHURCH_ADMIN can create groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 3); -- CHURCH_ADMIN can edit groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 4); -- CHURCH_ADMIN can delete groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 5); -- CHURCH_ADMIN can assign leaders
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 6); -- CHURCH_ADMIN can remove leaders
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 7); -- CHURCH_ADMIN can edit church
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 1); -- SUPER_ADMIN can join groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 2); -- SUPER_ADMIN can create groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 3); -- SUPER_ADMIN can edit groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 4); -- SUPER_ADMIN can delete groups
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 5); -- SUPER_ADMIN can assign leaders
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 6); -- SUPER_ADMIN can remove leaders
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 7); -- SUPER_ADMIN can edit church
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 8); -- SUPER_ADMIN can add church
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 9); -- SUPER_ADMIN can remove church



---------------- Test Data ----------------
-- Groups
INSERT INTO groups (title, description, schedule, location, address, contact_info, gender, age, frequency) VALUES
('Downtown Bible Study', 'A weekly Bible study in downtown.', 'Thursdays 7pm', 'Downtown Church', '123 Main St', 'alice@example.com', 'MALE', 'ALL_ADULT_AGES', 'DAILY'),
('Neighborhood Outreach', 'Service projects around the neighborhood.', 'Saturdays 9am', 'Community Center', '456 Oak Ave', 'bob@example.com', 'MALE', 'ALL_ADULT_AGES', 'MONTHLY'),
('Young Adults Connect', 'Young adults fellowship and study.', 'Wednesdays 6:30pm', 'Cafe Corner', '789 Pine Rd', 'carol@example.com', 'FEMALE', 'TWENTIES', 'WEEKLY'),
('Family Fellowship', 'Groups for families with kids.', 'Sundays 5pm', 'Parish Hall', '101 Maple Dr', 'david@example.com', 'FEMALE', 'FIFTIES', 'BIWEEKLY'),
('Seniors Breakfast', 'Breakfast and discussion for seniors. faucibus augue at, ultrices libero. Curabitur in scelerisque lectus. Praesent tincidunt nisl vitae consequat congue. Nunc a finibus sem. Duis quam sem, faucibus ut vestibulum non, blandit ut leo. Duis consectetur sapien eu tortor eleifend, nec malesuada lacus sagittis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed pulvinar, justo et eleifend rutrum, justo velit pretium sem, eget suscipit nibh turpis feugiat felis. Maecenas iaculis dui dignissim orci tincidunt condimentum. In hac habitasse platea dictumst. Phasellus ante lacus, dignissim ac placerat at, cursus ac odio. Sed sodales, nisi at porttitor tincidunt, arcu mauris lacinia ex, sit amet iaculis nisl mi sed massa. Etiam vehicula, nulla ac pretium sodales, quam arcu sodales nulla, sed sempe',
 'Fridays 8am', 'Senior Center', '202 Elm St', 'eve@example.com', 'BOTH', 'SEVENTIES_AND_UP', 'VARIES');


-- Leaders
-- INSERT INTO group_leaders (group_id, leader_id) VALUES (1, 1);

INSERT INTO category (name) VALUES ('Bible Study');
INSERT INTO category (name) VALUES ('Community Service');
INSERT INTO category (name) VALUES ('Young Adults');
INSERT INTO category (name) VALUES ('Families');
INSERT INTO category (name) VALUES ('Seniors');

-- Categories
INSERT INTO group_categories (group_id, category_id) VALUES (1,1);
INSERT INTO group_categories (group_id, category_id) VALUES (1,2);
INSERT INTO group_categories (group_id, category_id) VALUES (2,2);
INSERT INTO group_categories (group_id, category_id) VALUES (2,3);
INSERT INTO group_categories (group_id, category_id) VALUES (3,5);
INSERT INTO group_categories (group_id, category_id) VALUES (3,1);
INSERT INTO group_categories (group_id, category_id) VALUES (3,4);
INSERT INTO group_categories (group_id, category_id) VALUES (4,4);
INSERT INTO group_categories (group_id, category_id) VALUES (5,4);
INSERT INTO group_categories (group_id, category_id) VALUES (5,3);




--