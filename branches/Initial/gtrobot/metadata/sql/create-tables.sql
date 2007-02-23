-- Drop constraint (foreign key)
-- alter table USER_ENTRY drop constraint FK65F2821B8204EADD;

-- Drop table
drop table USER_ENTRY;
drop table WORD_ENTRY;
drop table WORD_MEANING;
drop table WORD_SENTENCE;
drop table WORD_ENTRY_SYNONYM;
drop table WORD_ENTRY_LOCALIZATIONS;
drop table WORD_MEANING_LOCALIZATIONS;
drop table WORD_SENTENCE_LOCALIZATIONS;
drop table WORD_ENTRY_MEANINGS;
drop table WORD_MEANING_SENTENCES;

-- Drop sequence
drop sequence SEQ_USER_ENTRY;
drop sequence SEQ_WORD_ENTRY;
drop sequence SEQ_WORD_MEANING;
drop sequence SEQ_WORD_SENTENCE;
drop sequence SEQ_WORD_ENTRY_SYNONYM;


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

create table WORD_ENTRY (
		WE_ID						int8 					not null, 
		WORD 						VARCHAR(80) 	not null, 
		PRONOUNCE 		  VARCHAR(80),
		LOCALE 					VARCHAR(3) 		not null, 
		PRIMARY KEY (WE_ID)
);
create table WORD_MEANING (
		WM_ID						int8 					not null, 
		MEANING					VARCHAR(255) 	not null, 
		LOCALE 					VARCHAR(3) 		not null, 
		PRIMARY KEY (WM_ID)
);
create table WORD_SENTENCE (
		WS_ID						int8 					not null, 
		SENTENCE 				VARCHAR(80) 	not null, 
		LOCALE 					VARCHAR(3) 		not null, 
		PRIMARY KEY (WS_ID)
);

create table WORD_ENTRY_SYNONYM (
    WES_ID					int8 					not null, 
		WE_ID						int8 					not null, 
		PRIMARY KEY (WES_ID, WE_ID)
);

create table WORD_ENTRY_LOCALIZATIONS (
		WE_ID						int8 					not null, 
		WE_ID_L					int8 					not null, 
		PRIMARY KEY (WE_ID, WE_ID_L)
);
create table WORD_MEANING_LOCALIZATIONS (
		WM_ID						int8 					not null, 
		WM_ID_L					int8 					not null, 
		PRIMARY KEY (WM_ID, WM_ID_L)
);
create table WORD_SENTENCE_LOCALIZATIONS (
		WS_ID						int8 					not null, 
		WS_ID_L					int8 					not null, 
		PRIMARY KEY (WS_ID, WS_ID_L)
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


