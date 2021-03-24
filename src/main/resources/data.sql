INSERT INTO users (is_moderator,code,reg_time,name,email,password,photo) VALUES (0,null,CAST('2019-06-25 15:32:06.427' AS DateTime),'Алёша Попович','mail@mail.ru','$2y$12$ZlJ8EyU1g55DguscyX7iveZWmVaTd5aNd/cf0VmCc7lwq47wkzLBe',null);
INSERT INTO users (is_moderator,code,reg_time,name,email,password,photo) VALUES (1,'privet',CAST('2019-06-25 15:33:06.427' AS DateTime),'Линус Торвальдс','lt@gmail.com','$2y$12$W3CBs9KViDstqu/Yb74jXOdfvNQt6ChW4I3aPEWCZ4kk66okAjVBy',null);
INSERT INTO users (is_moderator,code,reg_time,name,email,password,photo) VALUES (0,null,CAST('2019-08-20 15:33:06.427' AS DateTime),'Vasya Sidorov','mail3@mail.ru','$2y$12$W3CBs9KViDstqu/Yb74jXOdfvNQt6ChW4I3aPEWCZ4kk66okAjVBy',null);
INSERT INTO users (is_moderator,code,reg_time,name,email,password,photo) VALUES (1,null,CAST('2019-09-10 10:33:06.427' AS DateTime),'Margo','mr@gmail.com','$2y$12$W3CBs9KViDstqu/Yb74jXOdfvNQt6ChW4I3aPEWCZ4kk66okAjVBy',null);
INSERT INTO users (is_moderator,code,reg_time,name,email,password,photo) VALUES (0,null,CAST('2019-10-05 10:45:06.427' AS DateTime),'Петя','mail5@mail.ru','$2y$12$W3CBs9KViDstqu/Yb74jXOdfvNQt6ChW4I3aPEWCZ4kk66okAjVBy',null);
INSERT INTO users (is_moderator,code,reg_time,name,email,password,photo) VALUES (0,null,CAST('2020-11-05 10:45:06.427' AS DateTime),'Pasha','mail6@mail.ru','$2y$12$W3CBs9KViDstqu/Yb74jXOdfvNQt6ChW4I3aPEWCZ4kk66okAjVBy',null);

INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Самая 1 запись' , CAST('2019-06-29 18:32:06.427' AS DateTime), 'Первая запись', 2,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Очень <b>2 запись</b> <u>запись</u> Сразу после завершения работы над стандартом SQL1 в 1987 году была начата работа над новой версией стандарта, который должен был заменить стандарт SQL89, получив название SQL2, поскольку дата принятия документа на тот момент была неизвестна.' , CAST('2019-12-29 19:32:06.427' AS DateTime), 'Вторая запись', 5,2 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'DECLINED', 2, 'Самая 3 запись' , CAST('2019-12-30 19:32:06.427' AS DateTime), 'Третья запись', 0,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это <b>4 запись</b>' , CAST('2020-02-05 15:32:06.427' AS DateTime), 'Четвертая запись', 6,4 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 5 запись' , CAST('2021-02-06 15:32:06.427' AS DateTime), 'Пятая запись', 2,3 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 6 запись' , CAST('2021-02-06 15:37:06.427' AS DateTime), 'Шестая запись', 4,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 7 запись' , CAST('2021-02-06 16:37:06.427' AS DateTime), 'Седьмая запись', 4,2 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 8 запись' , CAST('2021-02-06 16:38:06.427' AS DateTime), 'Восьмая запись', 4,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 9 запись' , CAST('2021-02-06 18:38:06.427' AS DateTime), 'Девятая запись', 5,3 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 10 запись' , CAST('2021-02-07 16:38:06.427' AS DateTime), 'Десятая запись', 4,2 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 11 запись' , CAST('2021-02-07 17:38:06.427' AS DateTime), '11 запись', 4,2 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'DECLINED', 2, 'Это <b>12 запись</b> Сразу после завершения работы над стандартом SQL1 в 1987 году была начата работа над новой версией стандарта, который должен был заменить стандарт SQL89, получив название SQL2, поскольку дата принятия документа на тот момент была неизвестна.' , CAST('2021-02-07 19:38:06.427' AS DateTime), '12 запись', 0,4 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 13 <u>запись</u> Сразу после завершения работы над стандартом SQL1 в 1987 году была начата работа над новой версией стандарта, который должен был заменить стандарт SQL89, получив название SQL2, поскольку дата принятия документа на тот момент была неизвестна.' , CAST('2021-02-07 20:38:06.427' AS DateTime), '13 запись', 4,2 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 14 запись ' , CAST('2021-02-07 21:38:06.427' AS DateTime), '14 запись', 2,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 15 запись' , CAST('2021-02-07 22:38:06.427' AS DateTime), '15 запись', 2,2 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 16 запись' , CAST('2021-02-07 22:39:06.427' AS DateTime), '16 запись', 2,4 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 17 запись' , CAST('2021-02-07 22:40:06.427' AS DateTime), '17 запись да', 2,3 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 18 запись' , CAST('2021-02-07 22:41:06.427' AS DateTime), '18 запись', 2,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 19 запись' , CAST('2021-02-07 22:42:06.427' AS DateTime), '19 запись', 3,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 20 запись' , CAST('2021-02-07 22:43:06.427' AS DateTime), '20 запись', 2,3 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'DECLINED', 4, 'Это 21 запись' , CAST('2021-02-07 22:44:06.427' AS DateTime), '21 запись', 2,4 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 22 запись да' , CAST('2021-02-07 22:45:06.427' AS DateTime), '22 запись', 2,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 23 запись' , CAST('2021-02-07 22:46:06.427' AS DateTime), '23 запись', 4,2 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 24 запись' , CAST('2021-02-07 22:47:06.427' AS DateTime), '24 запись', 2,3 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 25 запись' , CAST('2021-02-07 22:48:06.427' AS DateTime), '25 запись', 2,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 4, 'Это 26 запись' , CAST('2021-02-07 22:49:06.427' AS DateTime), '26 запись', 3,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 27 запись' , CAST('2021-02-07 22:50:06.427' AS DateTime), '27 запись', 2,3 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 4, 'Это 28 запись' , CAST('2021-02-07 22:51:06.427' AS DateTime), '28 запись', 2,4 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 29 запись' , CAST('2021-02-07 22:52:06.427' AS DateTime), '29 запись', 2,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, '<b>Это 30</b> <u>запись</u> Сразу после завершения работы над стандартом SQL1 в 1987 году была начата работа над новой версией стандарта, который должен был заменить стандарт SQL89' , CAST('2021-02-07 22:53:06.427' AS DateTime), '30 запись', 4,2 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'NEW', null, 'Это 31 запись' , CAST('2021-02-08 22:38:06.427' AS DateTime), '31 запись', 0,3 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (0, 'NEW', null, 'Это 32 запись' , CAST('2021-05-08 22:38:06.427' AS DateTime), '32 запись', 0,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (0, 'NEW', null, 'Это 33 запись' , CAST('2021-05-08 22:48:06.427' AS DateTime), '33 запись', 0,3 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'NEW', null, 'Это 34 запись' , CAST('2021-05-08 22:38:06.427' AS DateTime), '34 запись', 0,1 );
INSERT INTO posts (is_active,moderation_status,moderator_id,text,time,title,view_count,user_id) VALUES (1, 'ACCEPTED', 2, 'Это 35 запись' , CAST('2021-05-08 23:38:06.427' AS DateTime), '35 запись', 0,3 );

INSERT INTO post_votes (user_id, post_id, time, value) VALUES (2, 1, CAST('2019-06-30 19:33:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (1, 1, CAST('2019-06-30 19:34:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (1, 2, CAST('2019-12-30 19:42:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (3, 2, CAST('2021-01-16 19:42:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (3, 4, CAST('2021-02-05 15:40:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (1, 4, CAST('2021-02-05 15:41:06.427' AS DateTime),-1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (2, 4, CAST('2021-02-05 16:40:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (1, 5, CAST('2021-02-06 16:41:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (2, 5, CAST('2021-02-06 16:42:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (3, 5, CAST('2021-02-06 16:43:08.427' AS DateTime),-1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (4, 5, CAST('2021-02-06 16:43:09.427' AS DateTime),-1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (1, 6, CAST('2021-02-07 16:45:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (2, 6, CAST('2021-02-07 16:46:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (4, 6, CAST('2021-02-07 16:47:08.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (3, 6, CAST('2021-02-07 16:48:09.427' AS DateTime),-1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (1, 7, CAST('2021-02-07 16:51:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (2, 7, CAST('2021-02-07 16:52:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (4, 7, CAST('2021-02-07 16:53:08.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (1, 8, CAST('2021-02-07 16:55:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (2, 8, CAST('2021-02-07 16:56:06.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (4, 8, CAST('2021-02-07 16:57:08.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (3, 8, CAST('2021-02-07 16:58:09.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (1, 9, CAST('2021-02-07 17:55:06.427' AS DateTime),-1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (2, 9, CAST('2021-02-07 17:56:06.427' AS DateTime),-1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (4, 9, CAST('2021-02-07 17:57:08.427' AS DateTime),-1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (3, 9, CAST('2021-02-07 17:58:09.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (1, 10, CAST('2021-02-07 17:55:06.427' AS DateTime),-1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (3, 10, CAST('2021-02-07 17:58:09.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (4, 12, CAST('2021-02-08 17:57:08.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (3, 12, CAST('2021-02-08 17:58:09.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (1, 15, CAST('2021-02-08 18:57:08.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (3, 15, CAST('2021-02-08 18:58:09.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (2, 30, CAST('2021-02-08 18:58:09.427' AS DateTime),1);
INSERT INTO post_votes (user_id, post_id, time, value) VALUES (3, 30, CAST('2021-02-08 18:59:09.427' AS DateTime),-1);

INSERT INTO post_comments (parent_id, text, time, post_id, user_id) VALUES (null, 'Классный пост', CAST('2019-12-28 15:42:06.427' AS DateTime),1,2);
INSERT INTO post_comments (parent_id, text, time, post_id, user_id) VALUES (1, 'Спасибо', CAST('2019-12-28 15:42:06.427' AS DateTime),1,1);
INSERT INTO post_comments (parent_id, text, time, post_id, user_id) VALUES (2, 'Пожалуйста', CAST('2020-12-28 15:45:06.427' AS DateTime),1,2);
INSERT INTO post_comments (parent_id, text, time, post_id, user_id) VALUES (null, '4 Классный пост', CAST('2021-02-15 15:52:06.427' AS DateTime),4,3);
INSERT INTO post_comments (parent_id, text, time, post_id, user_id) VALUES (null, '14 Классный пост',  CAST('2021-02-07 21:48:06.427' AS DateTime),14,4);
INSERT INTO post_comments (parent_id, text, time, post_id, user_id) VALUES (null, '30 Классный пост - 3!!!', CAST('2021-02-08 15:42:06.427' AS DateTime),30,3);
INSERT INTO post_comments (parent_id, text, time, post_id, user_id) VALUES (null, '30 Классный пост - 4!!!', CAST('2021-02-08 15:43:06.427' AS DateTime),30,4);
INSERT INTO post_comments (parent_id, text, time, post_id, user_id) VALUES (6, 'Спасибо - 3!!!', CAST('2021-02-09 15:42:06.427' AS DateTime),30,2);
INSERT INTO post_comments (parent_id, text, time, post_id, user_id) VALUES (7, 'Спасибо - 4!!!', CAST('2021-02-09 15:43:06.427' AS DateTime),30,2);

INSERT INTO tags (name) VALUES ('javacore');
INSERT INTO tags (name) VALUES ('java');
INSERT INTO tags (name) VALUES ('spring');
INSERT INTO tags (name) VALUES ('hibernate');
INSERT INTO tags (name) VALUES ('javadoc');
INSERT INTO tags (name) VALUES ('springcore');
INSERT INTO tags (name) VALUES ('springboot');

INSERT INTO tag2post (post_id, tag_id) VALUES (1,1);
INSERT INTO tag2post (post_id, tag_id) VALUES (1,2);
INSERT INTO tag2post (post_id, tag_id) VALUES (1,5);
INSERT INTO tag2post (post_id, tag_id) VALUES (1,6);
INSERT INTO tag2post (post_id, tag_id) VALUES (4,4);
INSERT INTO tag2post (post_id, tag_id) VALUES (4,3);
INSERT INTO tag2post (post_id, tag_id) VALUES (5,1);
INSERT INTO tag2post (post_id, tag_id) VALUES (5,2);
INSERT INTO tag2post (post_id, tag_id) VALUES (5,3);
INSERT INTO tag2post (post_id, tag_id) VALUES (5,4);
INSERT INTO tag2post (post_id, tag_id) VALUES (6,2);
INSERT INTO tag2post (post_id, tag_id) VALUES (7,2);
INSERT INTO tag2post (post_id, tag_id) VALUES (8,2);
INSERT INTO tag2post (post_id, tag_id) VALUES (8,1);
INSERT INTO tag2post (post_id, tag_id) VALUES (9,2);
INSERT INTO tag2post (post_id, tag_id) VALUES (9,4);
INSERT INTO tag2post (post_id, tag_id) VALUES (9,6);
INSERT INTO tag2post (post_id, tag_id) VALUES (10,3);
INSERT INTO tag2post (post_id, tag_id) VALUES (11,2);
INSERT INTO tag2post (post_id, tag_id) VALUES (11,1);
INSERT INTO tag2post (post_id, tag_id) VALUES (12,2);
INSERT INTO tag2post (post_id, tag_id) VALUES (13,2);
INSERT INTO tag2post (post_id, tag_id) VALUES (14,2);
INSERT INTO tag2post (post_id, tag_id) VALUES (14,3);
INSERT INTO tag2post (post_id, tag_id) VALUES (14,1);
INSERT INTO tag2post (post_id, tag_id) VALUES (14,5);
INSERT INTO tag2post (post_id, tag_id) VALUES (15,2);
INSERT INTO tag2post (post_id, tag_id) VALUES (17,2);
INSERT INTO tag2post (post_id, tag_id) VALUES (19,1);
INSERT INTO tag2post (post_id, tag_id) VALUES (25,7);
INSERT INTO tag2post (post_id, tag_id) VALUES (26,7);
INSERT INTO tag2post (post_id, tag_id) VALUES (30,5);