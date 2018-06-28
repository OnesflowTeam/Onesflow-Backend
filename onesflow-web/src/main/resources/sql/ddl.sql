-- 创建时间 2017/9/15
-- 版本号 v2

-- -------------------------------------------------------------------------- 角色,权限部分  ----------------------------------------------------------------------------
-- 老式的Guard插件来使用
-- 角色表
--
-- CREATE TABLE `role` (
--   `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
--   `name`         VARCHAR(32)     NOT NULL,
--   `description`  VARCHAR(128)    NOT NULL,
--   `gmt_create`   DATETIME        NOT NULL,
--   `gmt_modified` DATETIME        NOT NULL,
--   PRIMARY KEY (`id`)
-- )
--   DEFAULT CHARACTER SET = utf8;
--
-- --
-- -- 角色无关的权限表
-- --
-- CREATE TABLE `permission` (
--   `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
--   `description`  VARCHAR(64)     NOT NULL,
--   `name`         VARCHAR(128)    NOT NULL
--   COMMENT '用来标识该permission,一般采用url+Http  method的方式',
--   `gmt_create`   DATETIME        NOT NULL,
--   `gmt_modified` DATETIME        NOT NULL,
--   PRIMARY KEY (`id`)
-- )
--   DEFAULT CHARACTER SET = utf8;
--
-- --
-- -- 将角色和权限关联起来的表
-- --
-- CREATE TABLE `r_role_permission` (
--   `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
--   `role_id`       BIGINT UNSIGNED NULL,
--   `permission_id` BIGINT UNSIGNED NOT NULL,
--   `gmt_create`    DATETIME        NOT NULL,
--   `gmt_modified`  DATETIME        NOT NULL,
--   PRIMARY KEY (`id`)
-- )
--   DEFAULT CHARACTER SET = utf8;
--
-- # --
-- # -- 账户和角色相关联起来的表
-- # --
-- # CREATE TABLE `r_account_basicinfo_role`(
-- #   `rarid` INT UNSIGNED NOT NULL AUTO_INCREMENT,
-- #   `account_id` INT UNSIGNED NOT NULL ,
-- #   `role_id` INT UNSIGNED NOT NULL ,
-- #   `status` TINYINT(4) UNSIGNED  DEFAULT 1,
-- #   PRIMARY KEY (`rarid`),
-- #   UNIQUE KEY (`account_id`)
-- # )
-- #   DEFAULT CHARACTER SET = utf8
-- #   COMMENT = '这是账户信息和role信息的关联表';

-- --------------------------------------------------------------------------  账号部分  ----------------------------------------------------------------------------


--
-- 账户基本信息
--
# CREATE TABLE `account` (
#   `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
#   `name`            VARCHAR(64)     NOT NULL,
#   `password`        VARCHAR(128)    NOT NULL,
#   `phone`           VARCHAR(20)              DEFAULT NULL,
#   `role_id`         BIGINT UNSIGNED NOT NULL,
#   `wechat_id`       VARCHAR(128)             DEFAULT NULL,
#   `gmt_create`      DATETIME        NOT NULL,
#   `gmt_modified`    DATETIME        NOT NULL,
#   `last_login_time` DATETIME        NOT NULL,
#   `status`          TINYINT(1) UNSIGNED      DEFAULT 1,
#   PRIMARY KEY (`id`),
#   UNIQUE KEY uk_name (`name`)
# )
#   DEFAULT CHARACTER SET = utf8
#   COMMENT = '这是用户基本信息表，里面存放的都是一些不经常改变的属性域。';

-- 账号和user就是通过userId来关联的

-- ------------------------------------------------------------------------  邮件发送部分  ----------------------------------------------------------------------------

--
-- 邮件发送记录表
--
CREATE TABLE `mail_record` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `to_user`        BIGINT UNSIGNED NOT NULL ,
  `type`           INT(1)     NOT NULL,
  `content`        VARCHAR(1024) NOT NULL,
  `gmt_create`     DATETIME        NOT NULL,
  `gmt_modified`   DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;

-- ------------------------------------------------------------------------  建议部分  ----------------------------------------------------------------------------

--
-- 建议表
--
CREATE TABLE `advice` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `email`         VARCHAR(128) DEFAULT NULL ,
  `advice`        VARCHAR(1024) NOT NULL ,
  `gmt_create`     DATETIME        NOT NULL,
  `gmt_modified`   DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;


-- ------------------------------------------------------------------------  用户信息部分  ----------------------------------------------------------------------------

--
-- 用户基本信息表,用户可以自己在BlogAboutMe中填写自己的详细介绍
--
CREATE TABLE `user` (
  `id`                BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `loginname`         VARCHAR(64)     NOT NULL,
  `password`          VARCHAR(128)    NOT NULL,
  `phone`             VARCHAR(20)              DEFAULT NULL,
  `role_id`           BIGINT UNSIGNED NOT NULL,
  `nick`              VARCHAR(36)     NOT NULL DEFAULT 'NickNick',
  `sex`               TINYINT(1)      NOT NULL DEFAULT 1, -- 1表示男 , 2表示女 , 3表示未声明
  `introduction`      VARCHAR(188)    NOT NULL DEFAULT 'Here is introduction.',
  `avatar`            VARCHAR(64)              DEFAULT '/avatar/default',
  `background_img`    VARCHAR(64)              DEFAULT '/bkimg/default',
  `gmt_activate`      DATETIME                 DEFAULT NULL,
  `gmt_create`        DATETIME        NOT NULL,
  `gmt_modified`      DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;


--
-- 用户登录记录表
--
CREATE TABLE `login_record` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid`      BIGINT UNSIGNED     NOT NULL,
  `address`       VARCHAR(128)    NOT NULL,
  `success`        TINYINT(1) NOT NULL DEFAULT 1,
  `gmt_create`     DATETIME        NOT NULL,
  `gmt_modified`   DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;

-- --------------------------------------------------------------------------  Team部分  ----------------------------------------------------------------------------


--
-- 团队信息表
--
CREATE TABLE `team` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`         VARCHAR(128)    NOT NULL,
  `creator`      BIGINT UNSIGNED NOT NULL,
  `introduction` VARCHAR(288)    NOT NULL DEFAULT '并没有团队介绍',
  `gmt_create`   DATETIME        NOT NULL,
  `gmt_modified` DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;

--
-- 团队信息修改记录表
--
CREATE TABLE `team_info_change_record` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid`         VARCHAR(128)    NOT NULL,     -- 谁操作的
  `update_item` VARCHAR(128)    NOT NULL ,    -- 操作了什么东西，官方设定
  `gmt_create`   DATETIME        NOT NULL,
  `gmt_modified` DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;

--
-- 团队成员关联表
--
CREATE TABLE `team` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `team_id` BIGINT UNSIGNED NOT NULL ,
  `uid` BIGINT UNSIGNED NOT NULL ,
  `role_id`   INT UNSIGNED NOT NULL , -- 团员在该team中是什么角色
  `gmt_create`   DATETIME        NOT NULL,
  `gmt_modified` DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;

-- --------------------------------------------------------------------------  文章部分  ----------------------------------------------------------------------------
--
--
--
CREATE TABLE `blog_common` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid`      BIGINT UNSIGNED NOT NULL,
  `blog_intro` VARCHAR(1080) NOT NULL DEFAULT '该博主还没有介绍哦',
  `gmt_create`   DATETIME        NOT NULL,
  `gmt_modified` DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;

--
-- 博客文章表
--
CREATE TABLE `blog_article` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`         VARCHAR(128)    NOT NULL,
  `creator`      BIGINT UNSIGNED NOT NULL,
  `view`         INT UNSIGNED    NOT NULL DEFAULT 0, -- 浏览量
  `comment`      INT UNSiGNED NOT NULL DEFAULT 0,
  `grammar`      TINYINT(2)               DEFAULT 0, -- 语法格式
  `gmt_create`   DATETIME        NOT NULL,
  `gmt_modified` DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;

--
-- 博客文章内容表
--
CREATE TABLE `blog_article_content` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `length`       INT UNSIGNED    NOT NULL DEFAULT 0,
  `content`      TEXT            NOT NULL,
  `gmt_create`   DATETIME        NOT NULL,
  `gmt_modified` DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;

--
-- 博客分类表 , 可在service中进行缓存，并且在update中进行更新
--
CREATE TABLE `blog_group` (
  `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid`             BIGINT UNSIGNED NOT NULL ,  -- 这个group属于哪个用户
  `name`            VARCHAR(18)     NOT NULL DEFAULT 'blogGroup',
  `en`              VARCHAR(38)     NOT NULL DEFAULT 'blogGroup-english-name',
  `gmt_create`      DATETIME        NOT NULL,
  `gmt_modified`    DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;

--
-- 文章和分类map,一篇文章至少一个分类，不行就默认分类
-- 这个表只会在查询某个分类下的article和更新article的group中用到
--
CREATE TABLE `r_blog_article_group` (
  `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `article_id` BIGINT UNSIGNED NOT NULL,
  `group_id`   BIGINT UNSIGNED NOT NULL,
  `gmt_create`      DATETIME        NOT NULL,
  `gmt_modified`    DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;


--
-- 博客评论表  comment最好也用page来搞定
-- 博客评论采用楼中楼设计，分为两张表，前端在获取当前comment时候，先只获取第一级，等用户大电机查看针对第一级回复的回复后，在查看第二级回复，一共两级
-- 回复一旦被发表最好不可修改,comment数量可以从article自有属性中看出来
--
CREATE TABLE `blog_comment`(
  `id` BIGINT UNSIGNED NOT NULL  AUTO_INCREMENT,
  `uid` BIGINT UNSIGNED NOT NULL ,
  `aid`  BIGINT UNSIGNED NOT NULL ,
  `content` VARCHAR(188) NOT NULL ,
  `gmt_create`      DATETIME        NOT NULL,
  `gmt_modified`    DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE `blog_comment_replay`(
  `id` BIGINT UNSIGNED NOT NULL  AUTO_INCREMENT,
  `cid` BIGINT UNSIGNED NOT NULL ,  -- 针对哪条一级评论
  `uid` BIGINT UNSIGNED NOT NULL ,
  `to_uid` BIGINT UNSIGNED NOT NULL ,
  `content` VARCHAR(188) NOT NULL ,
  `gmt_create`      DATETIME        NOT NULL,
  `gmt_modified`    DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;

--
-- 博客标签表
--
CREATE TABLE `blog_tag` (
  `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid`             BIGINT UNSIGNED NOT NULL ,  -- 这个tag属于哪个用户
  `name`            VARCHAR(18)     NOT NULL DEFAULT 'blogTag',
  `en`              VARCHAR(38)     NOT NULL DEFAULT 'blogTag-english-name',
  `gmt_create`      DATETIME        NOT NULL,
  `gmt_modified`    DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;


--
-- 标签和文章map,一个文章 0+个tag
--
CREATE TABLE `r_blog_article_tag` (
  `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `article_id` BIGINT UNSIGNED NOT NULL,
  `tag_id`   BIGINT UNSIGNED NOT NULL,
  `gmt_create`      DATETIME        NOT NULL,
  `gmt_modified`    DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;


-- --------------------------------------------------------------------------  common部分  ----------------------------------------------------------------------------

--
-- 省份信息
--
CREATE TABLE `province` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`         VARCHAR(16)     NOT NULL,
  `gmt_create`   DATETIME        NOT NULL,
  `gmt_modified` DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8;

--
-- 通知中心
--
# CREATE TABLE `notification`(
#   `id`  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
#   `creator` BIGINT UNSIGNED NOT NULL ,
#   `name` VARCHAR(188) NOT NULL DEFAULT 'title',
#   `grammar`      TINYINT(1)               DEFAULT 0, -- 语法格式
#   `content`       TEXT NOT NULL,
#   `gmt_create`   DATETIME        NOT NULL,
#   `gmt_modified` DATETIME        NOT NULL,
#   PRIMARY KEY (`id`)
# )DEFAULT CHARACTER SET = utf8