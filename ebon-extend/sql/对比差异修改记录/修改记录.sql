--v2_v_rpt_customer 无改动
CREATE OR REPLACE VIEW v2_v_rpt_customer AS
SELECT c.customer_id id ,
       c.company_name_cn name,
       cg.customer_id cgId,
       cg.company_name_cn cgName,
       c.sap_Name cSapName,
       c.sap_code cSapCode
FROM cm_customer c
JOIN cm_customer cg ON c.isgroup = 0
AND cg.isgroup = 1
AND c.customer_group = cg.customer_id;

--V2_V_RPT_TIMESHEETITEM 无改动
CREATE OR REPLACE VIEW V2_V_RPT_TIMESHEETITEM AS
SELECT userId,
       WORK_DATE,
       projectCode,
       SUM(WORK_HOURS) WORK_HOURS,
       ph2,
       customer
  FROM (
      /* uPMS系统的项目报工部分*/
      SELECT   CUT.USER_ID USERID,
               TRH.timesheet_date WORK_DATE,
               CP.PROJ_SHORT_NAME PROJECTCODE,
               TRH.approved_hours WORK_HOURS,
               CP.PH2,
               CP.CUSTOMER
          FROM TS_RESOURCEHOUR TRH
          INNER JOIN CM_USERTASK CUT ON TRH.USERTASK_ID = CUT.USERTASK_ID AND CUT.TASK_TYPE <> 'OVERHEAD'
          INNER JOIN CM_TASK CT ON TO_CHAR(CT.TASK_ID) = CUT.TASK_ID
          INNER JOIN CM_PROJ CP ON CP.PROJ_OBJECT_ID = CT.PROJ_ID AND CP.ORIG_PROJ_ID IS NULL AND CP.PROJ_NODE_FLAG ='Y'

        UNION ALL
        /* uPMS系统的非项目报工部分 */
        SELECT CUT.USER_ID USERID,
               TRH.timesheet_date WORK_DATE,
               COH.OVERHEAD_CODE PROJECTCODE,
               TRH.approved_hours WORK_HOURS,
               NVL(TRH.PH2_ID,'0010000100') PH2,                  /* Other General */
               NVL(CC.CUSTOMER_ID,'349EFFBE0D8249A49028EB18275C6C96') CUSTOMER    /* Advances from customers */
          FROM TS_RESOURCEHOUR TRH
          LEFT JOIN CM_CUSTOMER CC ON CC.CUSTOMER_ID = TRH.CUSTOMER_ID
          INNER JOIN CM_USERTASK CUT ON TRH.USERTASK_ID = CUT.USERTASK_ID AND CUT.TASK_TYPE = 'OVERHEAD'
          INNER JOIN CM_OVERHEAD COH ON COH.OVERHEAD_ID = CUT.TASK_ID
          WHERE COH.OVERHEAD_CODE in ('SUP/001','SUP/002','SUP/003','SUP/004','SUP/005','SUP/006','SUP/007','SUP/008','SUP/009','SUP/010','VAC/001')

        UNION ALL
        /* UCS的项目报工部分 */
        SELECT CU.USER_ID USERID,
               VRTI.WORK_DATE,
               VRTI.PROJECTCODE,
               VRTI.WORK_HOURS,
               P.PH2,
               P.CUSTOMER
          FROM V2_RPT_TIMESHEETITEM VRTI
          LEFT JOIN CM_USERS CU on VRTI.EMPLOYEEID = CU.USER_NAME
          LEFT JOIN CM_PROJ P on VRTI.PROJECTCODE = P.PROJ_SHORT_NAME AND P.ORIG_PROJ_ID IS NULL AND P.PROJ_NODE_FLAG ='Y'
           WHERE VRTI.PROJECTCODE not in ('SUP/001','SUP/002','SUP/003','SUP/004','SUP/005','SUP/006','SUP/007','SUP/008','SUP/009','SUP/010','VAC/001')
           AND VRTI.Work_Date > to_date('2015/05/01','YYYY/MM/DD')

         UNION ALL
         /* UCS的生产性非项目报工 */
         SELECT CU.user_id USERID,
               VRTI.WORK_DATE,
               VRTI.PROJECTCODE,
               VRTI.WORK_HOURS,
               '0000500100' PH2,                /* ECU General */
               '349EFFBE0D8249A49028EB18275C6C96' CUSTOMER    /* Advances from customers */
          FROM V2_RPT_TIMESHEETITEM VRTI
          LEFT join cm_users CU on VRTI.employeeid = CU.user_name
         where VRTI.PROJECTCODE in ('SUP/001','SUP/002','SUP/003','SUP/004','SUP/005','SUP/006')
           AND VRTI.Work_Date > to_date('2015/05/01','YYYY/MM/DD')

        UNION ALL
        /* UCS的非生产性非项目报工 */
        SELECT CU.user_id USERID,
               VRTI.WORK_DATE,
               VRTI.PROJECTCODE,
               VRTI.WORK_HOURS,
               '0010000100' PH2,                /* Other General */
               '349EFFBE0D8249A49028EB18275C6C96' CUSTOMER    /* Advances from customers */
          FROM V2_RPT_TIMESHEETITEM VRTI
          left join cm_users CU on VRTI.employeeid = CU.user_name
         where VRTI.projectcode in ('SUP/007','SUP/008','SUP/009','SUP/010','VAC/001')
           AND VRTI.Work_Date > to_date('2015/05/01','YYYY/MM/DD')

 ) TS GROUP BY userId, WORK_DATE, projectCode, ph2, customer;

 -- 无改动
 create table FI_SAP_COST
