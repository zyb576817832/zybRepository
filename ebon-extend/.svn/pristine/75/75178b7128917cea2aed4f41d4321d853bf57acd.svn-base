-- TBL:uPMS人员报工数据
SELECT sum(t.approved_hours) FROM TS_RESOURCEHOUR t
where t.timesheet_date > to_date('2015/1/1','yyyy/mm/dd') 
and t.timesheet_date < to_date('2015/5/1','yyyy/mm/dd');

-- TBL:过滤掉VAC/001等Overhead的uPMS人员报工数据
select sum(t.work_hours) from (
       SELECT TRH.approved_hours WORK_HOURS,TRH.timesheet_date WORK_DATE
          FROM TS_RESOURCEHOUR TRH
          INNER JOIN CM_USERTASK CUT ON TRH.USERTASK_ID = CUT.USERTASK_ID AND CUT.TASK_TYPE = 'OVERHEAD'
          INNER JOIN CM_OVERHEAD COH ON COH.OVERHEAD_ID = CUT.TASK_ID
         WHERE COH.OVERHEAD_CODE not in ('SUP/001','SUP/002','SUP/003','SUP/004','SUP/005','SUP/006','SUP/007','SUP/008','SUP/009','SUP/010','SUP/011')        
) t where t.WORK_DATE >= to_date('2015/1/1','yyyy/mm/dd') 
and t.WORK_DATE < to_date('2015/5/1','yyyy/mm/dd')
          
-- TBL:UCS人员报工数据
select sum(t.work_hours) from V2_RPT_TIMESHEETITEM t
where t.WORK_DATE >= to_date('2015/1/1','yyyy/mm/dd') 
and t.WORK_DATE < to_date('2015/5/1','yyyy/mm/dd');

-- VIEW：所有的人员报工数据
select sum(t.WORK_HOURS) from V2_V_RPT_TIMESHEETITEM t
where t.WORK_DATE >= to_date('2015/1/1','yyyy/mm/dd') 
and t.WORK_DATE < to_date('2015/5/1','yyyy/mm/dd')

-- VIEW：过滤掉系统项目的人员报工数据（可能与下面重叠）
select sum(t.WORK_HOURS) from V2_V_RPT_TIMESHEETITEM t
where exists (
select * from cm_proj a where a.proj_node_flag='Y' and a.orig_proj_id is null and a.proj_sys_type='SYS'
and t.projectCode = a.proj_short_name
) and t.WORK_DATE >= to_date('2015/1/1','yyyy/mm/dd') 
and t.WORK_DATE < to_date('2015/5/1','yyyy/mm/dd')

-- VIEW：过滤掉uPMS不存在项目报工（可能与上面重叠）
select sum(t.WORK_HOURS) from V2_V_RPT_TIMESHEETITEM t
where not exists (
select * from V2_V_RPT_ALLPROJECT where PROJ_CODE=t.projectcode
)
and t.WORK_DATE >= to_date('2015/1/1','yyyy/mm/dd') 
and t.WORK_DATE < to_date('2015/5/1','yyyy/mm/dd')