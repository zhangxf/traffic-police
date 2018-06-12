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
