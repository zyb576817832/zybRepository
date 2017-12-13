CREATE OR REPLACE VIEW V2_V_RPT_PROJECT AS
SELECT P.PROJ_SHORT_NAME        AS PROJ_CODE,
          P.INTERNALORDER          AS PROJ_INTERNALORDER,
          P.PROJ_NAME                AS PROJ_DESCRIPTION,
          CO1.CODE_NAME            AS PROJ_CATEGORY,
          CO2.CODE_NAME            AS PROJ_HNTE,
          CO3.CODE_NAME              AS PROJ_TECH_CATEGORY,
          PHT.ENAME                 AS PH1,
          PH.ENAME                   AS PH2,
          PH.PARENT_ID               AS PH1_CODE,
          PH.PH_ID                     AS PH2_CODE,
          C.COMPANY_NAME_CN       AS CUSTOMER_GROUP,
          C.SAP_CODE                 AS SAP_CODE,
          C.SAP_NAME                 AS SAP_NAME 
    FROM CM_PROJ P 
   LEFT JOIN CM_PH PH ON P.PH2 = PH.PH_ID 
   LEFT JOIN CM_PH PHT ON PH.PARENT_ID = PHT.PH_ID 
   LEFT JOIN CM_CUSTOMER C ON P.CUSTOMER = C.CUSTOMER_ID 
   LEFT JOIN CM_CODE CO1 ON P.PROJ_TYPE = CO1.ID 
   LEFT JOIN CM_CODE CO2 ON P.HNTE = CO2.ID
   LEFT JOIN CM_CODE CO3 ON P.TECH_CTGRY_1 = CO3.ID 
   WHERE P.ORIG_PROJ_ID IS NULL 
     AND P.PROJ_NODE_FLAG = 'Y' 
     
     
     
CREATE OR REPLACE VIEW V2_V_RPT_TIMESHEETITEM AS   


SELECT user_id userId,timesheet_date WORK_DATE,projectCode,approved_hours WORK_HOURS FROM TS_RESOURCEHOUR rh

join

(


SELECT V1.user_id,V1.usertask_id,taskId,projectCode FROM 

(
       SELECT * FROM 

CM_USERTASK UT where task_type <> 'OVERHEAD'

) V1


 JOIN  

(
SELECT t.TASK_ID taskId, p.proj_short_name projectCode,task_type from

CM_TASK T  join cm_proj p on t.PROJ_ID = p.proj_object_id where task_type <> 'OVERHEAD'
) v on to_char(V1.task_id) = to_char(v.taskid)

)v

on rh.usertask_id = v.usertask_id


UNION

SELECT EMPLOYEEID USERID,TO_DATE(WORK_DATE,'yyyy/mm/dd'),PROJECTCODE,WORK_HOURS FROM V2_RPT_TIMESHEETITEM





