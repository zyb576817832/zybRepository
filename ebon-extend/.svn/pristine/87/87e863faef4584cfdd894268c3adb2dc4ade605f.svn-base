-- 人员、叶子部门、报表部门
select u.user_id userId , u.user_name userName,rptDept.* from cm_users u join (
       select * from (
       select  leaf.Dept_No_Path leafDeptNoPath,leaf.dept_no leafDeptNo,leaf.dept_name leafDeptName

       from cm_depart leaf 

       where leaf.dept_no not in (select parent_dept_no from cm_depart) 
) leafDept


join (
     select rptDept.Dept_No_Path rptDeptNoPath,rptDept.Dept_Code rptDeptCode,rptDept.Dept_Name rptDeptName from cm_depart rptDept


     where ceil(length(rptDept.Dept_No_Path)/36) = 4
) 
     rptDept on INSTR（leafDept.leafDeptNoPath,rptDept.rptDeptNoPath) = 1
) rptDept

on u.dept_id = rptDept.leafDeptNo



--- 人员历史部门


select userId,fromDate,toDate,leafDeptNo leafDeptCode,leafDeptName,rptDeptCode,rptDeptName from(
  select userId,deptId,fromDate,nvl2(max(toDate),max(toDate),sysdate) toDate from(
    select cud1.user_id userId,cud1.dept_id deptId,cud1.record_date fromDate, cud2.record_date toDate from cm_user_dept_history cud1

    left join cm_user_dept_history cud2 

    on cud1.user_id = cud2.user_id and cud1.record_date < cud2.record_date
    ) v group by userId,deptId,fromDate
 ) udh
 
 left join
 
 (
       select * from (
       select  leaf.Dept_No_Path leafDeptNoPath,leaf.dept_no leafDeptNo,leaf.dept_name leafDeptName

       from cm_depart leaf 

       where leaf.dept_no not in (select parent_dept_no from cm_depart) 
) leafDept


join (
     select rptDept.Dept_No_Path rptDeptNoPath,rptDept.Dept_Code rptDeptCode,rptDept.Dept_Name rptDeptName from cm_depart rptDept


     where ceil(length(rptDept.Dept_No_Path)/36) = 4
) 
     rptDept on INSTR（leafDept.leafDeptNoPath,rptDept.rptDeptNoPath) = 1
 
 ) leafDept
 
 on udh.deptId = leafDept.leafDeptNo



--USC 人员部门历史

select  userId,
nvl2(max(fromDate),max(fromDate) ,to_date('2000/1/1','yyyy/mm/dd')) fromDate,
toDate,
leafDeptName leafDeptCode,
leafDeptName,
rptDeptName rptDeptCode,  
rptDeptName  from(

select v2.userId,v1.wDate fromDate,v2.wDate toDate, v2.leafDeptName, v2.rptDeptName from

(

       select employeeid userId,max(to_date(work_date,'yyyy/mm/dd')) wDate ,dept3 leafDeptName,dept1 rptDeptName 
       from v2_rpt_timesheetitem 
       where employeeid not in (select user_id from cm_users)
       
       group by employeeid,dept3,dept1
 ) v1  
 
 right join
 
 (

       select employeeid userId,max(to_date(work_date,'yyyy/mm/dd')) wDate ,dept3 leafDeptName,dept1 rptDeptName 
       from v2_rpt_timesheetitem 
       where employeeid not in (select user_id from cm_users)
       
       group by employeeid,dept3,dept1
 ) v2 
 
on v1.userId = v2.userId and v1.wDate < v2.wDate

) v group by userId,toDate,leafDeptName,rptDeptName
  

