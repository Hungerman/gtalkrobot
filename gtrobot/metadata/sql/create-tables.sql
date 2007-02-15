-- Drop constraint (foreign key)
-- alter table USER_ENTRY drop constraint FK65F2821B8204EADD;

-- Drop table
drop table USER_ENTRY;

-- Drop sequence
drop sequence SEQ_USER_ENTRY;

-- Create table
create table USER_ENTRY (
		ID							int8 					not null, 
		JID 						VARCHAR(80) 	not null, 
		NICK_NAME 			VARCHAR(80) 	not null, 
		CHATTABLE 			BOOL 					not null, 
		ECHOABLE 				BOOL 					not null, 
		LOCALE 					VARCHAR(3) 		not null, 
		CREATE_DATE			timestamp			not null, 
		LAST_LOGIN_DATE	timestamp			not null,
		UNIQUE(JID),
		UNIQUE(NICK_NAME),
		PRIMARY KEY (ID)
);

-- Create foreign key
-- alter table ANKEN_CHECKBOX add constraint FK65F2821B8204EADD foreign key (ITEM_ID) references BPM_ITEM;

-- Create sequence
create sequence SEQ_USER_ENTRY;
alter SEQUENCE SEQ_USER_ENTRY RESTART with 10000;


