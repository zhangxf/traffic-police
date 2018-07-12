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
   name             	varchar(50) not null comment '权限名称',
   `action`             varchar(512) comment '地址',
   is_leaf            	varchar(1) not null default '0' comment '是否叶子节点1:是 0:否',
   parent_id            bigint comment '父节点id',
   authority_type		varchar(50) not null comment '权限类型 menu:菜单 button:按钮 api:接口',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   primary key (id)
);
alter table tp_authority comment '权限表';
alter table tp_authority add constraint uk_name unique(name);
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
   id_card_img_url      varchar(100) not null comment '证件图片',
   license_type         varchar(50) not null comment '驾驶证类型',
   license_no           varchar(50) not null comment '驾驶证编号',
   license_begin_date	datetime not null comment '驾驶证有效期开始时间',
   license_end_date		datetime not null comment '驾驶证有效期结束时间',
   head_url             varchar(100) not null comment '驾驶人本人头像',
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