(
  sap_cost_id         VARCHAR2(40) default sys_guid() not null,
  glaccount           VARCHAR2(80),
  costcenter          VARCHAR2(80),
  internalorderno     VARCHAR2(40),
  orderdesc           VARCHAR2(80),
  docheadtext         VARCHAR2(80),
  doctype             VARCHAR2(80),
  transactioncurrency VARCHAR2(40),
  transactionvalue    VARCHAR2(80),
  covalue             NUMBER(17,2),
  flag                VARCHAR2(8),
  postingdate         DATE
)


--增加BU列以后 V2_V_RPT_TIMESHEETITEM
CREATE OR REPLACE VIEW V2_V_RPT_TIMESHEETITEM AS
SELECT userId,
       WORK_DATE,
       projectCode,
       SUM(WORK_HOURS) WORK_HOURS,
       ph2,
       customer,
       bu
  FROM (
      /* uPMS系统的项目报工部分*/
      SELECT   CUT.USER_ID USERID,
               TRH.timesheet_date WORK_DATE,
               CP.PROJ_SHORT_NAME PROJECTCODE,
               TRH.approved_hours WORK_HOURS,
               CP.PH2,
               CP.CUSTOMER,
               '' BU
          FROM TS_RESOURCEHOUR TRH
          INNER JOIN CM_USERTASK CUT ON TRH.USERTASK_ID = CUT.USERTASK_ID AND CUT.TASK_TYPE <> 'OVERHEAD'
          INNER JOIN CM_TASK CT ON TO_CHAR(CT.TASK_ID) = CUT.TASK_ID
          INNER JOIN CM_PROJ CP ON CP.PROJ_OBJECT_ID = CT.PROJ_ID AND CP.ORIG_PROJ_ID IS NULL AND CP.PROJ_NODE_FLAG ='Y'

        UNION ALL
        /* uPMS系统的非项目报工部分 */
        SELECT CUT.USER_ID USERID,
               TRH.timesheet_date WORK_DATE,
               COH.OVERHEAD_CODE PROJECTCODE,
               TRH.approved_hours WORK_HOURS,
               NVL(TRH.PH2_ID,'0010000100') PH2,                  /* Other General */
               NVL(CC.CUSTOMER_ID,'349EFFBE0D8249A49028EB18275C6C96') CUSTOMER ,   /* Advances from customers */
               TRH.BU_TAG
          FROM TS_RESOURCEHOUR TRH
          LEFT JOIN CM_CUSTOMER CC ON CC.CUSTOMER_ID = TRH.CUSTOMER_ID
          INNER JOIN CM_USERTASK CUT ON TRH.USERTASK_ID = CUT.USERTASK_ID AND CUT.TASK_TYPE = 'OVERHEAD'
          INNER JOIN CM_OVERHEAD COH ON COH.OVERHEAD_ID = CUT.TASK_ID
          WHERE COH.OVERHEAD_CODE in ('SUP/001','SUP/002','SUP/003','SUP/004','SUP/005','SUP/006','SUP/007','SUP/008','SUP/009','SUP/010','VAC/001')

        UNION ALL
        /* UCS的项目报工部分 */
        SELECT CU.USER_ID USERID,
               VRTI.WORK_DATE,
               VRTI.PROJECTCODE,
               VRTI.WORK_HOURS,
               P.PH2,
               P.CUSTOMER,
               '' BU
          FROM V2_RPT_TIMESHEETITEM VRTI
          LEFT JOIN CM_USERS CU on VRTI.EMPLOYEEID = CU.USER_NAME
          LEFT JOIN CM_PROJ P on VRTI.PROJECTCODE = P.PROJ_SHORT_NAME AND P.ORIG_PROJ_ID IS NULL AND P.PROJ_NODE_FLAG ='Y'
           WHERE VRTI.PROJECTCODE not in ('SUP/001','SUP/002','SUP/003','SUP/004','SUP/005','SUP/006','SUP/007','SUP/008','SUP/009','SUP/010','VAC/001')
           AND VRTI.Work_Date > to_date('2015/05/01','YYYY/MM/DD')

         UNION ALL
         /* UCS的生产性非项目报工 */
         SELECT CU.user_id USERID,
               VRTI.WORK_DATE,
               VRTI.PROJECTCODE,
               VRTI.WORK_HOURS,
               '0000500100' PH2,                /* ECU General */
               '349EFFBE0D8249A49028EB18275C6C96' CUSTOMER,    /* Advances from customers */
               '' BU
          FROM V2_RPT_TIMESHEETITEM VRTI
          LEFT join cm_users CU on VRTI.employeeid = CU.user_name
         where VRTI.PROJECTCODE in ('SUP/001','SUP/002','SUP/003','SUP/004','SUP/005','SUP/006')
           AND VRTI.Work_Date > to_date('2015/05/01','YYYY/MM/DD')

        UNION ALL
        /* UCS的非生产性非项目报工 */
        SELECT CU.user_id USERID,
               VRTI.WORK_DATE,
               VRTI.PROJECTCODE,
               VRTI.WORK_HOURS,
               '0010000100' PH2,                /* Other General */
               '349EFFBE0D8249A49028EB18275C6C96' CUSTOMER ,   /* Advances from customers */
               '' BU
          FROM V2_RPT_TIMESHEETITEM VRTI
          left join cm_users CU on VRTI.employeeid = CU.user_name
         where VRTI.projectcode in ('SUP/007','SUP/008','SUP/009','SUP/010','VAC/001')
           AND VRTI.Work_Date > to_date('2015/05/01','YYYY/MM/DD')

 ) TS GROUP BY userId, WORK_DATE, projectCode, ph2, customer,bu
 

 -- 查询条件保存表
