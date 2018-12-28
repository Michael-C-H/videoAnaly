--摄像头配置数据表
create table VIDEO_CAMERA
(
ID varchar(32) not null primary key,
CODE varchar(128),     		/*摄像头编码*/
NAME varchar(256),      	/*摄像头显示名称*/
SOURCE varchar(256),  		/*视频源获取地址*/
ANLTSIS_TYPE varchar(256),      /*该视频源需要分析的类型*/
ONLINE_NUM int,			/*视频中最大人数*/
TOTAL int,   		        /*整个区域范围内最大人数*/
LINE_PT1 varchar(256),      	/*标记线起始点*/
LINE_PT2 varchar(256)  		/*标记线结束点*/
);

--视频中着装异常记录表
create table VIDEO_DRESS_ABNORMAL
(
ID varchar(32) not null primary key,
CODE varchar(128),     		/*发生异常的摄像头编码*/
NAME varchar(256),      	/*发生异常的摄像头显示名称*/
EX_DATE date,  			/*发生异常的时间*/
IMAGE blob,			/*发生异常时的截图的二进制数据*/
REASON varchar(256)      	/*发生异常时的原因*/
);

--视频中人数异常记录表
create table VIDEO_NUMBER_ABNORMAL
(
ID varchar(32) not null primary key,
CODE varchar(128),     		/*发生异常的摄像头编码*/
NAME varchar(256),      	/*发生异常的摄像头显示名称*/
EX_DATE date,  			/*发生异常的时间*/
IMAGE blob,			/*发生异常时的截图的二进制数据*/
LIMIT int,      		/*人数阀值*/
CURRENT_NUM int      		/*实际人数*/
);