CREATE TABLE `movie` (
  `id`            BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `code_prefix`   VARCHAR(10)  NULL
  COMMENT '编号的前缀',
  `code`          VARCHAR(15)  NOT NULL
  COMMENT '影片编码',
  `title`         VARCHAR(150) NULL
  COMMENT '标题',
  `series`        VARCHAR(150) NULL,
  `main_actress`  VARCHAR(60)  NULL
  COMMENT '主要女演员名称',
  `main_actor`    VARCHAR(60)  NULL
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
  `issue_date`    DATETIME     NULL
  COMMENT '发行日期',
  `gmt_created`   DATETIME     NULL
  COMMENT '创建时间',
  `gmt_modified`  DATETIME     NULL
  COMMENT '修改时间',
  `version`       INT          NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk` (`code`, `manufacturer`)
);
