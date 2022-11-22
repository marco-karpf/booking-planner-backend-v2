insert into users (id, age, bookingreason, email, firstname, image, lastname, password, profession, role, username)
values (1455, 58, 'Scrum Master Meeting','john.doe@mail.com','John','https://www.w3schools.com/howto/img_avatar.png','Doe','123','scrum Master','ADMIN','john');

insert into users (id, age, bookingreason, email, firstname, image, lastname, password, profession, role, username)
values (265166, 60, 'PI Planning Meeting','jane.doe@mail.com','Jane','https://www.w3schools.com/howto/img_avatar.png','Doe','123','Software Engineer','MEMBER','jane');

insert into users (id, age, bookingreason, email, firstname, image, lastname, password, profession, role, username)
values (3865131, 49, 'PO Recruiting','jack.doe@mail.com','Jack','https://www.w3schools.com/howto/img_avatar.png','Doe','123','Product Owner','MEMBER','jack');

INSERT INTO status (id, status)
VALUES (1, 'pending');

INSERT INTO status (id, status)
VALUES (2, 'approved');

INSERT INTO status (id, status)
VALUES (3, 'cancelled');

INSERT INTO status (id, status)
VALUES (2134, 'requested for cancellation');

insert into booking (id, title, status_id, user_id)
values (1213, 'Review Retro Planning 1', 1, 265166);

insert into booking (id, title, status_id, user_id)
values (2123, 'Review Retro Planning 2 ', 1, 265166);

insert into booking (id, title, status_id, user_id)
values (321312, 'Review Retro Planning 3', 1, 265166);

insert into booking (id, title, status_id, user_id)
values (43533454, 'PO Recruiting 1', 1, 3865131);

insert into booking (id, title, status_id, user_id)
values (56765, 'PO Recruiting 2', 1, 3865131);

insert into bookingdate (id, enddatetime, startdatetime, booking_id)
values (134554, '2022-12-12 12:00:00', '2022-12-12 11:00:00', 1213);

insert into bookingdate (id, enddatetime, startdatetime, booking_id)
values (267876, '2022-12-10 12:00:00', '2022-12-10 11:00:00', 2123);

insert into bookingdate (id, enddatetime, startdatetime, booking_id)
values (6787683, '2022-12-09 12:00:00', '2022-12-09 11:00:00', 321312);

insert into bookingdate (id, enddatetime, startdatetime, booking_id)
values (34534, '2022-12-09 14:00:00', '2022-12-09 13:00:00', 43533454);

insert into bookingdate (id, enddatetime, startdatetime, booking_id)
values (6577565, '2022-11-09 13:00:00', '2022-11-09 10:00:00', 56765);

insert into bookingdate (id, enddatetime, startdatetime, booking_id)
values (79786856, '2022-11-10 15:00:00', '2022-11-10 11:00:00', 56765);
