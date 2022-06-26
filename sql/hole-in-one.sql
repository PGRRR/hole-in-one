CREATE TABLE `members` (
	`member_id`	BIGINT	NOT NULL,
	`email`	VARCHAR(100)	NOT NULL,
	`password`	VARCHAR(100)	NOT NULL,
	`name`	VARCHAR(100)	NOT NULL,
	`phone`	INT	NULL,
	`address`	VARCHAR(100)	NULL,
	`level`	INT	NOT NULL	DEFAULT 0
);

CREATE TABLE `articles` (
	`article_id`	BIGINT	NOT NULL,
	`article_title`	VARCHAR(100)	NOT NULL,
	`article_text`	TEXT	NOT NULL,
	`article_like`	INT	NOT NULL	DEFAULT 0,
	`member_id`	BIGINT	NOT NULL
);

CREATE TABLE `comments` (
	`comment_id`	BIGINT	NOT NULL,
	`comment_text`	TEXT	NOT NULL,
	`article_id`	BIGINT	NOT NULL,
	`member_id`	BIGINT	NOT NULL
);

CREATE TABLE `stores` (
	`store_id`	BIGINT	NOT NULL,
	`store_name`	VARCHAR(100)	NOT NULL,
	`store_address`	VARCHAR(100)	NOT NULL,
	`store_phone`	INT	NOT NULL
);

CREATE TABLE `reservations` (
	`reservation_id`	BIGINT	NOT NULL,
	`reservation_price`	BIGINT	NOT NULL,
	`status`	ENUM('ready','finish')	NOT NULL	DEFAULT 'ready',
	`member_id`	BIGINT	NOT NULL,
	`store_id`	BIGINT	NOT NULL
);

ALTER TABLE `members` ADD CONSTRAINT `PK_MEMBERS` PRIMARY KEY (
	`member_id`
);

ALTER TABLE `articles` ADD CONSTRAINT `PK_ARTICLES` PRIMARY KEY (
	`article_id`
);

ALTER TABLE `comments` ADD CONSTRAINT `PK_COMMENTS` PRIMARY KEY (
	`comment_id`
);

ALTER TABLE `stores` ADD CONSTRAINT `PK_STORES` PRIMARY KEY (
	`store_id`
);

ALTER TABLE `reservations` ADD CONSTRAINT `PK_RESERVATIONS` PRIMARY KEY (
	`reservation_id`
);

