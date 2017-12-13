select to_date(ui.work_date,'YYYY-MM-DD') work_date,equipmentcode,projectcode,WORK_HOURS,RATE,
name eqName,cnName eqCnName,description eqDescription,department eqDepartment
 from v2_rpt_usageitem ui left join (

select e.*, to_date(work_date,'YYYY-MM-DD') w_date from  v2_equipment e join (
       select code , max(to_date(work_date,'YYYY-MM-DD')) d from v2_equipment e group by e.code 
       ) mv

       on e.code = mv.code and mv.d = to_date(e.work_date,'YYYY-MM-DD')
      ) equipment 
      
      on ui.equipmentcode = equipment.code