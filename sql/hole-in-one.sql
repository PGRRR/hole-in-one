CREATE TABLE `members` (
	`member_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`email`	VARCHAR(100)	NOT NULL,
	`password`	VARCHAR(100)	NOT NULL,
	`name`	VARCHAR(100)	NOT NULL,
	`phone`	INT	NULL,
	`address`	VARCHAR(100)	NULL,
	`level`	INT	NOT NULL	DEFAULT 0,
	PRIMARY KEY(member_id)
);

CREATE TABLE `articles` (
	`article_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`article_title`	VARCHAR(100)	NOT NULL,
	`article_text`	TEXT	NOT NULL,
	`article_like`	INT	NOT NULL	DEFAULT 0,
	`member_id`	BIGINT	NOT NULL,
	PRIMARY KEY(article_id)
);

CREATE TABLE `comments` (
	`comment_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`comment_text`	TEXT	NOT NULL,
	`article_id`	BIGINT	NOT NULL,
	`member_id`	BIGINT	NOT NULL,
	PRIMARY KEY(comment_id)
);

CREATE TABLE `stores` (
	`store_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`store_name`	VARCHAR(100)	NOT NULL,
	`store_address`	VARCHAR(100)	NOT NULL,
	`store_phone`	INT	NOT NULL,
	PRIMARY KEY(store_id)
);

CREATE TABLE `reservations` (
	`reservation_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`reservation_price`	BIGINT	NOT NULL,
	`status`	ENUM('ready','finish')	NOT NULL	DEFAULT 'ready',
	`member_id`	BIGINT	NOT NULL,
	`store_id`	BIGINT	NOT NULL,
	PRIMARY KEY(reservation_id)
);

