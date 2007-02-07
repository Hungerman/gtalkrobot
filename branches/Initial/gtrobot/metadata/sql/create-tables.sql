alter table ANKEN_CHECKBOX drop constraint FK65F2821B8204EADD;
alter table ANKEN_CHECKBOX drop constraint FK65F2821B6ECA441C;
alter table ANKEN_COMPETITOR drop constraint FKA6B1ADA46ECA441C;
alter table ANKEN_COMPETITOR drop constraint FKA6B1ADA4804D3C0A;
alter table ANKEN_EMPLOYEE drop constraint FK517A4D0639A0DCCA;
alter table ANKEN_EMPLOYEE drop constraint FK517A4D066ECA441C;
alter table ANKEN_EMP_GROUP drop constraint FKFDC7A4B0289511ED;
alter table ANKEN_EMP_GROUP drop constraint FKFDC7A4B0B1B3430A;
alter table ANKEN_EMP_GROUP drop constraint FKFDC7A4B06ECA441C;
alter table ANKEN_LIST drop constraint FK6EFE0AF617D00444;
alter table ANKEN_LIST drop constraint FK6EFE0AF6289511ED;
alter table ANKEN_LIST drop constraint FK6EFE0AF639A0DCCA;
alter table ANKEN_LIST drop constraint FK6EFE0AF6B1B3430A;
alter table ANKEN_LIST_REC drop constraint FK5AA00BA76ECA441C;
alter table ANKEN_LIST_REC drop constraint FK5AA00BA7D5EBEE2B;
alter table BPM_CHECK_ITEM drop constraint FK5530268A8204EADD;
alter table BPM_MENU drop constraint FKD8C403DF1F804683;
alter table BPM_RELATED_SELECT_ITEM_1 drop constraint FKEDF76AF48204EADD;
alter table BPM_RELATED_SELECT_ITEM_2 drop constraint FKEDF76AF58204EADD;
alter table BPM_SELECT_ITEM drop constraint FKE71EF9F68204EADD;
alter table BPM_SELECT_RELATION drop constraint FK8362475FE9961D44;
alter table BPM_SELECT_RELATION drop constraint FK8362475FE9961D46;
alter table BPM_SUBMENU drop constraint FK9A85B8FF1F804683;
alter table BPM_SUBMENU drop constraint FK9A85B8FF4F580FE6;
alter table BPM_TYPE drop constraint FKD8C77DBA26290812;
alter table BPM_URL drop constraint FK304882CF76082B46;
alter table COMPETITOR drop constraint FKF1846D4CAFDC87AB;
alter table COMPETITOR drop constraint FKF1846D4C1C92C8CE;
alter table CORPORATION_CHECKBOX drop constraint FK659AEE3A8204EADD;
alter table CORPORATION_CHECKBOX drop constraint FK659AEE3A12AA421A;
alter table DOCUMENT_FILE drop constraint FK19FC5E2034FDA145;
alter table DOCUMENT_FILE drop constraint FK19FC5E20156F4F8C;
alter table DOCUMENT_FILE drop constraint FK19FC5E2019F9C8A3;
alter table DOCUMENT_FILE_HIST drop constraint FK670605E134FDA145;
alter table DOCUMENT_FILE_HIST drop constraint FK670605E1156F4F8C;
alter table DOCUMENT_FILE_HIST drop constraint FK670605E14E867545;
alter table DOCUMENT_FILE_HIST drop constraint FK670605E119F9C8A3;
alter table EMPLOYEE_CHECKBOX drop constraint FK89590A3439A0DCCA;
alter table EMPLOYEE_CHECKBOX drop constraint FK89590A348204EADD;
alter table EMPLOYEE_GROUP drop constraint FKFF7FD8E39A0DCCA;
alter table EMPLOYEE_LICENSE drop constraint FKEA61FF9039A0DCCA;
alter table EMPLOYEE_LICENSE drop constraint FKEA61FF90A32A32DA;
alter table EMPLOYEE_LICENSE_HIST drop constraint FK891BCA7139A0DCCA;
alter table EMPLOYEE_LICENSE_HIST drop constraint FK891BCA71A32A32DA;
alter table EMPLOYEE_LICENSE_TEMP drop constraint FK89212F2339A0DCCA;
alter table EMPLOYEE_LICENSE_TEMP drop constraint FK89212F23A32A32DA;
alter table EMPLOYEE_ROLE drop constraint FK4299576739A0DCCA;
alter table EMPLOYEE_ROLE drop constraint FK42995767B3A8FCA;
alter table EMP_GROUP_MEMBER drop constraint FKFB4A92B139A0DCCA;
alter table EMP_GROUP_MEMBER drop constraint FKFB4A92B1289511ED;
alter table FILE_PERM drop constraint FK596443D339A0DCCA;
alter table FILE_PERM drop constraint FK596443D34E867545;
alter table FILE_UPLOAD_EMPLOYEE drop constraint FK90B2826939A0DCCA;
alter table FILE_UPLOAD_EMPLOYEE drop constraint FK90B2826934FDA145;
alter table LICENSE drop constraint FK34BC102172850D7E;
alter table LICENSE drop constraint FK34BC1021782877DD;
alter table LICENSE_FUNCTION drop constraint FKF9A81A3676082B46;
alter table LICENSE_FUNCTION drop constraint FKF9A81A3672850D7E;
alter table LICENSE_FUNCTION drop constraint FKF9A81A36782877DD;
alter table LICENSE_KIND drop constraint FKEBFBC8F272850D7E;
alter table LICENSE_MAINTENANCE_HIST drop constraint FK3224E14CA32A32DA;
alter table LOCALE_MESSAGE drop constraint FKE65C3B027D5BDD0D;
alter table MANAGER drop constraint FK5C949B8D39A0DCCA;
alter table MANAGER drop constraint FK5C949B8DBCC32F38;
alter table MANAGER drop constraint FK5C949B8DB1B3430A;
alter table MENU_BAR_SETTING drop constraint FK445F3044F580FE6;
alter table MULTI_VIEW_ITEM drop constraint FKC15EE4C7E368BB5B;
alter table OFFICE_CHECKBOX drop constraint FK46235DE6BE6E1EDA;
alter table OFFICE_CHECKBOX drop constraint FK46235DE68204EADD;
alter table PRODUCT_CHECKBOX drop constraint FK91B25D338204EADD;
alter table PRODUCT_CHECKBOX drop constraint FK91B25D33AFDC87AB;
alter table PROGRESS drop constraint FKF2FAB22D15319905;
alter table ROLE_FUNCTION drop constraint FK57D5418176082B46;
alter table ROLE_FUNCTION drop constraint FK57D54181B3A8FCA;
alter table RULE_ACTION drop constraint FKE5D84CD95026A965;
alter table RULE_ACTION drop constraint FKE5D84CD9E47C873A;
alter table SEARCH_CONTENT drop constraint FKBC1F98C28204EADD;
alter table SHEET_POLICY_SETTING drop constraint FK9D378B23D5ECC7D9;
alter table USABLE_INFO drop constraint FK40A1D79526290812;
drop table ACTION_USABLE_ITEM;
drop table ADDRESS;
drop table ANKEN;
drop table ANKEN_AUTOCOUNT;
drop table ANKEN_CHECKBOX;
drop table ANKEN_CHECKBOX_HIST;
drop table ANKEN_COMPETITOR;
drop table ANKEN_EMPLOYEE;
drop table ANKEN_EMP_GROUP;
drop table ANKEN_HIST;
drop table ANKEN_LIST;
drop table ANKEN_LIST_REC;
drop table BPM_CHECK_ITEM;
drop table BPM_FUNCTION;
drop table BPM_ITEM;
drop table BPM_MENU;
drop table BPM_RELATED_SELECT_ITEM_1;
drop table BPM_RELATED_SELECT_ITEM_2;
drop table BPM_RULE;
drop table BPM_SELECT_ITEM;
drop table BPM_SELECT_RELATION;
drop table BPM_SUBMENU;
drop table BPM_TYPE;
drop table BPM_TYPE_KIND;
drop table BPM_URL;
drop table COMMON_PARAMETER;
drop table COMPETITOR;
drop table CORPORATION;
drop table CORPORATION_AUTOCOUNT;
drop table CORPORATION_CHECKBOX;
drop table COUNTRY;
drop table CURRENCY;
drop table DATA_SEARCH_METHOD;
drop table DEPART;
drop table DOCUMENT_FILE;
drop table DOCUMENT_FILE_HIST;
drop table DOCUMENT_FOLDER;
drop table EMPLOYEE;
drop table EMPLOYEE_AUTOCOUNT;
drop table EMPLOYEE_CHECKBOX;
drop table EMPLOYEE_CHECKBOX_HIST;
drop table EMPLOYEE_GROUP;
drop table EMPLOYEE_HIST;
drop table EMPLOYEE_LICENSE;
drop table EMPLOYEE_LICENSE_HIST;
drop table EMPLOYEE_LICENSE_TEMP;
drop table EMPLOYEE_ROLE;
drop table EMP_GROUP_MEMBER;
drop table ENTITY_BELONG;
drop table FAVORITE_SEARCH_COND;
drop table FILE_PERM;
drop table FILE_TYPE;
drop table FILE_UPLOAD_EMPLOYEE;
drop table FILE_UPLOAD_HISTORY;
drop table HOLIDAY;
drop table INDIVIDUAL_VIEW;
drop table JOB_INSTANCE;
drop table JOB_RESULT;
drop table LANGUAGE;
drop table LICENSE;
drop table LICENSE_FUNCTION;
drop table LICENSE_KIND;
drop table LICENSE_MAINTENANCE_HIST;
drop table LICENSE_MASTER_KIND;
drop table LIST_PORTAL_VIEW;
drop table LOCALE_MESSAGE;
drop table MANAGER;
drop table MENU_BAR_SETTING;
drop table MESSAGE;
drop table MESSAGE_APPFILE;
drop table MESSAGE_FOLDER;
drop table MESSAGE_SENDTO;
drop table MESSAGE_TEMPLATE;
drop table MULTI_FORM_VIEW;
drop table MULTI_VIEW_ITEM;
drop table NAVIGATION_BAR_SETTING;
drop table OFFICE;
drop table OFFICE_AUTOCOUNT;
drop table OFFICE_CHECKBOX;
drop table POLICY_LEVEL;
drop table POST;
drop table PRODUCT;
drop table PRODUCT_AUTOCOUNT;
drop table PRODUCT_CHECKBOX;
drop table PRODUCT_CHECKBOX_HIST;
drop table PRODUCT_HIST;
drop table PROGRESS;
drop table PROGRESS_ACTION_RELATION;
drop table PROGRESS_CATEGORY;
drop table PROGRESS_USABLE_ITEM;
drop table RECEIVE_MESSAGE;
drop table ROLE;
drop table ROLE_FUNCTION;
drop table RULE_ACTION;
drop table RULE_ACTION_INFO;
drop table RULE_CONDITION;
drop table SEARCH_CONDITION;
drop table SEARCH_CONDITION_DIVISION;
drop table SEARCH_CONTENT;
drop table SEARCH_METHOD_DIVISION;
drop table SEND_MESSAGE;
drop table SHEET_POLICY_SETTING;
drop table TYPE_USABLE_INFO;
drop table USABLE_ACTION;
drop table USABLE_INFO;
drop table USABLE_ITEM;
drop table USABLE_PRODUCT_PROGRESS;
drop table USABLE_PROGRESS;
drop table VIEW_CASE;
drop table WORKDAY;
drop sequence 
            SEQ_ACTION_USABLE_ITEM_ID
        ;
drop sequence 
            SEQ_ANKEN_EMP_GRP_ID
        ;
drop sequence 
            SEQ_ANKEN_HIST_ID
        ;
drop sequence 
            SEQ_ANKEN_ID
        ;
drop sequence 
            SEQ_ANKEN_LIST_ID
        ;
drop sequence 
            SEQ_ANKEN_LIST_REC_ID
        ;
drop sequence 
            SEQ_BPM_ITEM_ITEM_ID
        ;
drop sequence 
            SEQ_BPM_RLT_SEL_ITM_1_ID
        ;
drop sequence 
            SEQ_BPM_RLT_SEL_ITM_2_ID
        ;
drop sequence 
            SEQ_BPM_RULE_ID
        ;
drop sequence 
            SEQ_BPM_SEL_REL_ID
        ;
drop sequence 
            SEQ_BPM_TYPE_ID
        ;
drop sequence 
            SEQ_COMPETITOR_ID
        ;
