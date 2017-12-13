select  userId,
fromDate,
nvl2(max(toDate),max(toDate) ,sysdate) toDate,
leafDeptName leafDeptCode,
leafDeptName,
rptDeptName rptDeptCode,
rptDeptName  from(

select v1.userId,v1.wDate fromDate,v2.wDate toDate, v1.leafDeptName, v1.rptDeptName from

(

       select employeeid userId,max(to_date(work_date,'yyyy/mm/dd')) wDate ,dept1 leafDeptName,dept3 rptDeptName
       from v2_rpt_timesheetitem
       where employeeid not in (select user_id from cm_users)

       group by employeeid,dept3,dept1
 ) v1

 left join

 (

       select employeeid userId,max(to_date(work_date,'yyyy/mm/dd')) wDate ,dept1 leafDeptName,dept3 rptDeptName
       from v2_rpt_timesheetitem
       where employeeid not in (select user_id from cm_users)

       group by employeeid,dept3,dept1
 ) v2

on v1.userId = v2.userId and v1.wDate < v2.wDate

) v group by userId,fromDate,toDate,leafDeptName,rptDeptName;