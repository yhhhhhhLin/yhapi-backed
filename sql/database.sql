-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '用户';

create table interfaceInfo
(
    id             bigint                               not null comment '接口id'
        primary key
        unique,
    name           varchar(255)                         not null comment '接口名称',
    isDelete       tinyint  default 0                   not null comment '是否删除 0为没删除 1为删除',
    createTime     datetime default current_timestamp() not null comment '创建时间',
    updateTime     datetime default current_timestamp   not null comment '更新时间',
    method         varchar(255)                         not null comment '请求方法',
    description    varchar(255)                         null comment '接口描述',
    url            varchar(255)                         not null comment '接口地址',
    requestHeader  text                                 null comment '请求头',
    responseHeader text                                 null comment '响应信息',
    status         tinyint  default 0                   null comment '接口状态 0为可用 1为不可用',
    userId         long                                 null comment '接口创建者id'
)
    comment '接口信息表';

create table userInterfaceInfo
(
    id             bigint                               not null comment 'id'
        primary key
        unique,
    userId           bigint                             not null comment '用户id',
    interfaceId      bigint                             not null comment '接口id',
    remNum           int default 10                     not null comment '剩余调用次数',
    allNum           int default 0                      not null comment '共调用多少次',
    status         tinyint  default 0                   null comment '用户是否能调用这个接口 0为可用 1为不可用',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
)
    comment '用户接口调用次数关系表';