drop sequence 
            SEQ_CORPORATION_ID
        ;
drop sequence 
            SEQ_DEPART_ID
        ;
drop sequence 
            SEQ_DOC_FILE_FOLDER_ID
        ;
drop sequence 
            SEQ_DOC_FILE_ID
        ;
drop sequence 
            SEQ_EMPLOYEE_GROUP_ID
        ;
drop sequence 
            SEQ_EMPLOYEE_HIST_ID
        ;
drop sequence 
            SEQ_EMPLOYEE_ID
        ;
drop sequence 
            SEQ_EMPLOYEE_LICENSE_HIST_ID
        ;
drop sequence 
            SEQ_FAVORITE_SEARCH_COND_ID
        ;
drop sequence 
            SEQ_FILE_PERM_ID
        ;
drop sequence 
            SEQ_FILE_TYPE_ID
        ;
drop sequence 
            SEQ_FILE_UPLOAD_HISTORY_ID
        ;
drop sequence 
            SEQ_HOLIDAY_ID
        ;
drop sequence 
            SEQ_INDIVIDUAL_VIEW_ID
        ;
drop sequence 
            SEQ_JOB_INSTANCE_ID
        ;
drop sequence 
            SEQ_JOB_RESULT_ID
        ;
drop sequence 
            SEQ_LICENSE_ID
        ;
drop sequence 
            SEQ_LICENSE_KIND_ID
        ;
drop sequence 
            SEQ_LICENSE_MAIN_HIST_ID
        ;
drop sequence 
            SEQ_LICENSE_MASTER_KIND_ID
        ;
drop sequence 
            SEQ_LIST_PORTAL_VIEW_ID
        ;
drop sequence 
            SEQ_MANAGER_SETTING_ID
        ;
drop sequence 
            SEQ_MENU_BAR_SETTING_ID
        ;
drop sequence 
            SEQ_MESSAGE_APPFILE_ID
        ;
drop sequence 
            SEQ_MESSAGE_FOLDER_ID
        ;
drop sequence 
            SEQ_MESSAGE_SENDTO_ID
        ;
drop sequence 
            SEQ_MESSAGE_TEMPLATE_ID
        ;
drop sequence 
            SEQ_MULTI_FORM_VIEW_ID
        ;
drop sequence 
            SEQ_MULTI_VIEW_ITEM_ID
        ;
drop sequence 
            SEQ_NAVIGATION_BAR_SETTING_ID
        ;
drop sequence 
            SEQ_OFFICE_ID
        ;
drop sequence 
            SEQ_POST_ID
        ;
drop sequence 
            SEQ_PRODUCT_HIST_ID
        ;
drop sequence 
            SEQ_PRODUCT_ID
        ;
drop sequence 
            SEQ_PROGRESS_ACT_REL_PAR_ID
        ;
drop sequence 
            SEQ_PROGRESS_CATEGORY_ID
        ;
drop sequence 
            SEQ_PROGRESS_ID
        ;
drop sequence 
            SEQ_PROG_USABLE_ITEM_PUI_ID
        ;
drop sequence 
            SEQ_RECEIVE_MESSAGE_ID
        ;
drop sequence 
            SEQ_ROLE_ID
        ;
drop sequence 
            SEQ_RULE_ACTION_ID
        ;
drop sequence 
            SEQ_SEARCH_CONDITION_ID
        ;
drop sequence 
            SEQ_SEARCH_CONTENT_ID
        ;
drop sequence 
            SEQ_SEND_MESSAGE_ID
        ;
drop sequence 
            SEQ_TYPE_USABLE_INFO_ID
        ;
drop sequence 
            SEQ_USABLE_INFO_ID
        ;
drop sequence 
            SEQ_USABLE_ITEM_ID
        ;
drop sequence 
            SEQ_USABLE_PDT_PGS_ID
        ;
drop sequence 
            SEQ_USABLE_PROGRESS_ID
        ;
