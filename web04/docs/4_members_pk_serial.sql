-- MEMBERS의 PK 자동증가 (Serial)
ALTER TABLE members
	MODIFY COLUMN MNO INTEGER NOT NULL AUTO_INCREMENT
	COMMENT '회원일련번호';