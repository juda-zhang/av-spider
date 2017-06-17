CREATE TABLE `product` (
  `id`            BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `code`          VARCHAR(20)  NOT NULL
  COMMENT '影片编码',
  `title`         VARCHAR(200) NULL
  COMMENT '标题',
  `series`        VARCHAR(150) NULL
  COMMENT '系列',
  `actress_name`  VARCHAR(60)  NULL
  COMMENT '主要女演员名称',
  `actor_name`    VARCHAR(60)  NULL
  COMMENT '主要男演员名称',
  `manufacturer`  VARCHAR(60)  NULL
  COMMENT '制片商',
  `producer`      VARCHAR(60)  NULL
  COMMENT '出品方',
  `region`        INT          NULL
  COMMENT '区域。0:亚洲 1:欧美 2:大陆 3:港台 4:东南亚 5:中亚 6:南美 7:其他',
  `censored_type` INT          NULL
  COMMENT '0：无码 1:有码',
  `type`          INT          NULL
  COMMENT '0:成人 1:素人 2:三级 3:写真',
  `director`      VARCHAR(60)  NULL
  COMMENT '导演',
  `duration`      INT UNSIGNED NULL
  COMMENT '片长，分钟为单位',
  `issue_date`    VARCHAR(20)  NULL
  COMMENT '发行日期,YYYY-MM-DD',
  `gmt_created`   DATETIME     NULL
  COMMENT '创建时间',
  `gmt_modified`  DATETIME     NULL
  COMMENT '修改时间',
  `version`       INT          NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk` (`code`)
);

CREATE TABLE `product_img` (
  `id`           BIGINT        NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `product_Id`   BIGINT        NOT NULL
  COMMENT '影片编码',
  `url`          VARCHAR(1000) NULL
  COMMENT '链接',
  `type`         INT           NULL
  COMMENT '0:封面 1:封底 2:内容预览',
  `gmt_created`  DATETIME      NULL
  COMMENT '创建时间',
  `gmt_modified` DATETIME      NULL
  COMMENT '修改时间',
  `version`      INT           NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `product_tag` (
  `id`           BIGINT   NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `product_Id`   BIGINT   NOT NULL
  COMMENT '影片编码',
  `tag_Id`       BIGINT   NOT NULL
  COMMENT '标签id',
  `gmt_created`  DATETIME NULL
  COMMENT '创建时间',
  `gmt_modified` DATETIME NULL
  COMMENT '修改时间',
  `version`      INT      NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `product_actress` (
  `id`           BIGINT   NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `product_Id`   BIGINT   NOT NULL
  COMMENT '影片id',
  `actress_Id`   BIGINT   NOT NULL
  COMMENT '女演员id',
  `gmt_created`  DATETIME NULL
  COMMENT '创建时间',
  `gmt_modified` DATETIME NULL
  COMMENT '修改时间',
  `version`      INT      NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `tag` (
  `id`            BIGINT      NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `name`          VARCHAR(60) NOT NULL
  COMMENT '标签名称',
  `english_name`  VARCHAR(60) NULL
  COMMENT '标题',
  `japanese_name` VARCHAR(60) NULL
  COMMENT '系列',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_name` (`name`),
  UNIQUE INDEX `uk_english_name` (`english_name`),
  UNIQUE INDEX `uk_japanese_name` (`japanese_name`)
);

CREATE TABLE `actress` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `name`         VARCHAR(200) NOT NULL
  COMMENT '演员唯一现用名，可能为中文，日文，英文',
  `birthday`     VARCHAR(20)  NULL
  COMMENT '生日YYYY-MM-DD',
  `cup`          VARCHAR(2)   NULL
  COMMENT '罩杯',
  `actress_name` VARCHAR(60)  NULL
  COMMENT '主要女演员名称',
  `height`       INT          NULL
  COMMENT '身高，单位cm',
  `region`       INT          NULL
  COMMENT '0:亚洲 1:欧美 2:大陆 3:港台 4:东南亚 5:中亚 6:南美 7:非洲 99:其他',
  `bust`         INT          NULL
  COMMENT '胸围',
  `waist`        INT          NULL
  COMMENT '腰围',
  `hips`         INT          NULL
  COMMENT '臀围',
  `gmt_created`  DATETIME     NULL
  COMMENT '创建时间',
  `gmt_modified` DATETIME     NULL
  COMMENT '修改时间',
  `version`      INT          NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_name` (`name`)
);