drop sequence hibernate_sequence;
create table ACTION_USABLE_ITEM (AUI_ID int8 not null, TUI_ID int8 not null, ITEM_ID int8 not null, ACTION_ID int8 not null, SHOW_FLG int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (AUI_ID));
create table ADDRESS (VERSION int8 not null, ZIP_CODE varchar(20) not null, PREFECTURE varchar(100) not null, CITY varchar(100) not null, TOWN varchar(100) not null, ZIP_CODE_UNIFIED varchar(20), PLACE_CODE varchar(100), OLD_ZIP_CODE varchar(10), OFFICE_DIVISION int8, SPECIAL_CODE_KIND int8, SPECIAL_CODE_DIVISION int8, OFFICE_KANA varchar(100), OFFICE_NAME varchar(100), PREFECTURE_KANA varchar(100), CITY_KANA varchar(100), TOWN_KANA varchar(100), OTHER_KANA varchar(100), OTHER varchar(100), CAN_USE int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (VERSION, ZIP_CODE, PREFECTURE, CITY, TOWN));
create table ANKEN (ANKEN_ID int8 not null, ANKEN_NUMBER varchar(100), CORPORATION_ID int8, OFFICE_ID int8, ANKEN_NAME varchar(200) not null, ANKEN_TYPE int8, ROUTE int8, STATUS int8, CLOSE_DATE timestamp, PARENT_ANKEN_ID int8, PROGRESS_ID int8, TAKING_AMOUNT int8, TAKING_AMOUNT_CURRENCY_ID int8, TAKING_AMOUNT_SYS int8, TAKING_DATE timestamp, BUDGET int8, AUTHERNTICATION int8, NEEDS int8, TIMING int8, COMPETITOR_FLG int8, TAKING_POSSIBILITY int8, DBC_VARCHAR_1 varchar(1000), DBC_VARCHAR_2 varchar(1000), DBC_VARCHAR_3 varchar(1000), DBC_VARCHAR_4 varchar(1000), DBC_VARCHAR_5 varchar(1000), DBC_TEXTAREA_1 varchar(4000), DBC_TEXTAREA_2 varchar(4000), DBC_TEXTAREA_3 varchar(4000), DBC_TEXTAREA_4 varchar(4000), DBC_TEXTAREA_5 varchar(4000), DBC_INTEGER_1 int8, DBC_INTEGER_2 int8, DBC_INTEGER_3 int8, DBC_INTEGER_4 int8, DBC_INTEGER_5 int8, DBC_SELECT_1 int8, DBC_SELECT_2 int8, DBC_SELECT_3 int8, DBC_SELECT_4 int8, DBC_SELECT_5 int8, DBC_REAL_1 varchar(255), DBC_REAL_2 varchar(255), DBC_REAL_3 varchar(255), DBC_REAL_4 varchar(255), DBC_REAL_5 varchar(255), DBC_DATE_1 timestamp, DBC_DATE_2 timestamp, DBC_DATE_3 timestamp, DBC_DATE_4 timestamp, DBC_DATE_5 timestamp, DBC_FILE_1 int8, DBC_FILE_2 int8, DBC_FILE_3 int8, DBC_FILE_4 int8, DBC_FILE_5 int8, DBC_RELSEL_1_1 int8, DBC_RELSEL_1_2 int8, DBC_RELSEL_2_1 int8, DBC_RELSEL_2_2 int8, DBC_RELSEL_3_1 int8, DBC_RELSEL_3_2 int8, DBC_RELSEL_4_1 int8, DBC_RELSEL_4_2 int8, DBC_RELSEL_5_1 int8, DBC_RELSEL_5_2 int8, DBC_YEARMONTH_1 varchar(6), DBC_YEARMONTH_2 varchar(6), DBC_YEARMONTH_3 varchar(6), DBC_YEARMONTH_4 varchar(6), DBC_YEARMONTH_5 varchar(6), DBC_AUTOCOUNT_1 varchar(20), DBC_AUTOCOUNT_2 varchar(20), DBC_AUTOCOUNT_3 varchar(20), DBC_AUTOCOUNT_4 varchar(20), DBC_AUTOCOUNT_5 varchar(20), DBC_MASTER_1 int8, DBC_MASTER_2 int8, DBC_MASTER_3 int8, DBC_MASTER_4 int8, DBC_MASTER_5 int8, DBC_TELNO_1 varchar(30), DBC_TELNO_1_UNIFIED varchar(30), DBC_TELNO_2 varchar(30), DBC_TELNO_2_UNIFIED varchar(30), DBC_TELNO_3 varchar(30), DBC_TELNO_3_UNIFIED varchar(30), DBC_TELNO_4 varchar(30), DBC_TELNO_4_UNIFIED varchar(30), DBC_TELNO_5 varchar(30), DBC_TELNO_5_UNIFIED varchar(30), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ANKEN_ID));
create table ANKEN_AUTOCOUNT (AUTOCOUNT_ID int8 not null, ANKEN_NUMBER int8, DBC_AUTOCOUNT_1 int8, DBC_AUTOCOUNT_2 int8, DBC_AUTOCOUNT_3 int8, DBC_AUTOCOUNT_4 int8, DBC_AUTOCOUNT_5 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (AUTOCOUNT_ID));
create table ANKEN_CHECKBOX (ANKEN_ID int8 not null, ITEM_ID int8 not null, DATA_1 int8, DATA_2 int8, DATA_3 int8, DATA_4 int8, DATA_5 int8, DATA_6 int8, DATA_7 int8, DATA_8 int8, DATA_9 int8, DATA_10 int8, DATA_11 int8, DATA_12 int8, DATA_13 int8, DATA_14 int8, DATA_15 int8, DATA_16 int8, DATA_17 int8, DATA_18 int8, DATA_19 int8, DATA_20 int8, DATA_21 int8, DATA_22 int8, DATA_23 int8, DATA_24 int8, DATA_25 int8, DATA_26 int8, DATA_27 int8, DATA_28 int8, DATA_29 int8, DATA_30 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ANKEN_ID, ITEM_ID));
create table ANKEN_CHECKBOX_HIST (ANKEN_HIST_ID int8 not null, ANKEN_ID int8 not null, ITEM_ID int8 not null, DATA_1 int8, DATA_2 int8, DATA_3 int8, DATA_4 int8, DATA_5 int8, DATA_6 int8, DATA_7 int8, DATA_8 int8, DATA_9 int8, DATA_10 int8, DATA_11 int8, DATA_12 int8, DATA_13 int8, DATA_14 int8, DATA_15 int8, DATA_16 int8, DATA_17 int8, DATA_18 int8, DATA_19 int8, DATA_20 int8, DATA_21 int8, DATA_22 int8, DATA_23 int8, DATA_24 int8, DATA_25 int8, DATA_26 int8, DATA_27 int8, DATA_28 int8, DATA_29 int8, DATA_30 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ANKEN_HIST_ID, ANKEN_ID, ITEM_ID));
create table ANKEN_COMPETITOR (ANKEN_ID int8 not null, COMPETITOR_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ANKEN_ID, COMPETITOR_ID));
create table ANKEN_EMPLOYEE (ANKEN_ID int8 not null, EMPLOYEE_ID int8 not null, MAIN_CHARGE_FLG int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ANKEN_ID, EMPLOYEE_ID));
create table ANKEN_EMP_GROUP (ANKEN_EMP_GROUP_ID int8 not null, ANKEN_ID int8, DEPART_ID int8, EMPLOYEE_GROUP_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ANKEN_EMP_GROUP_ID));
create table ANKEN_HIST (ANKEN_HIST_ID int8 not null, ANKEN_ID int8, ANKEN_NUMBER varchar(100), CORPORATION_ID int8, OFFICE_ID int8, ANKEN_NAME varchar(200) not null, ANKEN_TYPE int8, ROUTE int8, STATUS int8, CLOSE_DATE timestamp, PARENT_ANKEN_ID int8, PROGRESS_ID int8, TAKING_AMOUNT int8, TAKING_AMOUNT_CURRENCY_ID int8, TAKING_AMOUNT_SYS int8, TAKING_DATE timestamp, BUDGET int8, AUTHERNTICATION int8, NEEDS int8, TIMING int8, COMPETITOR_FLG int8, TAKING_POSSIBILITY int8, DBC_VARCHAR_1 varchar(1000), DBC_VARCHAR_2 varchar(1000), DBC_VARCHAR_3 varchar(1000), DBC_VARCHAR_4 varchar(1000), DBC_VARCHAR_5 varchar(1000), DBC_TEXTAREA_1 varchar(4000), DBC_TEXTAREA_2 varchar(4000), DBC_TEXTAREA_3 varchar(4000), DBC_TEXTAREA_4 varchar(4000), DBC_TEXTAREA_5 varchar(4000), DBC_INTEGER_1 int8, DBC_INTEGER_2 int8, DBC_INTEGER_3 int8, DBC_INTEGER_4 int8, DBC_INTEGER_5 int8, DBC_SELECT_1 int8, DBC_SELECT_2 int8, DBC_SELECT_3 int8, DBC_SELECT_4 int8, DBC_SELECT_5 int8, DBC_REAL_1 varchar(255), DBC_REAL_2 varchar(255), DBC_REAL_3 varchar(255), DBC_REAL_4 varchar(255), DBC_REAL_5 varchar(255), DBC_DATE_1 timestamp, DBC_DATE_2 timestamp, DBC_DATE_3 timestamp, DBC_DATE_4 timestamp, DBC_DATE_5 timestamp, DBC_FILE_1 int8, DBC_FILE_2 int8, DBC_FILE_3 int8, DBC_FILE_4 int8, DBC_FILE_5 int8, DBC_RELSEL_1_1 int8, DBC_RELSEL_1_2 int8, DBC_RELSEL_2_1 int8, DBC_RELSEL_2_2 int8, DBC_RELSEL_3_1 int8, DBC_RELSEL_3_2 int8, DBC_RELSEL_4_1 int8, DBC_RELSEL_4_2 int8, DBC_RELSEL_5_1 int8, DBC_RELSEL_5_2 int8, DBC_YEARMONTH_1 varchar(6), DBC_YEARMONTH_2 varchar(6), DBC_YEARMONTH_3 varchar(6), DBC_YEARMONTH_4 varchar(6), DBC_YEARMONTH_5 varchar(6), DBC_AUTOCOUNT_1 varchar(20), DBC_AUTOCOUNT_2 varchar(20), DBC_AUTOCOUNT_3 varchar(20), DBC_AUTOCOUNT_4 varchar(20), DBC_AUTOCOUNT_5 varchar(20), DBC_MASTER_1 int8, DBC_MASTER_2 int8, DBC_MASTER_3 int8, DBC_MASTER_4 int8, DBC_MASTER_5 int8, DBC_TELNO_1 varchar(30), DBC_TELNO_1_UNIFIED varchar(30), DBC_TELNO_2 varchar(30), DBC_TELNO_2_UNIFIED varchar(30), DBC_TELNO_3 varchar(30), DBC_TELNO_3_UNIFIED varchar(30), DBC_TELNO_4 varchar(30), DBC_TELNO_4_UNIFIED varchar(30), DBC_TELNO_5 varchar(30), DBC_TELNO_5_UNIFIED varchar(30), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ANKEN_HIST_ID));
create table ANKEN_LIST (ANKEN_LIST_ID int8 not null, LIST_NAME varchar(100), DESCRIPTION varchar(4000), EMPLOYEE_ID int8, DEPART_ID int8, EMPLOYEE_GROUP_ID int8, SEARCH_COND_FLG int4, SEARCH_COND_ID int8, AUTOREFRESH_FLG int4, UPDATE_TYPE int4, LIST_ORDER int4, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ANKEN_LIST_ID));
create table ANKEN_LIST_REC (ANKEN_LIST_REC_ID int8 not null, ANKEN_LIST_ID int8, ANKEN_ID int8, DELETE_FLG int4, REC_ORDER int4, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ANKEN_LIST_REC_ID), unique (ANKEN_LIST_ID, ANKEN_ID));
create table BPM_CHECK_ITEM (CHECK_ITEM_ID int8 not null, ITEM_ID int8 not null, CHECK_ITEM_NAME_ID varchar(100) not null, USE_FLG int8 not null, CHECK_ITEM_ORDER int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (CHECK_ITEM_ID, ITEM_ID));
create table BPM_FUNCTION (BPM_FUNCTION_ID int8 not null, BPM_FUNCTION_NAME varchar(100), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (BPM_FUNCTION_ID));
create table BPM_ITEM (ITEM_ID int8 not null, ITEM_NAME_ID varchar(100), INPUT_CAUTION varchar(100), DESCRIPTION varchar(100), ENTITY_BELONG int8 not null, ITEM_TYPE int8 not null, PROPERTY_NAME varchar(100), USE_FLG int8, MUST_ENTER_FLG int8, EXTRA_ITEM_FLG int8, IME_MODE int8, REF_ITEM_ID int8, COL_SIZE int8, INPUT_SIZE int8, INPUT_MAXLENGTH int8, DEFAULT_VALUE varchar(100), NUM_UNIT varchar(100), RESOLUTION int8, DATE_MODE int8, PART_LINE_TYPE int8, MASTER_BELONG_ID int8, SEQ_NAME varchar(100), AUTOCOUNT_LENGTH int8, AUTOCOUNT_PREFIX varchar(100), CALC_ITEM_ID_1 int8, CALC_ITEM_ID_2 int8, CALC_MODE int8, RADIO_FLG int8, REF_PERM_LEVEL int8, UPD_PERM_LEVEL int8, ALERT_ON_CHANGE int8, IS_UNIQUE int8, ITEM_ORDER int8 not null, HALF_WIDTH_FLG int8, LINK_FLG int8, PARAMETER_LINK_URL varchar(500), PARAMETER_LINK_NAME varchar(200), CORP_ITEM_KIND_FLG int8, TAG_CLASS_NAME varchar(255), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ITEM_ID));
create table BPM_MENU (BPM_MENU_ID int8 not null, MENU_NAME_ID varchar(255), IMAGE_FILE_PATH varchar(255), LINK_ID int8, PC_BP_FLG int8, MENU_FLG int8, MENU_ORDER int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (BPM_MENU_ID));
create table BPM_RELATED_SELECT_ITEM_1 (RELATED_SELECT_ITEM_ID int8 not null, SELECT_ITEM_ID int8 not null, ITEM_ID int8, SELECT_ITEM_NAME_ID varchar(100) not null, USE_FLG int8 not null, SELECT_ITEM_ORDER int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (RELATED_SELECT_ITEM_ID));
create table BPM_RELATED_SELECT_ITEM_2 (RELATED_SELECT_ITEM_ID int8 not null, SELECT_ITEM_ID int8 not null, ITEM_ID int8, SELECT_ITEM_NAME_ID varchar(100) not null, USE_FLG int8 not null, SELECT_ITEM_ORDER int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (RELATED_SELECT_ITEM_ID));
create table BPM_RULE (RULE_ID int8 not null, RULE_NAME_ID int8, EMPLOYEE_ID int8, DATA_TYPE int8, EXEC_EVENT int8, JOB_NAME varchar(255), JOB_GROUP varchar(255), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (RULE_ID));
create table BPM_SELECT_ITEM (SELECT_ITEM_ID int8 not null, ITEM_ID int8 not null, SELECT_ITEM_NAME_ID varchar(100) not null, USE_FLG int8 not null, SELECT_ITEM_ORDER int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (SELECT_ITEM_ID, ITEM_ID));
create table BPM_SELECT_RELATION (SELECT_ITEM_RELATION_ID int8 not null, RELATED_SELECT_ITEM_ID_1 int8, RELATED_SELECT_ITEM_ID_2 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (SELECT_ITEM_RELATION_ID));
create table BPM_SUBMENU (BPM_SUBMENU_ID int8 not null, BPM_SUBMENU_NAME_ID varchar(255), LINK_ID int8, PC_FLG int8, CAN_USE_MOBILE int8, BP_FLG int8, MENU_ORDER int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, BPM_MENU_ID int8, primary key (BPM_SUBMENU_ID));
create table BPM_TYPE (TYPE_ID int8 not null, TYPE_NAME varchar(200), TYPE_KIND_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (TYPE_ID));
create table BPM_TYPE_KIND (TYPE_KIND_ID int8 not null, TYPE_KIND_NAME varchar(200), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (TYPE_KIND_ID));
create table BPM_URL (LINK_ID int8 not null, BPM_FUNCTION_ID int8, PAGE_ID varchar(20), LINK_NAME_ID varchar(100) not null, LINK_URL varchar(500), CAN_USE_MENU int8, CAN_USE_MOBILE int8, FORM_KIND varchar(255), FORM_STYLE varchar(255), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (LINK_ID));
create table COMMON_PARAMETER (PARAMETER_ID int8 not null, MODIFIABLE int8, VALUE varchar(500), CACHE_NAME varchar(100), PARAMETER_KEY varchar(100) not null, CLASSIFICATION varchar(100) not null, EXPORT_FLG int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (PARAMETER_ID), unique (PARAMETER_KEY, CLASSIFICATION));
create table COMPETITOR (COMPETITOR_ID int8 not null, CORPORATOIN_ID int8, PRODUCT_ID int8, PRODUCT_NAME varchar(200), DESCRIPTION varchar(4000), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (COMPETITOR_ID));
create table CORPORATION (CORPORATION_ID int8 not null, CORPORATION_NUMBER varchar(100), CORPORATION_TYPE int8, CORPORATION_NAME varchar(200) not null, CORPORATION_NAME_ID varchar(100), CORPORATION_KANA varchar(200), ABBREVIATION int8, ZIP_CODE varchar(20), ZIP_CODE_UNIFIED varchar(20), ADDRESS varchar(200), ADDRESS_2 varchar(200), TEL_NO varchar(30), TEL_NO_UNIFIED varchar(30), FAX_NO varchar(30), FAX_NO_UNIFIED varchar(30), HP_URL varchar(500), DESCRIPTION varchar(4000), STOCK_EXCHANGE_FLG int8, PRESIDENT_NAME varchar(50), PRESIDENT_KANA varchar(50), ESTABLISH_DATE timestamp, CAPITAL varchar(255), CAPITAL_CURRENCY_ID int8, CAPITAL_SYS varchar(255), EMPLOYEE_NUM int8, CORPORATION_GROUP_ID int8, PARTNER_FLG int8, MARGIN_RATE varchar(255), PAYMENT_SITE varchar(500), CLAIM_PERSON_ID int8, CHANGE_PERSON_ID int8, COMPETITOR_FLG int8, RANK int8, DELETE_FLG int8, DBC_VARCHAR_1 varchar(1000), DBC_VARCHAR_2 varchar(1000), DBC_VARCHAR_3 varchar(1000), DBC_VARCHAR_4 varchar(1000), DBC_VARCHAR_5 varchar(1000), DBC_TEXTAREA_1 varchar(4000), DBC_TEXTAREA_2 varchar(4000), DBC_TEXTAREA_3 varchar(4000), DBC_TEXTAREA_4 varchar(4000), DBC_TEXTAREA_5 varchar(4000), DBC_INTEGER_1 int8, DBC_INTEGER_2 int8, DBC_INTEGER_3 int8, DBC_INTEGER_4 int8, DBC_INTEGER_5 int8, DBC_SELECT_1 int8, DBC_SELECT_2 int8, DBC_SELECT_3 int8, DBC_SELECT_4 int8, DBC_SELECT_5 int8, DBC_REAL_1 varchar(255), DBC_REAL_2 varchar(255), DBC_REAL_3 varchar(255), DBC_REAL_4 varchar(255), DBC_REAL_5 varchar(255), DBC_DATE_1 timestamp, DBC_DATE_2 timestamp, DBC_DATE_3 timestamp, DBC_DATE_4 timestamp, DBC_DATE_5 timestamp, DBC_FILE_1 int8, DBC_FILE_2 int8, DBC_FILE_3 int8, DBC_FILE_4 int8, DBC_FILE_5 int8, DBC_RELSEL_1_1 int8, DBC_RELSEL_1_2 int8, DBC_RELSEL_2_1 int8, DBC_RELSEL_2_2 int8, DBC_RELSEL_3_1 int8, DBC_RELSEL_3_2 int8, DBC_RELSEL_4_1 int8, DBC_RELSEL_4_2 int8, DBC_RELSEL_5_1 int8, DBC_RELSEL_5_2 int8, DBC_YEARMONTH_1 varchar(6), DBC_YEARMONTH_2 varchar(6), DBC_YEARMONTH_3 varchar(6), DBC_YEARMONTH_4 varchar(6), DBC_YEARMONTH_5 varchar(6), DBC_AUTOCOUNT_1 varchar(20), DBC_AUTOCOUNT_2 varchar(20), DBC_AUTOCOUNT_3 varchar(20), DBC_AUTOCOUNT_4 varchar(20), DBC_AUTOCOUNT_5 varchar(20), DBC_MASTER_1 int8, DBC_MASTER_2 int8, DBC_MASTER_3 int8, DBC_MASTER_4 int8, DBC_MASTER_5 int8, DBC_TELNO_1 varchar(30), DBC_TELNO_1_UNIFIED varchar(30), DBC_TELNO_2 varchar(30), DBC_TELNO_2_UNIFIED varchar(30), DBC_TELNO_3 varchar(30), DBC_TELNO_3_UNIFIED varchar(30), DBC_TELNO_4 varchar(30), DBC_TELNO_4_UNIFIED varchar(30), DBC_TELNO_5 varchar(30), DBC_TELNO_5_UNIFIED varchar(30), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (CORPORATION_ID));
create table CORPORATION_AUTOCOUNT (AUTOCOUNT_ID int8 not null, CORPORATION_NUMBER int8, DBC_AUTOCOUNT_1 int8, DBC_AUTOCOUNT_2 int8, DBC_AUTOCOUNT_3 int8, DBC_AUTOCOUNT_4 int8, DBC_AUTOCOUNT_5 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (AUTOCOUNT_ID));
create table CORPORATION_CHECKBOX (CORPORATION_ID int8 not null, ITEM_ID int8 not null, DATA_1 int8, DATA_2 int8, DATA_3 int8, DATA_4 int8, DATA_5 int8, DATA_6 int8, DATA_7 int8, DATA_8 int8, DATA_9 int8, DATA_10 int8, DATA_11 int8, DATA_12 int8, DATA_13 int8, DATA_14 int8, DATA_15 int8, DATA_16 int8, DATA_17 int8, DATA_18 int8, DATA_19 int8, DATA_20 int8, DATA_21 int8, DATA_22 int8, DATA_23 int8, DATA_24 int8, DATA_25 int8, DATA_26 int8, DATA_27 int8, DATA_28 int8, DATA_29 int8, DATA_30 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (CORPORATION_ID, ITEM_ID));
create table COUNTRY (COUNTRY_ID int8 not null, ENG_COUNTRY_NAME varchar(100) not null, ABBREVIATION varchar(100), CAN_USE int8, CAN_DELETE int8, LANGUAGE_ID int8, TIMEZONE int8, TIME_FORMAT int8, CURRENCY_ID int8, COUNTRY_TEL_NO varchar(10), ADDRESS_EASY_INPUT int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (COUNTRY_ID));
create table CURRENCY (CURRENCY_ID int8 not null, CURRENCY_NAME_ID varchar(100) not null, CURRENCY_RATE float8, CAN_ENTRY int8, BASE_CURRENCY_FLG int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (CURRENCY_ID));
create table DATA_SEARCH_METHOD (DATA_KIND_ID int8 not null, SEARCH_METHOD_DIVISION_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (DATA_KIND_ID));
create table DEPART (DEPART_ID int8 not null, DEPART_NUMBER varchar(100), DEPART_NAME varchar(100), DEPART_BELONG int8, DEPART_DEPTH int8, DEPART_ID_1 int8, DEPART_ID_2 int8, DEPART_ID_3 int8, DEPART_ID_4 int8, DEPART_ID_5 int8, DEPART_ID_6 int8, DEPART_ID_7 int8, DEPART_ID_8 int8, DEPART_ID_9 int8, DEPART_ID_10 int8, DEPART_ID_11 int8, DEPART_ID_12 int8, DEPART_ID_13 int8, DEPART_ID_14 int8, DEPART_ID_15 int8, DEPART_ORDER int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (DEPART_ID));
create table DOCUMENT_FILE (FILE_ID int8 not null, UPLOAD_EMPLOYEE_ID int8, UPLOAD_TIME timestamp, FILE_SIZE int8, FOLDER_ID int8, SHEET_ID int8, ITEM_ID int8, FILE_TYPE_ID int8, FILE_NAME varchar(500), HISTORY_FLG int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (FILE_ID));
create table DOCUMENT_FILE_HIST (FILE_HIST_ID int8 not null, FILE_ID int8, UPLOAD_EMPLOYEE_ID int8, UPLOAD_TIME timestamp, FILE_SIZE int8, FOLDER_ID int8, SHEET_ID int8, ITEM_ID int8, FILE_NAME varchar(500) not null, FILE_TYPE_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (FILE_HIST_ID));
create table DOCUMENT_FOLDER (FOLDER_ID int8 not null, FOLDER_DISP_NAME varchar(500) not null, FOLDER_NAME varchar(500), PARENT_FOLDER_ID int8 not null, FOLDER_DEPTH int8, SHEET_KIND int8, SHEET_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (FOLDER_ID));
create table EMPLOYEE (EMPLOYEE_ID int8 not null, EMPLOYEE_NUMBER varchar(100), EMPLOYEE_NAME varchar(100) not null, EMPLOYEE_NAME_ID varchar(100), EMPLOYEE_KANA varchar(100), ZIP_CODE varchar(20), ZIP_CODE_UNIFIED varchar(20), ADDRESS varchar(200), ADDRESS_2 varchar(200), TEL_NO varchar(50), TEL_NO_UNIFIED varchar(50), MOB_TEL_NO varchar(50), MOB_TEL_NO_UNIFIED varchar(50), TEL_NO_OTHER varchar(50), TEL_NO_OTHER_UNIFIED varchar(50), EMAIL varchar(100), MOB_EMAIL varchar(100), EMAIL_OTHER varchar(100), PASSWORD varchar(50), COUNTRY_ID int8, LANGUAGE_ID int8, DATETIME_FORMAT int8, TIMEZONE varchar(100), CURRENCY_ID int8, PHOTO_FILE_ID int8, DEPART_ID int8, POST_ID int8, CO_DEPART_ID_1 int8, CO_POST_ID_1 int8, CO_DEPART_ID_2 int8, CO_POST_ID_2 int8, CO_DEPART_ID_3 int8, CO_POST_ID_3 int8, CO_DEPART_ID_4 int8, CO_POST_ID_4 int8, CO_DEPART_ID_5 int8, CO_POST_ID_5 int8, EMPLOYEE_ORDER int8, PWD_LAST_UPDATE timestamp, PWD_TERM_CAUTION_FLG int8, FIRST_LOGIN_FLG int8, LOCK_DATE timestamp, LOGIN_ERROR_NUM int8, MOBILE_ID varchar(200), MOBILE_ID_AUTH_FLG int8, AUTO_LOGIN_PASSWORD int8, AUTO_LOGIN_URL_SEND_DATE timestamp, AUTO_LOGIN_URL_CAUTION_FLG int8, DBC_VARCHAR_1 varchar(1000), DBC_VARCHAR_2 varchar(1000), DBC_VARCHAR_3 varchar(1000), DBC_VARCHAR_4 varchar(1000), DBC_VARCHAR_5 varchar(1000), DBC_TEXTAREA_1 varchar(4000), DBC_TEXTAREA_2 varchar(4000), DBC_TEXTAREA_3 varchar(4000), DBC_TEXTAREA_4 varchar(4000), DBC_TEXTAREA_5 varchar(4000), DBC_INTEGER_1 int8, DBC_INTEGER_2 int8, DBC_INTEGER_3 int8, DBC_INTEGER_4 int8, DBC_INTEGER_5 int8, DBC_SELECT_1 int8, DBC_SELECT_2 int8, DBC_SELECT_3 int8, DBC_SELECT_4 int8, DBC_SELECT_5 int8, DBC_REAL_1 varchar(255), DBC_REAL_2 varchar(255), DBC_REAL_3 varchar(255), DBC_REAL_4 varchar(255), DBC_REAL_5 varchar(255), DBC_DATE_1 timestamp, DBC_DATE_2 timestamp, DBC_DATE_3 timestamp, DBC_DATE_4 timestamp, DBC_DATE_5 timestamp, DBC_FILE_1 int8, DBC_FILE_2 int8, DBC_FILE_3 int8, DBC_FILE_4 int8, DBC_FILE_5 int8, DBC_RELSEL_1_1 int8, DBC_RELSEL_1_2 int8, DBC_RELSEL_2_1 int8, DBC_RELSEL_2_2 int8, DBC_RELSEL_3_1 int8, DBC_RELSEL_3_2 int8, DBC_RELSEL_4_1 int8, DBC_RELSEL_4_2 int8, DBC_RELSEL_5_1 int8, DBC_RELSEL_5_2 int8, DBC_YEARMONTH_1 varchar(6), DBC_YEARMONTH_2 varchar(6), DBC_YEARMONTH_3 varchar(6), DBC_YEARMONTH_4 varchar(6), DBC_YEARMONTH_5 varchar(6), DBC_AUTOCOUNT_1 varchar(20), DBC_AUTOCOUNT_2 varchar(20), DBC_AUTOCOUNT_3 varchar(20), DBC_AUTOCOUNT_4 varchar(20), DBC_AUTOCOUNT_5 varchar(20), DBC_MASTER_1 int8, DBC_MASTER_2 int8, DBC_MASTER_3 int8, DBC_MASTER_4 int8, DBC_MASTER_5 int8, DBC_TELNO_1 varchar(30), DBC_TELNO_1_UNIFIED varchar(30), DBC_TELNO_2 varchar(30), DBC_TELNO_2_UNIFIED varchar(30), DBC_TELNO_3 varchar(30), DBC_TELNO_3_UNIFIED varchar(30), DBC_TELNO_4 varchar(30), DBC_TELNO_4_UNIFIED varchar(30), DBC_TELNO_5 varchar(30), DBC_TELNO_5_UNIFIED varchar(30), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (EMPLOYEE_ID));
create table EMPLOYEE_AUTOCOUNT (AUTOCOUNT_ID int8 not null, DBC_AUTOCOUNT_1 int8, DBC_AUTOCOUNT_2 int8, DBC_AUTOCOUNT_3 int8, DBC_AUTOCOUNT_4 int8, DBC_AUTOCOUNT_5 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (AUTOCOUNT_ID));
create table EMPLOYEE_CHECKBOX (EMPLOYEE_ID int8 not null, ITEM_ID int8 not null, DATA_1 int8, DATA_2 int8, DATA_3 int8, DATA_4 int8, DATA_5 int8, DATA_6 int8, DATA_7 int8, DATA_8 int8, DATA_9 int8, DATA_10 int8, DATA_11 int8, DATA_12 int8, DATA_13 int8, DATA_14 int8, DATA_15 int8, DATA_16 int8, DATA_17 int8, DATA_18 int8, DATA_19 int8, DATA_20 int8, DATA_21 int8, DATA_22 int8, DATA_23 int8, DATA_24 int8, DATA_25 int8, DATA_26 int8, DATA_27 int8, DATA_28 int8, DATA_29 int8, DATA_30 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (EMPLOYEE_ID, ITEM_ID));
create table EMPLOYEE_CHECKBOX_HIST (EMPLOYEE_HIST_ID int8 not null, EMPLOYEE_ID int8 not null, ITEM_ID int8 not null, DATA_1 int8, DATA_2 int8, DATA_3 int8, DATA_4 int8, DATA_5 int8, DATA_6 int8, DATA_7 int8, DATA_8 int8, DATA_9 int8, DATA_10 int8, DATA_11 int8, DATA_12 int8, DATA_13 int8, DATA_14 int8, DATA_15 int8, DATA_16 int8, DATA_17 int8, DATA_18 int8, DATA_19 int8, DATA_20 int8, DATA_21 int8, DATA_22 int8, DATA_23 int8, DATA_24 int8, DATA_25 int8, DATA_26 int8, DATA_27 int8, DATA_28 int8, DATA_29 int8, DATA_30 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (EMPLOYEE_HIST_ID, EMPLOYEE_ID, ITEM_ID));
create table EMPLOYEE_GROUP (EMPLOYEE_GROUP_ID int8 not null, EMPLOYEE_GROUP_NUMBER varchar(100), EMPLOYEE_GROUP_NAME_ID int8 not null, DESCRIPTION varchar(4000), EMPLOYEE_ID int8, GROUP_TYPE int8, EMPLOYEE_GROUP_ORDER int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (EMPLOYEE_GROUP_ID));
create table EMPLOYEE_HIST (EMPLOYEE_HIST_ID int8 not null, EMPLOYEE_ID int8, EMPLOYEE_NUMBER varchar(100), EMPLOYEE_NAME varchar(100) not null, EMPLOYEE_NAME_ID varchar(100), EMPLOYEE_KANA varchar(100), ZIP_CODE varchar(20), ZIP_CODE_UNIFIED varchar(20), ADDRESS varchar(200), ADDRESS_2 varchar(200), TEL_NO varchar(50), TEL_NO_UNIFIED varchar(50), MOB_TEL_NO varchar(50), MOB_TEL_NO_UNIFIED varchar(50), TEL_NO_OTHER varchar(50), TEL_NO_OTHER_UNIFIED varchar(50), EMAIL varchar(100), MOB_EMAIL varchar(100), EMAIL_OTHER varchar(100), PASSWORD varchar(50), COUNTRY_ID int8, LANGUAGE_ID int8, DATETIME_FORMAT int8, TIMEZONE varchar(100), CURRENCY_ID int8, PHOTO_FILE_ID int8, DEPART_ID int8, POST_ID int8, CO_DEPART_ID_1 int8, CO_POST_ID_1 int8, CO_DEPART_ID_2 int8, CO_POST_ID_2 int8, CO_DEPART_ID_3 int8, CO_POST_ID_3 int8, CO_DEPART_ID_4 int8, CO_POST_ID_4 int8, CO_DEPART_ID_5 int8, CO_POST_ID_5 int8, EMPLOYEE_ORDER int8, PWD_LAST_UPDATE timestamp, PWD_TERM_CAUTION_FLG int8, FIRST_LOGIN_FLG int8, LOCK_DATE timestamp, LOGIN_ERROR_NUM int8, MOBILE_ID varchar(200), MOBILE_ID_AUTH_FLG int8, AUTO_LOGIN_PASSWORD int8, AUTO_LOGIN_URL_SEND_DATE timestamp, AUTO_LOGIN_URL_CAUTION_FLG int8, DBC_VARCHAR_1 varchar(1000), DBC_VARCHAR_2 varchar(1000), DBC_VARCHAR_3 varchar(1000), DBC_VARCHAR_4 varchar(1000), DBC_VARCHAR_5 varchar(1000), DBC_TEXTAREA_1 varchar(4000), DBC_TEXTAREA_2 varchar(4000), DBC_TEXTAREA_3 varchar(4000), DBC_TEXTAREA_4 varchar(4000), DBC_TEXTAREA_5 varchar(4000), DBC_INTEGER_1 int8, DBC_INTEGER_2 int8, DBC_INTEGER_3 int8, DBC_INTEGER_4 int8, DBC_INTEGER_5 int8, DBC_SELECT_1 int8, DBC_SELECT_2 int8, DBC_SELECT_3 int8, DBC_SELECT_4 int8, DBC_SELECT_5 int8, DBC_REAL_1 varchar(255), DBC_REAL_2 varchar(255), DBC_REAL_3 varchar(255), DBC_REAL_4 varchar(255), DBC_REAL_5 varchar(255), DBC_DATE_1 timestamp, DBC_DATE_2 timestamp, DBC_DATE_3 timestamp, DBC_DATE_4 timestamp, DBC_DATE_5 timestamp, DBC_FILE_1 int8, DBC_FILE_2 int8, DBC_FILE_3 int8, DBC_FILE_4 int8, DBC_FILE_5 int8, DBC_RELSEL_1_1 int8, DBC_RELSEL_1_2 int8, DBC_RELSEL_2_1 int8, DBC_RELSEL_2_2 int8, DBC_RELSEL_3_1 int8, DBC_RELSEL_3_2 int8, DBC_RELSEL_4_1 int8, DBC_RELSEL_4_2 int8, DBC_RELSEL_5_1 int8, DBC_RELSEL_5_2 int8, DBC_YEARMONTH_1 varchar(6), DBC_YEARMONTH_2 varchar(6), DBC_YEARMONTH_3 varchar(6), DBC_YEARMONTH_4 varchar(6), DBC_YEARMONTH_5 varchar(6), DBC_AUTOCOUNT_1 varchar(20), DBC_AUTOCOUNT_2 varchar(20), DBC_AUTOCOUNT_3 varchar(20), DBC_AUTOCOUNT_4 varchar(20), DBC_AUTOCOUNT_5 varchar(20), DBC_MASTER_1 int8, DBC_MASTER_2 int8, DBC_MASTER_3 int8, DBC_MASTER_4 int8, DBC_MASTER_5 int8, DBC_TELNO_1 varchar(30), DBC_TELNO_1_UNIFIED varchar(30), DBC_TELNO_2 varchar(30), DBC_TELNO_2_UNIFIED varchar(30), DBC_TELNO_3 varchar(30), DBC_TELNO_3_UNIFIED varchar(30), DBC_TELNO_4 varchar(30), DBC_TELNO_4_UNIFIED varchar(30), DBC_TELNO_5 varchar(30), DBC_TELNO_5_UNIFIED varchar(30), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (EMPLOYEE_HIST_ID));
create table EMPLOYEE_LICENSE (EMPLOYEE_ID int8 not null, LICENSE_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (EMPLOYEE_ID, LICENSE_ID));
create table EMPLOYEE_LICENSE_HIST (EMPLOYEE_LICENSE_HIST_ID int8 not null, EMPLOYEE_ID int8, LICENSE_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (EMPLOYEE_LICENSE_HIST_ID));
create table EMPLOYEE_LICENSE_TEMP (EMPLOYEE_ID int8 not null, LICENSE_ID int8 not null, PLAN_UPDATE_TIME timestamp, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (EMPLOYEE_ID, LICENSE_ID));
create table EMPLOYEE_ROLE (EMPLOYEE_ID int8 not null, ROLE_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (EMPLOYEE_ID, ROLE_ID));
create table EMP_GROUP_MEMBER (EMPLOYEE_GROUP_ID int8 not null, EMPLOYEE_ID int8 not null, EMP_GROUP_MEMBER_ORDER int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (EMPLOYEE_GROUP_ID, EMPLOYEE_ID));
create table ENTITY_BELONG (BELONG_ID int8 not null, CLASS_NAME varchar(200), VIEW_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (BELONG_ID));
create table FAVORITE_SEARCH_COND (FAVORITE_SEARCH_COND_ID int8 not null, SEARCH_CONDITION_NAME varchar(100), EMPLOYEE_ID int8 not null, SEARCH_CONDITION_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (FAVORITE_SEARCH_COND_ID));
create table FILE_PERM (FILE_PERM_ID int8 not null, FILE_ID int8, PERM_TYPE int8, EMPLOYEE_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (FILE_PERM_ID));
create table FILE_TYPE (FILE_TYPE_ID int8 not null, FILE_TYPE_NAME varchar(500) not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (FILE_TYPE_ID));
create table FILE_UPLOAD_EMPLOYEE (FOLDER_ID int8 not null, EMPLOYEE_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (FOLDER_ID, EMPLOYEE_ID));
create table FILE_UPLOAD_HISTORY (FILE_ID int8 not null, FILE_NAME varchar(100), FILE_DECRIPTION varchar(255), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (FILE_ID));
create table HOLIDAY (ID int8 not null, LOCAL varchar(3), DESCRIPTION varchar(255), HOLIDAY_DATE timestamp, DIVISION_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ID));
create table INDIVIDUAL_VIEW (ID int8 not null, EMPLOYEE_ID int8 not null, CASE_ID int8, VIEW_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ID));
create table JOB_INSTANCE (ID varchar(255) not null, RUN_NAME varchar(80) not null, JOB_NAME varchar(100), JOB_CLASS varchar(100), TRIGGER_NAME varchar(100), DESCRIPTION varchar(100), EXECUTION_PC_ADDRESS varchar(100), JOB_GROUP varchar(100), REPETITION_FLG int8, TRIGGER_GROUP varchar(100), NEXT_FIRE_TIME timestamp, PREVIOUS_FIRE_TIME timestamp, STATE int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ID));
create table JOB_RESULT (ID int8 not null, RUN_NAME varchar(80) not null, RESULT char(1), START_TIME timestamp, COMPLETE_TIME timestamp, EXCEPTION_MESSAGE varchar(1000), RESULT_DESCRIPTION varchar(1000), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ID));
create table LANGUAGE (LANGUAGE_ID int8 not null, LANGUAGE_NUMBER varchar(100), LANGUAGE_NAME varchar(100), CAN_USE int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (LANGUAGE_ID));
create table LICENSE (LICENSE_ID int8 not null, LICENSE_MASTER_KIND_ID int8, LICENSE_KIND_ID int8, VERSION varchar(100), APPLICATION_OPTION varchar(100), PUBLISHER_NAME varchar(100), HOLDER_NAME varchar(100), BASE_LICENSE_ID int8, LICENSE_NUM int8, SITE_LICENSE_FLG int8, AGREEMENT text, LICENSE_KEY varchar(100), DELETE_FLG int8, EXPIRE_DATE timestamp, PUBLISH_ID varchar(100), PUBLISH_DATE timestamp, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (LICENSE_ID));
create table LICENSE_FUNCTION (LICENSE_MASTER_KIND_ID int8 not null, LICENSE_KIND_ID int8 not null, BPM_FUNCTION_ID int8 not null, BPM_FUNCTION_ORDER int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (LICENSE_MASTER_KIND_ID, LICENSE_KIND_ID, BPM_FUNCTION_ID));
create table LICENSE_KIND (LICENSE_KIND_ID int8 not null, LICENSE_KIND_NAME varchar(100), LICENSE_MASTER_KIND_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (LICENSE_KIND_ID));
create table LICENSE_MAINTENANCE_HIST (LICENSE_MAINTENANSE_HIST_ID int8 not null, UPDATE_TYPE varchar(1), LICENSE_ID int8, LICENSE_NUM int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (LICENSE_MAINTENANSE_HIST_ID));
create table LICENSE_MASTER_KIND (LICENSE_MASTER_KIND_ID int8 not null, LICENSE_MASTER_KIND_NAME varchar(100), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (LICENSE_MASTER_KIND_ID));
create table LIST_PORTAL_VIEW (LIST_PORTAL_ID int8 not null, LIST_ID int8 not null, LIST_TYPE_ID int8 not null, VIEW_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (LIST_PORTAL_ID));
create table LOCALE_MESSAGE (LOCALE varchar(255) not null, MSG_ID int8 not null, VALUE varchar(4000), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (LOCALE, MSG_ID));
create table MANAGER (MANAGER_SETTING_ID int8 not null, DEPART_ID int8, EMPLOYEE_ID int8, MANAGER_EMPLOYEE_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (MANAGER_SETTING_ID));
create table MENU_BAR_SETTING (MENU_BAR_SETTING_ID int8 not null, EMPLOYEE_ID int8, BPM_MENU_ID int8, DISP_FLG int8, MENU_ORDER int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (MENU_BAR_SETTING_ID));
create table MESSAGE (MSG_ID int8 not null, MSG_KEY varchar(100) unique, MSG_DECRIPTION varchar(4000), MSG_DEFAULT_VALUE varchar(4000), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (MSG_ID));
create table MESSAGE_APPFILE (MESSAGE_APPFILE_ID int8 not null, SEND_MESSAGE_ID int8, APPFILE_NAME varchar(500), FILE_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (MESSAGE_APPFILE_ID));
create table MESSAGE_FOLDER (MESSAGE_FOLDER_ID int8 not null, MESSAGE_FOLDER_NAME varchar(50), FOLDER_TYPE int8, EMPLOYEE_ID int8, FOLDER_ORDER int8, PARENT_FOLDER_ID int8, FOLDER_DEPTH int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (MESSAGE_FOLDER_ID));
create table MESSAGE_SENDTO (MESSAGE_SENDTO_ID int8 not null, SEND_MESSAGE_ID int8, SEND_TYPE int8, RECEIVE_EMPLOYEE_ID int8, RECEIVE_TYPE int8, RECEIVE_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (MESSAGE_SENDTO_ID));
create table MESSAGE_TEMPLATE (MESSAGE_TEMPLATE_ID int8 not null, TEMPLATE_TYPE int8, TEMPLATE_NAME varchar(100), EMPLOYEE_ID int8, TEMPLATE_DETAIL varchar(4000), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (MESSAGE_TEMPLATE_ID));
create table MULTI_FORM_VIEW (VIEW_ID int8 not null, BELONG int8 not null, USE_TYPE int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (VIEW_ID));
create table MULTI_VIEW_ITEM (ID int8 not null, VIEW_ID int8, ITEM_ID int8 not null, DISP_ORDER int8, SORT_ORDER int8, SORT_TYPE int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ID));
create table NAVIGATION_BAR_SETTING (NAVIGATION_BAR_ID int8 not null, EMPLOYEE_ID int8, CONTENT_ID int8, DISP_FLG int8, DISP_ORDER int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (NAVIGATION_BAR_ID));
create table OFFICE (OFFICE_ID int8 not null, OFFICE_NUMBER varchar(100), CORPORATOIN_ID int8, OFFICE_NAME varchar(200), OFFICE_KANA varchar(200), ZIP_CODE varchar(20), ZIP_CODE_UNIFIED varchar(20), ADDRESS varchar(200), ADDRESS_2 varchar(200), CHARGE_PERSON_ID int8, FAX_NO varchar(30), FAX_NO_UNIFIED varchar(30), TEL_NO varchar(30), TEL_NO_UNIFIED varchar(30), DESCRIPTION varchar(4000), MAIN_OFFICE_FLG int8, DELETE_FLG int8, DBC_VARCHAR_1 varchar(1000), DBC_VARCHAR_2 varchar(1000), DBC_VARCHAR_3 varchar(1000), DBC_VARCHAR_4 varchar(1000), DBC_VARCHAR_5 varchar(1000), DBC_TEXTAREA_1 varchar(4000), DBC_TEXTAREA_2 varchar(4000), DBC_TEXTAREA_3 varchar(4000), DBC_TEXTAREA_4 varchar(4000), DBC_TEXTAREA_5 varchar(4000), DBC_INTEGER_1 int8, DBC_INTEGER_2 int8, DBC_INTEGER_3 int8, DBC_INTEGER_4 int8, DBC_INTEGER_5 int8, DBC_SELECT_1 int8, DBC_SELECT_2 int8, DBC_SELECT_3 int8, DBC_SELECT_4 int8, DBC_SELECT_5 int8, DBC_REAL_1 varchar(255), DBC_REAL_2 varchar(255), DBC_REAL_3 varchar(255), DBC_REAL_4 varchar(255), DBC_REAL_5 varchar(255), DBC_DATE_1 timestamp, DBC_DATE_2 timestamp, DBC_DATE_3 timestamp, DBC_DATE_4 timestamp, DBC_DATE_5 timestamp, DBC_FILE_1 int8, DBC_FILE_2 int8, DBC_FILE_3 int8, DBC_FILE_4 int8, DBC_FILE_5 int8, DBC_RELSEL_1_1 int8, DBC_RELSEL_1_2 int8, DBC_RELSEL_2_1 int8, DBC_RELSEL_2_2 int8, DBC_RELSEL_3_1 int8, DBC_RELSEL_3_2 int8, DBC_RELSEL_4_1 int8, DBC_RELSEL_4_2 int8, DBC_RELSEL_5_1 int8, DBC_RELSEL_5_2 int8, DBC_YEARMONTH_1 varchar(6), DBC_YEARMONTH_2 varchar(6), DBC_YEARMONTH_3 varchar(6), DBC_YEARMONTH_4 varchar(6), DBC_YEARMONTH_5 varchar(6), DBC_AUTOCOUNT_1 varchar(20), DBC_AUTOCOUNT_2 varchar(20), DBC_AUTOCOUNT_3 varchar(20), DBC_AUTOCOUNT_4 varchar(20), DBC_AUTOCOUNT_5 varchar(20), DBC_MASTER_1 int8, DBC_MASTER_2 int8, DBC_MASTER_3 int8, DBC_MASTER_4 int8, DBC_MASTER_5 int8, DBC_TELNO_1 varchar(30), DBC_TELNO_1_UNIFIED varchar(30), DBC_TELNO_2 varchar(30), DBC_TELNO_2_UNIFIED varchar(30), DBC_TELNO_3 varchar(30), DBC_TELNO_3_UNIFIED varchar(30), DBC_TELNO_4 varchar(30), DBC_TELNO_4_UNIFIED varchar(30), DBC_TELNO_5 varchar(30), DBC_TELNO_5_UNIFIED varchar(30), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (OFFICE_ID));
create table OFFICE_AUTOCOUNT (AUTOCOUNT_ID int8 not null, OFFICE_NUMBER int8, DBC_AUTOCOUNT_1 int8, DBC_AUTOCOUNT_2 int8, DBC_AUTOCOUNT_3 int8, DBC_AUTOCOUNT_4 int8, DBC_AUTOCOUNT_5 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (AUTOCOUNT_ID));
create table OFFICE_CHECKBOX (OFFICE_ID int8 not null, ITEM_ID int8 not null, DATA_1 int8, DATA_2 int8, DATA_3 int8, DATA_4 int8, DATA_5 int8, DATA_6 int8, DATA_7 int8, DATA_8 int8, DATA_9 int8, DATA_10 int8, DATA_11 int8, DATA_12 int8, DATA_13 int8, DATA_14 int8, DATA_15 int8, DATA_16 int8, DATA_17 int8, DATA_18 int8, DATA_19 int8, DATA_20 int8, DATA_21 int8, DATA_22 int8, DATA_23 int8, DATA_24 int8, DATA_25 int8, DATA_26 int8, DATA_27 int8, DATA_28 int8, DATA_29 int8, DATA_30 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (OFFICE_ID, ITEM_ID));
create table POLICY_LEVEL (POLICY_LEVEL int8 not null, DESCRIPTION varchar(4000), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (POLICY_LEVEL));
create table POST (POST_ID int8 not null, POST_NUMBER varchar(100), POST_NAME varchar(100) not null, POST_ORDER int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (POST_ID));
create table PRODUCT (PRODUCT_ID int8 not null, PRODUCT_NUMBER varchar(100), PRODUCT_NAME varchar(200) not null, PRODUCT_NAME_ID varchar(100), ETM_UNIT_PRICE varchar(255), ETM_UNIT_PRICE_CUR_ID int8, ETM_UNIT_PRICE_SYS varchar(255), DESCRIPTION varchar(4000), PRODUCT_FLG int8, CATEGORY_BELONG int8, PHOTO int8, DEPTH int8, PRODUCT_ID_1 int8, PRODUCT_ID_2 int8, PRODUCT_ID_3 int8, PRODUCT_ID_4 int8, PRODUCT_ID_5 int8, PRODUCT_ID_6 int8, PRODUCT_ID_7 int8, PRODUCT_ID_8 int8, PRODUCT_ID_9 int8, PRODUCT_ID_10 int8, PRODUCT_ATTRIBUTE_1 int8, PRODUCT_ATTRIBUTE_2 int8, DBC_VARCHAR_1 varchar(1000), DBC_VARCHAR_2 varchar(1000), DBC_VARCHAR_3 varchar(1000), DBC_VARCHAR_4 varchar(1000), DBC_VARCHAR_5 varchar(1000), DBC_TEXTAREA_1 varchar(4000), DBC_TEXTAREA_2 varchar(4000), DBC_TEXTAREA_3 varchar(4000), DBC_TEXTAREA_4 varchar(4000), DBC_TEXTAREA_5 varchar(4000), DBC_INTEGER_1 int8, DBC_INTEGER_2 int8, DBC_INTEGER_3 int8, DBC_INTEGER_4 int8, DBC_INTEGER_5 int8, DBC_SELECT_1 int8, DBC_SELECT_2 int8, DBC_SELECT_3 int8, DBC_SELECT_4 int8, DBC_SELECT_5 int8, DBC_REAL_1 varchar(255), DBC_REAL_2 varchar(255), DBC_REAL_3 varchar(255), DBC_REAL_4 varchar(255), DBC_REAL_5 varchar(255), DBC_DATE_1 timestamp, DBC_DATE_2 timestamp, DBC_DATE_3 timestamp, DBC_DATE_4 timestamp, DBC_DATE_5 timestamp, DBC_FILE_1 int8, DBC_FILE_2 int8, DBC_FILE_3 int8, DBC_FILE_4 int8, DBC_FILE_5 int8, DBC_RELSEL_1_1 int8, DBC_RELSEL_1_2 int8, DBC_RELSEL_2_1 int8, DBC_RELSEL_2_2 int8, DBC_RELSEL_3_1 int8, DBC_RELSEL_3_2 int8, DBC_RELSEL_4_1 int8, DBC_RELSEL_4_2 int8, DBC_RELSEL_5_1 int8, DBC_RELSEL_5_2 int8, DBC_YEARMONTH_1 varchar(6), DBC_YEARMONTH_2 varchar(6), DBC_YEARMONTH_3 varchar(6), DBC_YEARMONTH_4 varchar(6), DBC_YEARMONTH_5 varchar(6), DBC_AUTOCOUNT_1 varchar(20), DBC_AUTOCOUNT_2 varchar(20), DBC_AUTOCOUNT_3 varchar(20), DBC_AUTOCOUNT_4 varchar(20), DBC_AUTOCOUNT_5 varchar(20), DBC_MASTER_1 int8, DBC_MASTER_2 int8, DBC_MASTER_3 int8, DBC_MASTER_4 int8, DBC_MASTER_5 int8, DBC_TELNO_1 varchar(30), DBC_TELNO_1_UNIFIED varchar(30), DBC_TELNO_2 varchar(30), DBC_TELNO_2_UNIFIED varchar(30), DBC_TELNO_3 varchar(30), DBC_TELNO_3_UNIFIED varchar(30), DBC_TELNO_4 varchar(30), DBC_TELNO_4_UNIFIED varchar(30), DBC_TELNO_5 varchar(30), DBC_TELNO_5_UNIFIED varchar(30), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (PRODUCT_ID));
create table PRODUCT_AUTOCOUNT (AUTOCOUNT_ID int8 not null, PRODUCT_NUMBER int8, DBC_AUTOCOUNT_1 int8, DBC_AUTOCOUNT_2 int8, DBC_AUTOCOUNT_3 int8, DBC_AUTOCOUNT_4 int8, DBC_AUTOCOUNT_5 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (AUTOCOUNT_ID));
create table PRODUCT_CHECKBOX (PRODUCT_ID int8 not null, ITEM_ID int8 not null, DATA_1 int8, DATA_2 int8, DATA_3 int8, DATA_4 int8, DATA_5 int8, DATA_6 int8, DATA_7 int8, DATA_8 int8, DATA_9 int8, DATA_10 int8, DATA_11 int8, DATA_12 int8, DATA_13 int8, DATA_14 int8, DATA_15 int8, DATA_16 int8, DATA_17 int8, DATA_18 int8, DATA_19 int8, DATA_20 int8, DATA_21 int8, DATA_22 int8, DATA_23 int8, DATA_24 int8, DATA_25 int8, DATA_26 int8, DATA_27 int8, DATA_28 int8, DATA_29 int8, DATA_30 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (PRODUCT_ID, ITEM_ID));
create table PRODUCT_CHECKBOX_HIST (PRODUCT_HIST_ID int8 not null, PRODUCT_ID int8 not null, ITEM_ID int8 not null, DATA_1 int8, DATA_2 int8, DATA_3 int8, DATA_4 int8, DATA_5 int8, DATA_6 int8, DATA_7 int8, DATA_8 int8, DATA_9 int8, DATA_10 int8, DATA_11 int8, DATA_12 int8, DATA_13 int8, DATA_14 int8, DATA_15 int8, DATA_16 int8, DATA_17 int8, DATA_18 int8, DATA_19 int8, DATA_20 int8, DATA_21 int8, DATA_22 int8, DATA_23 int8, DATA_24 int8, DATA_25 int8, DATA_26 int8, DATA_27 int8, DATA_28 int8, DATA_29 int8, DATA_30 int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (PRODUCT_HIST_ID, PRODUCT_ID, ITEM_ID));
create table PRODUCT_HIST (PRODUCT_HIST_ID int8 not null, PRODUCT_ID int8, PRODUCT_NUMBER varchar(100), PRODUCT_NAME varchar(200) not null, PRODUCT_NAME_ID varchar(100), ETM_UNIT_PRICE varchar(255), ETM_UNIT_PRICE_CUR_ID int8, ETM_UNIT_PRICE_SYS varchar(255), DESCRIPTION varchar(4000), PRODUCT_FLG int8, CATEGORY_BELONG int8, PHOTO int8, DEPTH int8, PRODUCT_ID_1 int8, PRODUCT_ID_2 int8, PRODUCT_ID_3 int8, PRODUCT_ID_4 int8, PRODUCT_ID_5 int8, PRODUCT_ID_6 int8, PRODUCT_ID_7 int8, PRODUCT_ID_8 int8, PRODUCT_ID_9 int8, PRODUCT_ID_10 int8, PRODUCT_ATTRIBUTE_1 int8, PRODUCT_ATTRIBUTE_2 int8, DBC_VARCHAR_1 varchar(1000), DBC_VARCHAR_2 varchar(1000), DBC_VARCHAR_3 varchar(1000), DBC_VARCHAR_4 varchar(1000), DBC_VARCHAR_5 varchar(1000), DBC_TEXTAREA_1 varchar(4000), DBC_TEXTAREA_2 varchar(4000), DBC_TEXTAREA_3 varchar(4000), DBC_TEXTAREA_4 varchar(4000), DBC_TEXTAREA_5 varchar(4000), DBC_INTEGER_1 int8, DBC_INTEGER_2 int8, DBC_INTEGER_3 int8, DBC_INTEGER_4 int8, DBC_INTEGER_5 int8, DBC_SELECT_1 int8, DBC_SELECT_2 int8, DBC_SELECT_3 int8, DBC_SELECT_4 int8, DBC_SELECT_5 int8, DBC_REAL_1 varchar(255), DBC_REAL_2 varchar(255), DBC_REAL_3 varchar(255), DBC_REAL_4 varchar(255), DBC_REAL_5 varchar(255), DBC_DATE_1 timestamp, DBC_DATE_2 timestamp, DBC_DATE_3 timestamp, DBC_DATE_4 timestamp, DBC_DATE_5 timestamp, DBC_FILE_1 int8, DBC_FILE_2 int8, DBC_FILE_3 int8, DBC_FILE_4 int8, DBC_FILE_5 int8, DBC_RELSEL_1_1 int8, DBC_RELSEL_1_2 int8, DBC_RELSEL_2_1 int8, DBC_RELSEL_2_2 int8, DBC_RELSEL_3_1 int8, DBC_RELSEL_3_2 int8, DBC_RELSEL_4_1 int8, DBC_RELSEL_4_2 int8, DBC_RELSEL_5_1 int8, DBC_RELSEL_5_2 int8, DBC_YEARMONTH_1 varchar(6), DBC_YEARMONTH_2 varchar(6), DBC_YEARMONTH_3 varchar(6), DBC_YEARMONTH_4 varchar(6), DBC_YEARMONTH_5 varchar(6), DBC_AUTOCOUNT_1 varchar(20), DBC_AUTOCOUNT_2 varchar(20), DBC_AUTOCOUNT_3 varchar(20), DBC_AUTOCOUNT_4 varchar(20), DBC_AUTOCOUNT_5 varchar(20), DBC_MASTER_1 int8, DBC_MASTER_2 int8, DBC_MASTER_3 int8, DBC_MASTER_4 int8, DBC_MASTER_5 int8, DBC_TELNO_1 varchar(30), DBC_TELNO_1_UNIFIED varchar(30), DBC_TELNO_2 varchar(30), DBC_TELNO_2_UNIFIED varchar(30), DBC_TELNO_3 varchar(30), DBC_TELNO_3_UNIFIED varchar(30), DBC_TELNO_4 varchar(30), DBC_TELNO_4_UNIFIED varchar(30), DBC_TELNO_5 varchar(30), DBC_TELNO_5_UNIFIED varchar(30), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (PRODUCT_HIST_ID));
create table PROGRESS (PROGRESS_ID int8 not null, PROGRESS_CATEGORY_ID int8 not null, PROGRESS_NAME_ID varchar(100), PROGRESS_ORDER int8, ICON_LETTER varchar(2), ICON_FILE varchar(500), ENTRY_FLG int8, TAKING_FLG int8, SALES_FLG int8, CAN_DELETE int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (PROGRESS_ID));
create table PROGRESS_ACTION_RELATION (PAR_ID int8 not null, TUI_ID int8 not null, PROGRESS_ID int8 not null, ACTION_ID int8 not null, SHOW_FLG int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (PAR_ID));
create table PROGRESS_CATEGORY (PROGRESS_CATEGORY_ID int8 not null, PROGRESS_CATEGORY_NAME_ID varchar(100), PROGRESS_CATEGORY_ORDER int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (PROGRESS_CATEGORY_ID));
create table PROGRESS_USABLE_ITEM (PUI_ID int8 not null, TUI_ID int8 not null, ITEM_ID int8 not null, PROGRESS_ID int8 not null, SHOW_FLG int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (PUI_ID));
create table RECEIVE_MESSAGE (RECEIVE_MESSAGE_ID int8 not null, SEND_MESSAGE_ID int8, RECEIVE_EMPLOYEE_ID int8, READ_FLG int8, MESSAGE_FOLDER_ID int8, RECEIVE_DATE timestamp, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (RECEIVE_MESSAGE_ID));
create table ROLE (ROLE_ID int8 not null, ROLE_NAME varchar(100), DESCRIPTION varchar(4000), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ROLE_ID));
create table ROLE_FUNCTION (ROLE_ID int8 not null, BPM_FUNCTION_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (ROLE_ID, BPM_FUNCTION_ID));
create table RULE_ACTION (RULE_ACTION_ID int8 not null, RULE_ID int8, RULE_ACTION_INFO_ID int8, PARAM_INDEX int8, PARAM_VALUE varchar(255), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (RULE_ACTION_ID));
create table RULE_ACTION_INFO (RULE_ACTION_INFO_ID int8 not null, RULE_ACTION_NAME_ID int8, DESCRIPTION_ID int8, DATA_TYPE int8, TYPE int8, EXEC_CLASS_NAME varchar(255), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (RULE_ACTION_INFO_ID));
create table RULE_CONDITION (RULE_ID int8 not null, SEARCH_CONDITION_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (RULE_ID));
create table SEARCH_CONDITION (SEARCH_CONDITION_ID int8 not null, DISP_SETTING_KIND_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (SEARCH_CONDITION_ID));
create table SEARCH_CONDITION_DIVISION (CONDITION_KIND_ID int8 not null, CONDITION_DIVISION_CODE int8 not null, CONDITION_DIVISION_NAME varchar(200), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (CONDITION_KIND_ID));
create table SEARCH_CONTENT (SEARCH_CONTENT_ID int8 not null, SEARCH_CONDITION_ID int8 not null, ITEM_ID int8 not null, INPUT_INDEX int8, INPUT_VALUE varchar(4000), CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (SEARCH_CONTENT_ID));
create table SEARCH_METHOD_DIVISION (SEARCH_METHOD_DIVISION_ID int8 not null, CONDITION_KIND_ID int8 not null, DESCRIPTION int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (SEARCH_METHOD_DIVISION_ID));
create table SEND_MESSAGE (SEND_MESSAGE_ID int8 not null, SEND_EMPLOYEE_ID int8, MESSAGE_TITLE varchar(100), MESSAGE_DETAIL varchar(4000), TRANS_MESSAGE_ID int8, REPLY_MESSAGE_ID int8, SEND_TIME timestamp, MESSAGE_FOLDER_ID int8, DELETE_FLG int8, APPEND_FILE_FLG int8, DATA_TYPE int8, DATA_ID int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (SEND_MESSAGE_ID));
create table SHEET_POLICY_SETTING (BELONG_ID int8 not null, POLICY_LEVEL int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (BELONG_ID));
create table TYPE_USABLE_INFO (TUI_ID int8 not null, BELONG_ID int8 not null, TYPE_ID int8 not null, PRODUCT_FLG int8, ACTION_TIME_FLG int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (TUI_ID));
create table USABLE_ACTION (UA_ID int8 not null, TUI_ID int8 not null, ACTION_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (UA_ID));
create table USABLE_INFO (BELONG_ID int8 not null, TYPE_KIND_ID int8 not null, PROGRESS_ITEM_FLG int8, ACTION_ITEM_FLG int8, PROGRESS_FLG int8, ACTION_FLG int8, PROGRESS_ACTION_FLG int8, PRODUCT_FLG int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (BELONG_ID));
create table USABLE_ITEM (USABLE_ITEM_ID int8 not null, TUI_ID int8 not null, ITEM_ID int8 not null, SHOW_FLG int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (USABLE_ITEM_ID));
create table USABLE_PRODUCT_PROGRESS (UPP_ID int8 not null, TUI_ID int8 not null, PRODUCT_PROGRESS_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (UPP_ID));
create table USABLE_PROGRESS (UP_ID int8 not null, TUI_ID int8 not null, PROGRESS_ID int8 not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (UP_ID));
create table VIEW_CASE (CASE_ID int8 not null, CASE_NAME_ID varchar(100) not null, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (CASE_ID));
create table WORKDAY (WORKDAY_SETTING_ID int8 not null, COUNTRY_ID varchar(3), SUN_FLG int8, MON_FLG int8, TUE_FLG int8, WED_FLG int8, THU_FLG int8, FRI_FLG int8, SAT_FLG int8, CREATE_BY int8, CREATE_DATE timestamp, UPDATE_BY int8, UPDATE_DATE timestamp, primary key (WORKDAY_SETTING_ID));
alter table ANKEN_CHECKBOX add constraint FK65F2821B8204EADD foreign key (ITEM_ID) references BPM_ITEM;
alter table ANKEN_CHECKBOX add constraint FK65F2821B6ECA441C foreign key (ANKEN_ID) references ANKEN;
alter table ANKEN_COMPETITOR add constraint FKA6B1ADA46ECA441C foreign key (ANKEN_ID) references ANKEN;
alter table ANKEN_COMPETITOR add constraint FKA6B1ADA4804D3C0A foreign key (COMPETITOR_ID) references COMPETITOR;
alter table ANKEN_EMPLOYEE add constraint FK517A4D0639A0DCCA foreign key (EMPLOYEE_ID) references EMPLOYEE;
alter table ANKEN_EMPLOYEE add constraint FK517A4D066ECA441C foreign key (ANKEN_ID) references ANKEN;
alter table ANKEN_EMP_GROUP add constraint FKFDC7A4B0289511ED foreign key (EMPLOYEE_GROUP_ID) references EMPLOYEE_GROUP;
alter table ANKEN_EMP_GROUP add constraint FKFDC7A4B0B1B3430A foreign key (DEPART_ID) references DEPART;
alter table ANKEN_EMP_GROUP add constraint FKFDC7A4B06ECA441C foreign key (ANKEN_ID) references ANKEN;
alter table ANKEN_LIST add constraint FK6EFE0AF617D00444 foreign key (SEARCH_COND_ID) references SEARCH_CONDITION;
alter table ANKEN_LIST add constraint FK6EFE0AF6289511ED foreign key (EMPLOYEE_GROUP_ID) references EMPLOYEE_GROUP;
alter table ANKEN_LIST add constraint FK6EFE0AF639A0DCCA foreign key (EMPLOYEE_ID) references EMPLOYEE;
alter table ANKEN_LIST add constraint FK6EFE0AF6B1B3430A foreign key (DEPART_ID) references DEPART;
alter table ANKEN_LIST_REC add constraint FK5AA00BA76ECA441C foreign key (ANKEN_ID) references ANKEN;
alter table ANKEN_LIST_REC add constraint FK5AA00BA7D5EBEE2B foreign key (ANKEN_LIST_ID) references ANKEN_LIST;
alter table BPM_CHECK_ITEM add constraint FK5530268A8204EADD foreign key (ITEM_ID) references BPM_ITEM;
alter table BPM_MENU add constraint FKD8C403DF1F804683 foreign key (LINK_ID) references BPM_URL;
alter table BPM_RELATED_SELECT_ITEM_1 add constraint FKEDF76AF48204EADD foreign key (ITEM_ID) references BPM_ITEM;
alter table BPM_RELATED_SELECT_ITEM_2 add constraint FKEDF76AF58204EADD foreign key (ITEM_ID) references BPM_ITEM;
alter table BPM_SELECT_ITEM add constraint FKE71EF9F68204EADD foreign key (ITEM_ID) references BPM_ITEM;
alter table BPM_SELECT_RELATION add constraint FK8362475FE9961D44 foreign key (RELATED_SELECT_ITEM_ID_1) references BPM_RELATED_SELECT_ITEM_1;
alter table BPM_SELECT_RELATION add constraint FK8362475FE9961D46 foreign key (RELATED_SELECT_ITEM_ID_2) references BPM_RELATED_SELECT_ITEM_2;
alter table BPM_SUBMENU add constraint FK9A85B8FF1F804683 foreign key (LINK_ID) references BPM_URL;
alter table BPM_SUBMENU add constraint FK9A85B8FF4F580FE6 foreign key (BPM_MENU_ID) references BPM_MENU;
alter table BPM_TYPE add constraint FKD8C77DBA26290812 foreign key (TYPE_KIND_ID) references BPM_TYPE_KIND;
alter table BPM_URL add constraint FK304882CF76082B46 foreign key (BPM_FUNCTION_ID) references BPM_FUNCTION;
alter table COMPETITOR add constraint FKF1846D4CAFDC87AB foreign key (PRODUCT_ID) references PRODUCT;
alter table COMPETITOR add constraint FKF1846D4C1C92C8CE foreign key (CORPORATOIN_ID) references CORPORATION;
alter table CORPORATION_CHECKBOX add constraint FK659AEE3A8204EADD foreign key (ITEM_ID) references BPM_ITEM;
alter table CORPORATION_CHECKBOX add constraint FK659AEE3A12AA421A foreign key (CORPORATION_ID) references CORPORATION;
alter table DOCUMENT_FILE add constraint FK19FC5E2034FDA145 foreign key (FOLDER_ID) references DOCUMENT_FOLDER;
alter table DOCUMENT_FILE add constraint FK19FC5E20156F4F8C foreign key (UPLOAD_EMPLOYEE_ID) references EMPLOYEE;
alter table DOCUMENT_FILE add constraint FK19FC5E2019F9C8A3 foreign key (FILE_TYPE_ID) references FILE_TYPE;
alter table DOCUMENT_FILE_HIST add constraint FK670605E134FDA145 foreign key (FOLDER_ID) references DOCUMENT_FOLDER;
alter table DOCUMENT_FILE_HIST add constraint FK670605E1156F4F8C foreign key (UPLOAD_EMPLOYEE_ID) references EMPLOYEE;
alter table DOCUMENT_FILE_HIST add constraint FK670605E14E867545 foreign key (FILE_ID) references DOCUMENT_FILE;
alter table DOCUMENT_FILE_HIST add constraint FK670605E119F9C8A3 foreign key (FILE_TYPE_ID) references FILE_TYPE;
alter table EMPLOYEE_CHECKBOX add constraint FK89590A3439A0DCCA foreign key (EMPLOYEE_ID) references EMPLOYEE;
alter table EMPLOYEE_CHECKBOX add constraint FK89590A348204EADD foreign key (ITEM_ID) references BPM_ITEM;
alter table EMPLOYEE_GROUP add constraint FKFF7FD8E39A0DCCA foreign key (EMPLOYEE_ID) references EMPLOYEE;
alter table EMPLOYEE_LICENSE add constraint FKEA61FF9039A0DCCA foreign key (EMPLOYEE_ID) references EMPLOYEE;
alter table EMPLOYEE_LICENSE add constraint FKEA61FF90A32A32DA foreign key (LICENSE_ID) references LICENSE;
alter table EMPLOYEE_LICENSE_HIST add constraint FK891BCA7139A0DCCA foreign key (EMPLOYEE_ID) references EMPLOYEE;
alter table EMPLOYEE_LICENSE_HIST add constraint FK891BCA71A32A32DA foreign key (LICENSE_ID) references LICENSE;
alter table EMPLOYEE_LICENSE_TEMP add constraint FK89212F2339A0DCCA foreign key (EMPLOYEE_ID) references EMPLOYEE;
alter table EMPLOYEE_LICENSE_TEMP add constraint FK89212F23A32A32DA foreign key (LICENSE_ID) references LICENSE;
alter table EMPLOYEE_ROLE add constraint FK4299576739A0DCCA foreign key (EMPLOYEE_ID) references EMPLOYEE;
alter table EMPLOYEE_ROLE add constraint FK42995767B3A8FCA foreign key (ROLE_ID) references ROLE;
alter table EMP_GROUP_MEMBER add constraint FKFB4A92B139A0DCCA foreign key (EMPLOYEE_ID) references EMPLOYEE;
alter table EMP_GROUP_MEMBER add constraint FKFB4A92B1289511ED foreign key (EMPLOYEE_GROUP_ID) references EMPLOYEE_GROUP;
alter table FILE_PERM add constraint FK596443D339A0DCCA foreign key (EMPLOYEE_ID) references EMPLOYEE;
alter table FILE_PERM add constraint FK596443D34E867545 foreign key (FILE_ID) references DOCUMENT_FILE;
alter table FILE_UPLOAD_EMPLOYEE add constraint FK90B2826939A0DCCA foreign key (EMPLOYEE_ID) references EMPLOYEE;
alter table FILE_UPLOAD_EMPLOYEE add constraint FK90B2826934FDA145 foreign key (FOLDER_ID) references DOCUMENT_FOLDER;
alter table LICENSE add constraint FK34BC102172850D7E foreign key (LICENSE_MASTER_KIND_ID) references LICENSE_MASTER_KIND;
alter table LICENSE add constraint FK34BC1021782877DD foreign key (LICENSE_KIND_ID) references LICENSE_KIND;
alter table LICENSE_FUNCTION add constraint FKF9A81A3676082B46 foreign key (BPM_FUNCTION_ID) references BPM_FUNCTION;
alter table LICENSE_FUNCTION add constraint FKF9A81A3672850D7E foreign key (LICENSE_MASTER_KIND_ID) references LICENSE_MASTER_KIND;
alter table LICENSE_FUNCTION add constraint FKF9A81A36782877DD foreign key (LICENSE_KIND_ID) references LICENSE_KIND;
alter table LICENSE_KIND add constraint FKEBFBC8F272850D7E foreign key (LICENSE_MASTER_KIND_ID) references LICENSE_MASTER_KIND;
alter table LICENSE_MAINTENANCE_HIST add constraint FK3224E14CA32A32DA foreign key (LICENSE_ID) references LICENSE;
alter table LOCALE_MESSAGE add constraint FKE65C3B027D5BDD0D foreign key (MSG_ID) references MESSAGE;
alter table MANAGER add constraint FK5C949B8D39A0DCCA foreign key (EMPLOYEE_ID) references EMPLOYEE;
alter table MANAGER add constraint FK5C949B8DBCC32F38 foreign key (MANAGER_EMPLOYEE_ID) references EMPLOYEE;
alter table MANAGER add constraint FK5C949B8DB1B3430A foreign key (DEPART_ID) references DEPART;
alter table MENU_BAR_SETTING add constraint FK445F3044F580FE6 foreign key (BPM_MENU_ID) references BPM_MENU;
alter table MULTI_VIEW_ITEM add constraint FKC15EE4C7E368BB5B foreign key (VIEW_ID) references MULTI_FORM_VIEW;
alter table OFFICE_CHECKBOX add constraint FK46235DE6BE6E1EDA foreign key (OFFICE_ID) references OFFICE;
alter table OFFICE_CHECKBOX add constraint FK46235DE68204EADD foreign key (ITEM_ID) references BPM_ITEM;
alter table PRODUCT_CHECKBOX add constraint FK91B25D338204EADD foreign key (ITEM_ID) references BPM_ITEM;
alter table PRODUCT_CHECKBOX add constraint FK91B25D33AFDC87AB foreign key (PRODUCT_ID) references PRODUCT;
alter table PROGRESS add constraint FKF2FAB22D15319905 foreign key (PROGRESS_CATEGORY_ID) references PROGRESS_CATEGORY;
alter table ROLE_FUNCTION add constraint FK57D5418176082B46 foreign key (BPM_FUNCTION_ID) references BPM_FUNCTION;
alter table ROLE_FUNCTION add constraint FK57D54181B3A8FCA foreign key (ROLE_ID) references ROLE;
alter table RULE_ACTION add constraint FKE5D84CD95026A965 foreign key (RULE_ACTION_INFO_ID) references RULE_ACTION_INFO;
alter table RULE_ACTION add constraint FKE5D84CD9E47C873A foreign key (RULE_ID) references BPM_RULE;
alter table SEARCH_CONTENT add constraint FKBC1F98C28204EADD foreign key (ITEM_ID) references BPM_ITEM;
alter table SHEET_POLICY_SETTING add constraint FK9D378B23D5ECC7D9 foreign key (POLICY_LEVEL) references POLICY_LEVEL;
alter table USABLE_INFO add constraint FK40A1D79526290812 foreign key (TYPE_KIND_ID) references BPM_TYPE_KIND;
create sequence 
            SEQ_ACTION_USABLE_ITEM_ID
        ;
