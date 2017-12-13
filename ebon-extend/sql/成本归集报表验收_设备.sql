-- TBL:LIMS设备报工数据
select sum(t.work_hours) from V2_RPT_USAGEITEM t
where t.WORK_DATE >= to_date('2015/1/1','yyyy/mm/dd') 
and t.WORK_DATE < to_date('2015/5/1','yyyy/mm/dd');

-- 扣除uPMS不存在项目数据
select sum(t.work_hours) from V2_RPT_USAGEITEM t
where not exists (
select * from cm_proj a where a.proj_node_flag='Y' and a.orig_proj_id is null
and t.projectcode = a.proj_short_name
)
and t.WORK_DATE >= to_date('2015/1/1','yyyy/mm/dd') 
and t.WORK_DATE < to_date('2015/5/1','yyyy/mm/dd')

-- 扣除系统项目报工与空项目报工
select sum(t.work_hours) from V2_RPT_USAGEITEM t
where exists (
select * from cm_proj a where a.proj_node_flag='Y' and a.orig_proj_id is null and a.proj_sys_type='SYS'
and t.projectCode = a.proj_short_name
)
and t.WORK_DATE >= to_date('2015/1/1','yyyy/mm/dd') 
and t.WORK_DATE < to_date('2015/5/1','yyyy/mm/dd')