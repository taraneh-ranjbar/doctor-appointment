INSERT INTO specialist (id, code, name) values (1L, '123','Anesthesiology');
INSERT INTO specialist (id, code, name) values (2L, '124','Family Medicine');
INSERT INTO specialist (id, code, name) values (3L, '125','General Surgery');

INSERT INTO doctor (id, FULL_NAME, specialist_id) values (101L, 'doctorNameTest1',1L);
INSERT INTO doctor (id, FULL_NAME, specialist_id) values (202L, 'doctorNameTest2',2L);