create sequence 
            SEQ_ANKEN_EMP_GRP_ID
        ;
create sequence 
            SEQ_ANKEN_HIST_ID
        ;
create sequence 
            SEQ_ANKEN_ID
        ;
create sequence 
            SEQ_ANKEN_LIST_ID
        ;
create sequence 
            SEQ_ANKEN_LIST_REC_ID
        ;
create sequence 
            SEQ_BPM_ITEM_ITEM_ID
        ;
create sequence 
            SEQ_BPM_RLT_SEL_ITM_1_ID
        ;
create sequence 
            SEQ_BPM_RLT_SEL_ITM_2_ID
        ;
create sequence 
            SEQ_BPM_RULE_ID
        ;
create sequence 
            SEQ_BPM_SEL_REL_ID
        ;
create sequence 
            SEQ_BPM_TYPE_ID
        ;
create sequence 
            SEQ_COMPETITOR_ID
        ;
create sequence 
            SEQ_CORPORATION_ID
        ;
create sequence 
            SEQ_DEPART_ID
        ;
create sequence 
            SEQ_DOC_FILE_FOLDER_ID
        ;
create sequence 
            SEQ_DOC_FILE_ID
        ;
create sequence 
            SEQ_EMPLOYEE_GROUP_ID
        ;
create sequence 
            SEQ_EMPLOYEE_HIST_ID
        ;
