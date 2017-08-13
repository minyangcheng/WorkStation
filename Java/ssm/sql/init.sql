CREATE TABLE `class` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR (50) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

CREATE TABLE `student` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR (50) NOT NULL,
	`age` INT (11) NOT NULL,
	`c_id` INT (11) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

INSERT INTO class (name) VALUES('一班');
INSERT INTO class (name) VALUES('二班');
INSERT INTO class (name) VALUES('三班');

SELECT * FROM class;

INSERT INTO student (name,age,c_id) VALUES('小明',12,1);
INSERT INTO student (name,age,c_id) VALUES('小黄',12,1);
INSERT INTO student (name,age,c_id) VALUES('小李',12,2);
INSERT INTO student (name,age,c_id) VALUES('小黑',12,2);
INSERT INTO student (name,age,c_id) VALUES('小玲',12,2);
INSERT INTO student (name,age,c_id) VALUES('小成',12,3);
INSERT INTO student (name,age,c_id) VALUES('小张',12,3);

SELECT * FROM student;