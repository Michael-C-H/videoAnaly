--����ͷ�������ݱ�
create table VIDEO_CAMERA
(
ID varchar(32) not null primary key,
CODE varchar(128),     		/*����ͷ����*/
NAME varchar(256),      	/*����ͷ��ʾ����*/
SOURCE varchar(256),  		/*��ƵԴ��ȡ��ַ*/
ANLTSIS_TYPE varchar(256),      /*����ƵԴ��Ҫ����������*/
ONLINE_NUM int,			/*��Ƶ���������*/
TOTAL int,   		        /*��������Χ���������*/
LINE_PT1 varchar(256),      	/*�������ʼ��*/
LINE_PT2 varchar(256)  		/*����߽�����*/
);

--��Ƶ����װ�쳣��¼��
create table VIDEO_DRESS_ABNORMAL
(
ID varchar(32) not null primary key,
CODE varchar(128),     		/*�����쳣������ͷ����*/
NAME varchar(256),      	/*�����쳣������ͷ��ʾ����*/
EX_DATE date,  			/*�����쳣��ʱ��*/
IMAGE blob,			/*�����쳣ʱ�Ľ�ͼ�Ķ���������*/
REASON varchar(256)      	/*�����쳣ʱ��ԭ��*/
);

--��Ƶ�������쳣��¼��
create table VIDEO_NUMBER_ABNORMAL
(
ID varchar(32) not null primary key,
CODE varchar(128),     		/*�����쳣������ͷ����*/
NAME varchar(256),      	/*�����쳣������ͷ��ʾ����*/
EX_DATE date,  			/*�����쳣��ʱ��*/
IMAGE blob,			/*�����쳣ʱ�Ľ�ͼ�Ķ���������*/
LIMIT int,      		/*������ֵ*/
CURRENT_NUM int      		/*ʵ������*/
);