create sequence 
            SEQ_EMPLOYEE_ID
        ;
create sequence 
            SEQ_EMPLOYEE_LICENSE_HIST_ID
        ;
create sequence 
            SEQ_FAVORITE_SEARCH_COND_ID
        ;
create sequence 
            SEQ_FILE_PERM_ID
        ;
create sequence 
            SEQ_FILE_TYPE_ID
        ;
create sequence 
            SEQ_FILE_UPLOAD_HISTORY_ID
        ;
create sequence 
            SEQ_HOLIDAY_ID
        ;
create sequence 
            SEQ_INDIVIDUAL_VIEW_ID
        ;
create sequence 
            SEQ_JOB_INSTANCE_ID
        ;
create sequence 
            SEQ_JOB_RESULT_ID
        ;
create sequence 
            SEQ_LICENSE_ID
        ;
create sequence 
            SEQ_LICENSE_KIND_ID
        ;
create sequence 
            SEQ_LICENSE_MAIN_HIST_ID
        ;
create sequence 
            SEQ_LICENSE_MASTER_KIND_ID
        ;
create sequence 
            SEQ_LIST_PORTAL_VIEW_ID
        ;
create sequence 
            SEQ_MANAGER_SETTING_ID
        ;
create sequence 
            SEQ_MENU_BAR_SETTING_ID
        ;
