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
alter table tp_user add constraint uk_id_type unique(id_type);
alter table tp_user add constraint uk_id_no unique(id_no);
alter table tp_user add constraint uk_id_card_img_url unique(id_card_img_url);
alter table tp_user add constraint uk_id_type unique(id_type);
alter table tp_user add constraint uk_license_no unique(license_no);
alter table tp_user add constraint uk_license_begin_date unique(license_begin_date);
alter table tp_user add constraint uk_license_end_date unique(license_end_date);
alter table tp_user add constraint uk_head_url unique(head_url);
alter table tp_user add constraint uk_phone unique(phone);