create table v2_search_history(
id  varchar2(40) default sys_guid() not null,
s_user varchar2(40) not null,
s_name varchar(40) not null,
s_content varchar2(200) 
)


-- Create table 项目预算表
create table V2_BUDGET
(
  id        VARCHAR2(40) default sys_guid() not null,
  proj_code VARCHAR2(40) not null,
  peo       VARCHAR2(40),
  mad       VARCHAR2(40),
  ser       VARCHAR2(40),
  time      DATE default sysdate not null
)

-- Add comments to the table 
comment on table V2_BUDGET
  is '项目预算，用来存储每一个项目各部分费用';
-- Add comments to the columns 
comment on column V2_BUDGET.proj_code
  is '项目编号';
comment on column V2_BUDGET.peo
  is '人工费';
comment on column V2_BUDGET.mad
  is '设备费';
comment on column V2_BUDGET.ser
  is '三项费用';
comment on column V2_BUDGET.time
  is '每次更新就有记录，以日期最大最为项目当前预算';
  
--项目预算CPC中间表
  -- Create table
create table V2_BUDGET_CPC
(
  actualtotal VARCHAR2(100),
  budget      VARCHAR2(100),
  month       VARCHAR2(40),
  proj_id     VARCHAR2(100) not null,
  id          VARCHAR2(40) default sys_guid() not null,
  updatetime  DATE default sysdate not null,
  monthcost   VARCHAR2(100)
)
-- Add comments to the table 
comment on table V2_BUDGET_CPC
  is '项目预算建立的中间表：为了报表显示,以月份维度显示。';
-- Add comments to the columns 
comment on column V2_BUDGET_CPC.actualtotal
  is '项目总花费';
comment on column V2_BUDGET_CPC.budget
  is '当期预算';
comment on column V2_BUDGET_CPC.month
  is '月份';
comment on column V2_BUDGET_CPC.proj_id
  is '项目号';
comment on column V2_BUDGET_CPC.updatetime
  is '取得项目总费用的最新值';
