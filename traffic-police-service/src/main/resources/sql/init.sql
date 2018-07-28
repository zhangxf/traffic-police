/*==============================================================*/
/* Table: tp_bguser                                             */
/*==============================================================*/
create table tp_bguser
(
   id                   bigint not null auto_increment comment '主键',
   username             varchar(50) not null comment '用户名',
   password             varchar(50) not null comment '密码',
   email                varchar(50) not null comment 'email',
   telephone            varchar(50) not null comment '手机号',
   realname             varchar(50) not null comment '真实姓名',
   is_enable            varchar(1) not null default '0' comment '是否启用1:是 0:否',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_bguser comment '后台用户表';
alter table tp_bguser add constraint uk_username unique(username);
alter table tp_bguser add constraint uk_email unique(email);
alter table tp_bguser add constraint uk_telephone unique(telephone);
/*==============================================================*/
/* Table: tp_role                                               */
/*==============================================================*/
create table tp_role
(
   id                   bigint not null auto_increment comment '主键',
   name             	varchar(50) not null comment '角色名称',
   code             	varchar(50) not null comment '角色代码 例如：ADMIN, SUPERADMIN, USER',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_role comment '角色表';
alter table tp_role add constraint uk_name unique(name);
alter table tp_role add constraint uk_code unique(code);
/*==============================================================*/
/* Table: tp_authority                                          */
/*==============================================================*/
create table tp_authority
(
   id                   bigint not null auto_increment comment '主键',
   code             	varchar(50) not null comment '权限',
   name             	varchar(50) not null comment '权限名称',
   `action`             varchar(512) comment '地址',
   menu_id				bigint comment '所属菜单id',
   id_on_page			varchar(50) comment '在页面上的位置唯一标识。用于前端处理显示不显示该按钮',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_authority comment '权限表';
alter table tp_authority add constraint uk_name unique(name);
alter table tp_authority add constraint uk_code unique(code);
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-add', '后台用户添加', '/bguser/add', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-update', '后台用户修改', '/bguser/update', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-page', '后台用户查询', '/bguser/page', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-delete', '后台用户删除', '/bguser/delete', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-menu', '查询当前用户菜单', '/bguser/menu', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-privilege-authorities', '查询某用户特权权限', '/bguser/privilege/authorities', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-privilege-menu', '查询某用户特权菜单', '/bguser/privilege/menu', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-config-privilege-authorities', '配置某用户特权权限', '/bguser/config/privilege/authorities', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-config-privilege-menu', '配置某用户特权菜单', '/bguser/config/privilege/menu', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-config-roles', '配置某用户的角色', '/bguser/config/roles', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-roles', '查询某用户的角色', '/bguser/roles', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-buttons', '查询某用户某页面按扭权限', '/bguser/buttons', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('bguser-info', '查询登录用户信息', '/bguser/info', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('role-add', '添加角色', '/role/add', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('role-update', '更新角色', '/role/update', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('role-page', '查询角色', '/role/page', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('role-delete', '删除角色', '/role/delete', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('role-authorities', '查询某角色的权限', '/role/authorities', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('role-menu', '查询某角色的菜单', '/role/menu', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('role-config-authorities', '配置某角色的权限', '/role/config/authorities', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('role-config-menu', '配置某角色的菜单', '/role/config/menu', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('menu-add', '添加菜单', '/menu/add', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('menu-update', '更新菜单', '/menu/update', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('menu-delete', '删除菜单', '/menu/delete', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('menu-all', '查询所有菜单', '/menu/all', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('menu-query', '查询单个菜单', '/menu/query', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('menu-all-leaf', '查询所有叶子菜单', '/menu/all-leaf', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('authority-add','添加权限', '/authority/add', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('authority-update', '更新权限', '/authority/update', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('authority-page', '查询权限', '/authority/page', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('authority-delete', '删除权限', '/authority/delete', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('category-add', '添加分类', '/category/add', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('category-update', '更新分类', '/category/update', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('category-delete', '删除分类', '/category/delete', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('category-video', '查询视频分类', '/category/video', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('category-question', '查询试题分类', '/category/question', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('question-add', '添加试题', '/question/add', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('question-delete', '删除试题', '/question/delete', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('question-update', '修改试题', '/question/update', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('question-page', '分页查询试题', '/question/page', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('question-find-by-id', '根据id查询试题', '/question/find-by-id', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('file-video-upload', '视频上传', '/file/video/upload', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('file-image-upload', '图片上传', '/file/image/upload', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('video-add', '添加视频', '/video/add', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('video-delete', '删除视频', '/video/delete', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('video-update', '修改视频', '/video/update', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('video-page', '分页查询视频', '/video/page', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('video-find-by-id', '根据id查询视频', '/video/find-by-id', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('driver-page', '分页查询驾驶人', '/driver/page', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('driver-audit', '驾驶人审核', '/driver/audit', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('driver-ops-black', '驾驶人拉黑', '/driver/ops/black', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('driver-ops-white', '驾驶人洗白', '/driver/ops/white', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('driver-add', '添加驾驶人', '/driver/add', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('driver-find-by-id', '根据id查询驾驶人', '/driver/find-by-id', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('driver-update', '修改驾驶人', '/driver/update', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('video-config-check', '查询审验教育视频学习设置', '/video/config/check', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('video-config-check-setting', '保存审验教育视频学习设置', '/video/config/check/setting', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('question-config-check', '查询审验教育试题学习设置', '/question/config/check', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_authority(code, name, `action`, create_time, update_time) values('question-config-check-setting', '保存审验教育试题学习设置', '/question/config/check/setting', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
/*==============================================================*/
/* Table: tp_bguser_role                                        */
/*==============================================================*/
create table tp_bguser_role
(
   id                   bigint not null auto_increment comment '主键',
   user_id              bigint not null comment '后台用户id',
   role_id              bigint not null comment '角色id',
   create_time          datetime comment '创建时间',
   primary key (id)
);
alter table tp_bguser_role comment '用户角色表';
alter table tp_bguser_role add constraint uk_user_id_role_id unique(user_id, role_id);
/*==============================================================*/
/* Table: tp_role_authority                                        */
/*==============================================================*/
create table tp_role_authority
(
   id                   bigint not null auto_increment comment '主键',
   role_id              bigint not null comment '角色id',
   authority_id         bigint not null comment '权限id',
   create_time          datetime comment '创建时间',
   primary key (id)
);
alter table tp_role_authority comment '角色权限表';
alter table tp_role_authority add constraint uk_role_id_authority_id unique(role_id, authority_id);
/*==============================================================*/
/* Table: tp_user_authority                                        */
/*==============================================================*/
create table tp_user_authority
(
   id                   bigint not null auto_increment comment '主键',
   user_id              bigint not null comment '用户id',
   authority_id         bigint not null comment '权限id',
   create_time          datetime comment '创建时间',
   primary key (id)
);
alter table tp_user_authority comment '用户权限表';
alter table tp_user_authority add constraint uk_user_id_authority_id unique(user_id, authority_id);
/*==============================================================*/
/* Table: tp_menu                                        */
/*==============================================================*/
create table tp_menu
(
   id                   bigint not null auto_increment comment '主键',
   name             	varchar(100) not null comment '菜单名称',
   `action` 			varchar(512) comment '菜单地址',
   is_leaf            	varchar(1) not null default '0' comment '是否叶子节点1:是 0:否',
   parent_id            bigint comment '父节点id',
   sorted_order			bigint not null comment '排序',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_menu comment '菜单表';
alter table tp_menu add constraint uk_menu_name unique(name);
/*==============================================================*/
/* Table: tp_role_menu                                        */
/*==============================================================*/
create table tp_role_menu
(
   id                   bigint not null auto_increment comment '主键',
   role_id              bigint not null comment '角色id',
   menu_id         		bigint not null comment '菜单id',
   create_time          datetime comment '创建时间',
   primary key (id)
);
alter table tp_role_menu comment '角色菜单表';
alter table tp_role_menu add constraint uk_role_id_menu_id unique(role_id, menu_id);
/*==============================================================*/
/* Table: tp_user_menu                                        */
/*==============================================================*/
create table tp_user_menu
(
   id                   bigint not null auto_increment comment '主键',
   user_id              bigint not null comment '用户id',
   menu_id         		bigint not null comment '菜单id',
   create_time          datetime comment '创建时间',
   primary key (id)
);
alter table tp_user_menu comment '用户菜单表';
alter table tp_user_menu add constraint uk_user_id_menu_id unique(user_id, menu_id);
/*==============================================================*/
/* Table: tp_fileinfo		                                        */
/*==============================================================*/
create table tp_fileinfo
(
   token             	varchar(50) not null comment '唯一标识',
   origin_name          varchar(100) not null comment '原文件名字',
   dest_name      		varchar(100) not null comment '上传后的目标文件名字',
   url         			varchar(255) not null comment '上传后的目标文件相对路径',
   file_type           	varchar(50) not null comment '文件类型',
   duration				bigint comment '时长:单位秒',
   file_size			bigint comment '文件大小KB',
   create_time          datetime comment '创建时间',
   primary key (token)
);
alter table tp_bguser_role comment '文件上传表';
/*==============================================================*/
/* Table: tp_user		                                        */
/*==============================================================*/
create table tp_user
(
   id                   bigint not null auto_increment comment '主键',
   id_type             	varchar(50) not null comment '证件类型',
   id_no             	varchar(50) not null comment '证件号码',
   id_card_img_url      varchar(100) comment '证件图片',
   license_type         varchar(50) not null comment '驾驶证类型',
   license_no           varchar(50) not null comment '驾驶证编号',
   license_begin_date	datetime not null comment '驾驶证有效期开始时间',
   license_end_date		datetime not null comment '驾驶证有效期结束时间',
   head_url             varchar(100) comment '驾驶人本人头像',
   phone            	varchar(50) not null comment '手机号',
   audit_state          varchar(50) not null comment '审核状态',
   audit_desc           varchar(255) comment '审核描述',
   audit_time			datetime comment '审核时间',
   disabled				varchar(1) not null default '0' comment '是否禁用（拉黑/洗白）1:是 0:否',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_user comment '驾驶人用户表';
alter table tp_user add constraint uk_id_no unique(id_no);
alter table tp_user add constraint uk_license_no unique(license_no);
alter table tp_user add constraint uk_phone unique(phone);
/*==============================================================*/
/* Table: tp_category		                                        */
/*==============================================================*/
create table tp_category
(
   id                   bigint not null auto_increment comment '主键',
   name             	varchar(50) not null comment '分类名称',
   `type`             	varchar(50) not null comment '分类类型VIDEO:视频 QUESTION:试题',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_category comment '视频试题分类表';
alter table tp_category add constraint uk_name_type unique(name, type);
insert into tp_category(name, `type`, create_time, update_time) values('道路交通安全法律法规', 'QUESTION', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('交通安全文明驾驶常识', 'QUESTION', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('应急处置知识', 'QUESTION', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('交通事故案例警示教育', 'QUESTION', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('防御性驾驶知识', 'QUESTION', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('驾驶心理健康知识', 'QUESTION', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('其他相关知识', 'QUESTION', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('道路交通安全法律法规', 'VIDEO', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('交通安全文明驾驶常识', 'VIDEO', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('应急处置知识', 'VIDEO', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('交通事故案例警示教育', 'VIDEO', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('防御性驾驶知识', 'VIDEO', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('驾驶心理健康知识', 'VIDEO', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
insert into tp_category(name, `type`, create_time, update_time) values('其他相关知识', 'VIDEO', str_to_date('2018-07-15','%Y-%m-%d'), str_to_date('2018-07-15','%Y-%m-%d'));
/*==============================================================*/
/* Table: tp_question		                                        */
/*==============================================================*/
create table tp_question
(
   id                   bigint not null auto_increment comment '主键',
   question             varchar(500) not null comment '问题',
   answer             	varchar(50) comment '答案',
   item1				varchar(500) comment '选项1',
   item2				varchar(500) comment '选项2',
   item3				varchar(500) comment '选项3',
   item4				varchar(500) comment '选项4',
   explains				varchar(500) comment '说明',
   url					varchar(100) comment '图片地址',
   origin_url			varchar(512) comment '原图片地址',
   category_id          bigint comment '分类id',
   subject				varchar(1) comment '科目类型，1：科目1；4：科目4',
   `type`				varchar(500) comment '题目类型 分为A1, A2, A3, B1, B2逗号分隔',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_question comment '试题表';
/*==============================================================*/
/* Table: tp_video		                                        */
/*==============================================================*/
create table tp_video
(
   id                   bigint not null auto_increment comment '主键',
   name             	varchar(500) not null comment '视频名称',
   origin_name          varchar(500) comment '原视频名称',
   introduction			longtext comment '视频介绍',
   duration				bigint comment '时长:单位秒',
   file_size			bigint comment '文件大小KB',
   url					varchar(100) comment '视频地址',
   thumb_url			varchar(100) comment '视频缩略图地址',
   category_id          bigint comment '分类id',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_video comment '视频表';
/*==============================================================*/
/* Table: tp_video_config		                                        */
/*==============================================================*/
create table tp_video_config
(
   id                   bigint not null auto_increment comment '主键',
   category_id          bigint not null comment '分类id',
   learn_num			bigint comment '学习个数',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_video_config comment '视频学习设置表';
alter table tp_video_config add constraint uk_category_id unique(category_id);
/*==============================================================*/
/* Table: tp_question_config		                                        */
/*==============================================================*/
create table tp_question_config
(
   id                   bigint not null auto_increment comment '主键',
   total_num            bigint not null comment '题目总数',
   correct_num			bigint comment '正确题目数',
   period				bigint comment '考试时长',
   edu_type             varchar(50) comment '教育类型',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_question_config comment '试题学习设置表';
alter table tp_question_config add constraint uk_edu_type unique(edu_type);
/*==============================================================*/
/* Table: tp_question_config_detail		                                        */
/*==============================================================*/
create table tp_question_config_detail
(
   id                   bigint not null auto_increment comment '主键',
   category_id          bigint not null comment '分类id',
   learn_num			bigint comment '分类下需要的个数',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_question_config_detail comment '试题学习设置明细表';
alter table tp_question_config_detail add constraint uk_category_id unique(category_id);
/*==============================================================*/
/* Table: tp_video_record		                                        */
/*==============================================================*/
create table tp_video_record
(
   id                   bigint not null auto_increment comment '主键',
   video_id             bigint not null comment '视频id',
   user_id			    bigint not null comment '用户id',
   batch_num			varchar(50) not null comment '批次编号',
   duration				bigint comment '视频时长:单位秒',
   completed_duration	bigint comment '完成时长:单位秒',
   is_completed         varchar(1) not null default '0' comment '是否已看完1:是 0:否',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_video_record comment '试题学习记录表';
alter table tp_video_record add constraint uk_video_user_batch_num_id unique(video_id, user_id, batch_num);
