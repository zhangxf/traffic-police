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
alter table tp_bguser add constraint uk_username unique(username);
alter table tp_bguser add constraint uk_email unique(email);
alter table tp_bguser add constraint uk_telephone unique(telephone);