comment on column V2_BUDGET_CPC.monthcost
  is '项目上月花费';

  --里程碑点CPC表
  -- Create table
create table V2_MILESTONE_CPC
(
  id              VARCHAR2(100) default sys_guid() not null,
  month           VARCHAR2(100),
  proj_short_name VARCHAR2(100) not null,
  qa4             VARCHAR2(100),
  qa3             VARCHAR2(100),
  qa2             VARCHAR2(100),
  qa1             VARCHAR2(100),
  qa0             VARCHAR2(100)
)
-- Add comments to the table 
comment on table V2_MILESTONE_CPC
  is '里程碑点存放对应的日期';
-- Add comments to the columns 
comment on column V2_MILESTONE_CPC.month
  is '月份标签';
comment on column V2_MILESTONE_CPC.proj_short_name
  is '项目名称,包括系统项目和子项目';
comment on column V2_MILESTONE_CPC.qa2
  is '如果是系统项目则对应正式批量供货时间';
comment on column V2_MILESTONE_CPC.qa0
  is '如果是系统项目则对应项目启动会议时间';

 --里程碑点系统项目视图
  create or replace view v2_v_milestone_sys as
select proj_short_name,
       max(case task_code when 'M1010' then plan_end_date else TO_DATE('2000/01/01','YYYY/MM/DD') end) as STARTMEETING,
       max(case task_code when 'M1020' then plan_end_date else  TO_DATE('2000/01/01','YYYY/MM/DD') end) as QA0,
       max(case task_code when 'M1030' then plan_end_date else  TO_DATE('2000/01/01','YYYY/MM/DD') end) as QA3,
       max(case task_code when 'M1040' then plan_end_date else  TO_DATE('2000/01/01','YYYY/MM/DD') end) as BATCHSUPPLY,
       max(case task_code when 'M1050' then plan_end_date else  TO_DATE('2000/01/01','YYYY/MM/DD') end) as QA4
from (

      select p.proj_short_name,
             t.TASK_CODE,
             t.TASK_NAME,
             t.PLAN_END_DATE
        from cm_proj p

        join cm_projwbs w
          on p.proj_object_id = w.PROJ_ID
         and w.PROJ_NODE_FLAG = 'Y'

        join cm_task t
          on t.PROJ_ID = p.proj_object_id
         and t.WBS_ID = w.WBS_ID

       where p.orig_proj_id is null
         and p.proj_node_flag = 'Y'
         and t.TASK_TYPE = 'TT_FinMile'
         and t.STATUS = 'TK_NotStart'
         and p.proj_sys_type = 'SYS'
         --and p.proj_sys_type in ('NEW','QUOTATION','RUNNING','PENDING','RISKRELEASE','ESOP')

 )  group by proj_short_name
    order by proj_short_name;

    --里程碑点子项目视图
    create or replace view v2_v_milestone_other as
select proj_short_name,
       max(case task_code when 'M1000' then plan_end_date else TO_DATE('2000/01/01','YYYY/MM/DD') end) as QA0,
       max(case task_code when 'M1010' then plan_end_date else  TO_DATE('2000/01/01','YYYY/MM/DD') end) as QA1,
       max(case task_code when 'M1020' then plan_end_date else  TO_DATE('2000/01/01','YYYY/MM/DD') end) as QA2,
       max(case task_code when 'M1030' then plan_end_date else  TO_DATE('2000/01/01','YYYY/MM/DD') end) as QA3,
       max(case task_code when 'M1040' then plan_end_date else  TO_DATE('2000/01/01','YYYY/MM/DD') end) as QA4
from (

      select p.proj_short_name,
             t.TASK_CODE,
             t.TASK_NAME,
             t.PLAN_END_DATE
        from cm_proj p

        join cm_projwbs w
          on p.proj_object_id = w.PROJ_ID
         and w.PROJ_NODE_FLAG = 'Y'

        join cm_task t
          on t.PROJ_ID = p.proj_object_id
         and t.WBS_ID = w.WBS_ID

       where p.orig_proj_id is null
         and p.proj_node_flag = 'Y'
         and t.TASK_TYPE = 'TT_FinMile'
         and t.STATUS = 'TK_NotStart'
         and (p.proj_sys_type ='SYS.ECU' or p.proj_sys_type ='SYS.COMP')
         --and p.proj_sys_type in ('NEW','QUOTATION','RUNNING','PENDING','RISKRELEASE','ESOP')

 )  group by proj_short_name
    order by proj_short_name;
