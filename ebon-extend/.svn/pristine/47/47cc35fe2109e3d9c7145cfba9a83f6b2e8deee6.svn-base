SELECT user_id userId,timesheet_date,projectCode,approved_hours FROM TS_RESOURCEHOUR rh

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
