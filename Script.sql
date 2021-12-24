

SELECT * FROM university.Student;
SELECT * FROM university.Person;
SELECT * FROM university.Teacher;
SELECT * FROM university.Enrolment;
SELECT * FROM university.Course;


INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('1', 'amalia', 'pop');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('2', 'bianca', 'pop');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('3', 'amalia', 'pop');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('4', 'diana', 'cristea');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('5', 'andrei', 'pop');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('6', 'madalina', 'pop');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('8', 'amalia', 'pop');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('9', 'amalia', 'pop');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('25', 'amalia', 'bianca');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('26', 'amalia', 'pop');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('29', 'camelia', 'oanta');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('30', 'camelia', 'oanta');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('53', 'camelia', 'oanta');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('55', 'anamaria', 'rot');
INSERT INTO `university`.`Person` (`ID`, `firstName`, `lastName`) VALUES ('56', 'camelia', 'oanta');


INSERT INTO `university`.`Student` (`studentId`, `totalCredits`, `personId`) VALUES ('1', '21', '1');
INSERT INTO `university`.`Student` (`studentId`, `totalCredits`, `personId`) VALUES ('4', '33', '3');
INSERT INTO `university`.`Student` (`studentId`, `totalCredits`, `personId`) VALUES ('5', '33', '2');


INSERT INTO `university`.`Course` (`id`, `title`, `credits`,`teacherId`,`maxStudents`) VALUES ('11', 'MAP', '6','21','30');
INSERT INTO `university`.`Course` (`id`, `title`, `credits`,`teacherId`,`maxStudents`) VALUES ('12', 'LP', '6','29','30');
INSERT INTO `university`.`Course` (`id`, `title`, `credits`,`teacherId`,`maxStudents`) VALUES ('13', 'MAP', '6','29','30');
INSERT INTO `university`.`Course` (`id`, `title`, `credits`,`teacherId`,`maxStudents`) VALUES ('14', 'RC', '6','29','30');

INSERT INTO `university`.`Teacher` (`personId`, `teacherId`) VALUES ('1', '99');
INSERT INTO `university`.`Teacher` (`personId`, `teacherId`) VALUES ('3', '21');
INSERT INTO `university`.`Teacher` (`personId`, `teacherId`) VALUES ('4', '22');
INSERT INTO `university`.`Teacher` (`personId`, `teacherId`) VALUES ('5', '5');
INSERT INTO `university`.`Teacher` (`personId`, `teacherId`) VALUES ('6', '9');
INSERT INTO `university`.`Teacher` (`personId`, `teacherId`) VALUES ('26', '8');
INSERT INTO `university`.`Teacher` (`personId`, `teacherId`) VALUES ('30', '29');







INSERT INTO `university`.`Enrolment` (`studentId`, `courseId`) VALUES ('1', '11');

