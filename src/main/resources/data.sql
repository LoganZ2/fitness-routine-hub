-- 插入 Users 表数据
INSERT INTO Users (username, description, created_at, update_at)
VALUES ('john_doe', 'Fitness enthusiast', '2023-03-15T08:00:00Z', '2023-03-16T08:00:00Z');

INSERT INTO Users (username, description, created_at, update_at)
VALUES ('jane_smith', 'Yoga lover', '2023-03-15T09:00:00Z', NULL);

INSERT INTO Users (username, description, created_at, update_at)
VALUES ('alex_lee', 'Runner and cyclist', '2023-03-15T10:00:00Z', '2023-03-16T10:00:00Z');

-- 插入 health_profile 表数据（使用 ordinal 值存储枚举）
-- Gender: MALE=0, FEMALE=1, OTHER=2
-- HeightUnit: CM=0
-- WeightUnit: KG=0
-- Objective: BULK=0, CUT=1, MAINTAIN=2
INSERT INTO health_profile (id, gender, height, height_unit, weight, weight_unit, age, objective)
VALUES (1, 0, 180, 0, 75, 0, 30, 2);

INSERT INTO health_profile (id, gender, height, height_unit, weight, weight_unit, age, objective)
VALUES (2, 1, 165, 0, 60, 0, 28, 1);

INSERT INTO health_profile (id, gender, height, height_unit, weight, weight_unit, age, objective)
VALUES (3, 2, 170, 0, 68, 0, 35, 0);

-- 插入 day_info 表数据
INSERT INTO day_info (id, user_id, date, challenge_completed, net_calories)
VALUES (1, 1, '2023-03-15T08:00:00Z', TRUE, 1500.0);

INSERT INTO day_info (id, user_id, date, challenge_completed, net_calories)
VALUES (2, 2, '2023-03-15T09:00:00Z', FALSE, 1200.0);

INSERT INTO day_info (id, user_id, date, challenge_completed, net_calories)
VALUES (3, 3, '2023-03-15T10:00:00Z', TRUE, 1800.0);

-- 插入 Posts 表数据（使用 ordinal 值存储枚举 PostType）
-- 根据枚举顺序：DISCUSSION=0, QUESTION=1, GUIDE=2, LOG=3, REVIEW=4
INSERT INTO Posts (id, user_id, title, type, body, created_at, update_at)
VALUES (1, 1, 'My First Post', 0, 'Starting my fitness journey!', '2023-03-15T08:30:00Z', '2023-03-15T09:00:00Z');

INSERT INTO Posts (id, user_id, title, type, body, created_at, update_at)
VALUES (2, 2, 'Yoga Tips', 2, 'Sharing some yoga tips for beginners.', '2023-03-15T09:30:00Z', NULL);

INSERT INTO Posts (id, user_id, title, type, body, created_at, update_at)
VALUES (3, 3, 'Running Schedule', 1, 'What is the best running schedule?', '2023-03-15T10:30:00Z', '2023-03-15T11:00:00Z');

-- 插入 Replies 表数据
INSERT INTO Replies (post_id, content, created_at)
VALUES (1, 'Great start!',  '2023-03-15T09:10:00Z');

INSERT INTO Replies (post_id, content, created_at)
VALUES (1, 'Keep it up!', '2023-03-15T09:20:00Z');

INSERT INTO Replies (post_id, content, created_at)
VALUES (2, 'Thanks for the tips!', '2023-03-15T09:40:00Z');

INSERT INTO Replies (post_id, content, created_at)
VALUES (3, 'I follow a similar schedule.', '2023-03-15T11:10:00Z');

INSERT INTO Replies (post_id, content, created_at)
VALUES (3, 'Maybe try interval training?', '2023-03-15T11:20:00Z');


