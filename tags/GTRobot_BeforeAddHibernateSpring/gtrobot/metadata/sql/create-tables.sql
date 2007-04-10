-- Drop constraint (foreign key)
-- alter table USER_ENTRY drop constraint FK65F2821B8204EADD;

-- Drop table
drop table USER_ENTRY;
drop table WORD_ENTRY;
drop table WORD_MEANING;
drop table WORD_SENTENCE;
drop table WORD_ENTRY_SYNONYM;
drop table WORD_ENTRY_MEANINGS;
drop table WORD_MEANING_SENTENCES;
drop table JP_WORD_ENTRY;
drop table USER_JP_WORD_ENTRY;

-- Drop sequence
drop sequence SEQ_USER_ENTRY;
drop sequence SEQ_WORD_ENTRY;
drop sequence SEQ_WORD_MEANING;
drop sequence SEQ_WORD_SENTENCE;
drop sequence SEQ_WORD_ENTRY_SYNONYM;
drop sequence SEQ_JP_WORD_ENTRY;


-- Create table
create table USER_ENTRY (
		UE_ID							int8 					not null, 
		JID 						VARCHAR(80) 	not null, 
		NICK_NAME 			VARCHAR(80) 	not null, 
		CHATTABLE 			BOOL 					not null, 
		ECHOABLE 				BOOL 					not null, 
		LOCALE 					VARCHAR(3) 		not null, 
		CREATE_DATE			timestamp			not null, 
		LAST_LOGIN_DATE	timestamp			not null,
		UNIQUE(JID),
		UNIQUE(NICK_NAME),
		PRIMARY KEY (UE_ID)
);

create table WORD_ENTRY (
		WE_ID						int8 					not null, 
		WORD 						VARCHAR(80) 	not null, 
		PRONOUNCE 		  VARCHAR(80),
		LOCALE 					VARCHAR(3) 		not null, 
		PRIMARY KEY (WE_ID)
);
create table WORD_MEANING (
		WM_ID						int8 					not null, 
		WORD_TYPE				int8 					not null, 
		MEANING					VARCHAR(255) 	not null, 
		LOCALE 					VARCHAR(3) 		not null, 
		PRIMARY KEY (WM_ID)
);
create table WORD_SENTENCE (
		WS_ID						int8 					not null, 
		SENTENCE 				VARCHAR(80) 	not null, 
		COMMENTS 				VARCHAR(2000) 	not null, 
		LOCALE 					VARCHAR(3) 		not null, 
		PRIMARY KEY (WS_ID)
);

create table WORD_ENTRY_SYNONYM (
    WES_ID					int8 					not null, 
		WE_ID						int8 					not null, 
		PRIMARY KEY (WES_ID, WE_ID)
);

create table WORD_ENTRY_MEANINGS (
		WE_ID						int8 					not null, 
		WM_ID						int8 					not null, 
		PRIMARY KEY (WE_ID, WM_ID)
);
create table WORD_MEANING_SENTENCES (
		WM_ID						int8 					not null, 
		WS_ID						int8 					not null, 
		PRIMARY KEY (WM_ID, WS_ID)
);

create table JP_WORD_ENTRY (
		JWE_ID					int8 					not null, 
		WORD 						VARCHAR(80) 	not null, 
		PRONOUNCE 		  VARCHAR(80),
		WORD_TYPE				VARCHAR(20), 
		MEANING					VARCHAR(255) 	not null, 
		SENTENCE 				VARCHAR(2000), 
		COMMENTS 				VARCHAR(2000), 
		PRIMARY KEY (JWE_ID)
);
create table USER_JP_WORD_ENTRY (
		UE_ID						int8 					not null, 
		JWE_ID					int8 					not null, 
		PASS_COUNT			int8 					not null default 0, 
		FAIL_COUNT			int8 					not null default 0, 
		PRIMARY KEY (UE_ID, JWE_ID)
);



-- Create foreign key
-- alter table ANKEN_CHECKBOX add constraint FK65F2821B8204EADD foreign key (ITEM_ID) references BPM_ITEM;

-- Create sequence
create sequence SEQ_USER_ENTRY;
alter SEQUENCE SEQ_USER_ENTRY RESTART with 10000;
create sequence SEQ_WORD_ENTRY;
alter SEQUENCE SEQ_WORD_ENTRY RESTART with 10000;
create sequence SEQ_WORD_MEANING;
alter SEQUENCE SEQ_WORD_MEANING RESTART with 10000;
create sequence SEQ_WORD_SENTENCE;
alter SEQUENCE SEQ_WORD_SENTENCE RESTART with 10000;
create sequence SEQ_WORD_ENTRY_SYNONYM;
alter SEQUENCE SEQ_WORD_ENTRY_SYNONYM RESTART with 10000;
create sequence SEQ_JP_WORD_ENTRY;
alter SEQUENCE SEQ_JP_WORD_ENTRY RESTART with 10000;