create sequence 
            SEQ_MESSAGE_APPFILE_ID
        ;
create sequence 
            SEQ_MESSAGE_FOLDER_ID
        ;
create sequence 
            SEQ_MESSAGE_SENDTO_ID
        ;
create sequence 
            SEQ_MESSAGE_TEMPLATE_ID
        ;
create sequence 
            SEQ_MULTI_FORM_VIEW_ID
        ;
create sequence 
            SEQ_MULTI_VIEW_ITEM_ID
        ;
create sequence 
            SEQ_NAVIGATION_BAR_SETTING_ID
        ;
create sequence 
            SEQ_OFFICE_ID
        ;
create sequence 
            SEQ_POST_ID
        ;
create sequence 
            SEQ_PRODUCT_HIST_ID
        ;
create sequence 
            SEQ_PRODUCT_ID
        ;
create sequence 
            SEQ_PROGRESS_ACT_REL_PAR_ID
        ;
create sequence 
            SEQ_PROGRESS_CATEGORY_ID
        ;
create sequence 
            SEQ_PROGRESS_ID
        ;
create sequence 
            SEQ_PROG_USABLE_ITEM_PUI_ID
        ;
create sequence 
            SEQ_RECEIVE_MESSAGE_ID
        ;
create sequence 
            SEQ_ROLE_ID
        ;
create sequence 
            SEQ_RULE_ACTION_ID
        ;
create sequence 
            SEQ_SEARCH_CONDITION_ID
        ;
create sequence 
            SEQ_SEARCH_CONTENT_ID
        ;
create sequence 
            SEQ_SEND_MESSAGE_ID
        ;
create sequence 
            SEQ_TYPE_USABLE_INFO_ID
        ;
create sequence 
            SEQ_USABLE_INFO_ID
        ;
create sequence 
            SEQ_USABLE_ITEM_ID
        ;
create sequence 
            SEQ_USABLE_PDT_PGS_ID
        ;
create sequence 
            SEQ_USABLE_PROGRESS_ID
        ;
create sequence hibernate_sequence;
