select 
    p.PROJ_CODE          projectCode,
    p.PROJ_INTERNALORDER projectInternalOrder,
    p.PROJ_DESCRIPTION   projectDescription,
    p.PROJ_CATEGORY      projectCategory,
    p.PROJ_HNTE          projectHnte,
    p.PROJ_TECH_CATEGORY projectTechCategory,         
    ph.ph1Name           ph1,
    ph.ph2Name           ph2,
    ph.ph1Code           ph1Code,
    ph.ph2Code           ph2Code,
    tur.bu           bu,
    c.name             customerGroupCode,    
    c.cSapCode           sapCode,
    c.cSapName           sapName,    
    p.PROJ_TYPE      PROJ_TYPE,
    cc.name employeeDept,
    to_char(round(avg(Rate))) employeeRate,
    to_char(sum(tur.WORK_HOURS)) employeeHours,
    to_char(sum(tur.employeeAmount)) employeeAmount,    
    '' equipmentDept,
    '' equipmentGroup,
    '' equipmentName,
    '' equipmentRate,
    '' equipmentHours,
    '' euqipmentAmount,    
    '' COSTCENTER,
    '' costElement,
    '' costTransationtype,
    '' costTransationcost,
    '' costCOCost,        
    to_char(sum(tur.employeeAmount)) totalProjectCost   
    from (SELECT T.userId,
                 WORK_DATE,
                 projectCode,
                 WORK_HOURS,
                 ph2,
                 customer,
                 bu,
                 NVL2(MAX(RATE), MAX(RATE), 0) RATE,
                 WORK_HOURS * NVL2(MAX(RATE), MAX(RATE), 0) employeeAmount
            from V2_V_RPT_TIMESHEETITEM t          
            LEFT join (                      
                      select USERID,
                              uch.fromDate costCenterFromDate,
                              uch.toDate   costCenterToDate,
                              CR.startDate rateStartDate,
                              CR.endDate   rateEndDate,
                              RATE
                        from V2_V_RPT_COSTCENTER_RATE CR
                        join V2_V_RPT_UCH uch on cr.costcenterId = uch.costcenterId                    
                      ) UR
              ON T.userId = UR.USERID
             and t.WORK_DATE >= costCenterFromDate
             and t.WORK_DATE < costCenterToDate
             and t.WORK_DATE >= rateStartDate
             and t.WORK_DATE < rateEndDate
           GROUP BY T.userId,
                    WORK_DATE,
                    projectCode,
                    WORK_HOURS,
                    ph2,
                    bu,
                    customer) tur
  join V2_v_Rpt_ALLProject p on tur.projectCode = p.PROJ_CODE
  left join V2_V_RPT_UCH uch on tur.userId = uch.userId and tur.WORK_DATE >= uch.fromDate and tur.WORK_DATE < uch.toDate
  left join CM_COSTCENTER CC ON uch.costcenterId = CC.id
  left join V2_V_RPT_PH ph on tur.ph2 = ph.ph2Code
  left join V2_V_RPT_CUSTOMER C ON tur.customer = C.id
  where tur.WORK_DATE >= to_date('2015/3/1' , 'yyyy/mm/dd') and tur.WORK_DATE < to_date('2015/3/13' || ' 23:59:59' , 'yyyy/mm/dd hh24:mi:ss')  
  group by p.PROJ_CODE,
           p.PROJ_INTERNALORDER,
           p.PROJ_DESCRIPTION,
           p.PROJ_CATEGORY,
           p.PROJ_HNTE,
           p.PROJ_TECH_CATEGORY,
           p.PROJ_TYPE,
           ph.ph2Code,
           ph.ph2Name,
           ph.ph1Code,
           ph.ph1Name,
           c.name,  
           c.cSapCode,
           c.cSapName,         
           cc.name,
           tur.bu

union all

  select p.PROJ_CODE          projectCode,
         p.PROJ_INTERNALORDER projectInternalOrder,
         p.PROJ_DESCRIPTION   projectDescription,
         p.PROJ_CATEGORY      projectCategory,
         p.PROJ_HNTE          projectHnte,
         p.PROJ_TECH_CATEGORY projectTechCategory,              
         ph.ph1Name           ph1,
         ph.ph2Name           ph2,
         ph.ph1Code           ph1Code,
         ph.ph2Code           ph2Code,
         ''          bu,
         c.name             customerGroupCode,         
         c.cSapCode           sapCode,
         c.cSapName           sapName,         
         p.PROJ_TYPE      PROJ_TYPE,         
         '' employeeDept,
         '' employeeRate,
         '' employeeHours,
         '' employeeAmount,         
         tur.eqDepartment equipmentDept,
         tur.eqName equipmentGroup,
         tur.eqCnName equipmentName,
         to_char(tur.RATE) equipmentRate,
         to_char(sum(tur.WORK_HOURS)) equipmentHours,
         to_char(sum(tur.euqipmentAmount)) euqipmentAmount,         
         '' COSTCENTER,
         '' costElement,
         '' costTransationtype,
         '' costTransationcost,
         '' costCOCost,         
         to_char(sum(tur.euqipmentAmount)) totalProjectCost

  from (select work_date,
               equipmentcode,
               projectcode,
               WORK_HOURS,
               RATE,
               eqName,
               eqCnName,
               eqDescription,
               eqDepartment,
               WORK_HOURS * RATE euqipmentAmount,
               p.PH2_CODE ph2,
               p.customer
          from v2_v_rpt_usageitem u
          LEFT join V2_V_RPT_PROJECT p  ON u.projectcode = p.PROJ_CODE) tur
  join V2_v_Rpt_ALLProject p on tur.projectCode = p.PROJ_CODE
  left join V2_V_RPT_PH ph on tur.ph2 = ph.ph2Code
  left join V2_V_RPT_CUSTOMER C ON tur.customer = C.id
  where tur.WORK_DATE >= to_date('2015/3/1' , 'yyyy/mm/dd') and tur.WORK_DATE < to_date('2015/3/13' || ' 23:59:59' , 'yyyy/mm/dd hh24:mi:ss')
  group by p.PROJ_CODE,
           p.PROJ_INTERNALORDER,
           p.PROJ_DESCRIPTION,
           p.PROJ_CATEGORY,
           p.PROJ_HNTE,
           p.PROJ_TECH_CATEGORY,
           p.PROJ_TYPE,
           ph.ph2Code,
           ph.ph2Name,
           ph.ph1Code,
           ph.ph1Name,
           c.name,
           c.cSapCode,
           c.cSapName,           
           tur.eqDepartment,
           tur.eqName,
           tur.eqCnName,
           tur.RATE

union all

  select p.PROJ_CODE          projectCode,
         p.PROJ_INTERNALORDER projectInternalOrder,
         p.PROJ_DESCRIPTION   projectDescription,
         p.PROJ_CATEGORY      projectCategory,
         p.PROJ_HNTE          projectHnte,
         p.PROJ_TECH_CATEGORY projectTechCategory,            
         ph.ph1Name           ph1,
         ph.ph2Name           ph2,
         ph.ph1Code           ph1Code,
         ph.ph2Code           ph2Code,
         ''          bu,
         c.name             customerGroupCode,       
         c.cSapCode           sapCode,
         c.cSapName           sapName,         
         p.PROJ_TYPE      PROJ_TYPE,       
         '' employeeDept,
         '' employeeRate,
         '' employeeHours,
         '' employeeAmount,         
         '' equipmentDept,
         '' equipmentGroup,
         '' equipmentName,
         '' equipmentRate,
         '' equipmentHours,
         '' euqipmentAmount,         
         tur.COSTCENTER COSTCENTER,
         tur.GLACCOUNT costElement,
         costTransationtype costTransationtype,
         to_char(sum(tur.costTransationcost)) costTransationcost,
         to_char(sum(tur.costCOCost)) costCOCost,
         to_char(sum(tur.costCOCost)) totalProjectCost
  from (SELECT P.PROJ_SHORT_NAME AS projectCode,
               GLACCOUNT,
               TRANSACTIONCURRENCY AS costTransationtype,
               CC.NAME AS COSTCENTER,
               D.DEPT_NAME AS SAP_DEPT_NAME,
               D.DEPT_NO AS SAP_DEPT_NO,
               vp.PH2_CODE ph2,
               vp.CUSTOMER,
               POSTINGDATE WORK_DATE,
               CASE
                 WHEN SC.FLAG = 'S' THEN
                  TO_NUMBER(SC.TRANSACTIONVALUE)
                 WHEN SC.FLAG = 'H' THEN
                  -TO_NUMBER(SC.TRANSACTIONVALUE)
               END AS costTransationcost,               
               CASE
                 WHEN SC.FLAG = 'S' THEN
                  TO_NUMBER(SC.COVALUE)
                 WHEN SC.FLAG = 'H' THEN
                  -TO_NUMBER(SC.COVALUE)
               END AS costCOCost
          FROM FI_SAP_COST SC
          LEFT JOIN CM_COSTCENTER CC    ON SC.COSTCENTER = CC.CODE
          LEFT JOIN CM_DEPART D   ON CC.DEPT_ID = D.DEPT_NO
          LEFT JOIN CM_PROJ P  ON SC.INTERNALORDERNO = P.INTERNALORDER AND P.ORIG_PROJ_ID IS NULL AND P.PROJ_NODE_FLAG='Y'
          LEFT JOIN V2_V_RPT_PROJECT VP  ON P.PROJ_SHORT_NAME = VP.PROJ_CODE
         WHERE P.PROJ_SHORT_NAME IS NOT NULL
        union all
        SELECT O.OVERHEAD_CODE AS PROJ_CODE,
               GLACCOUNT,
               TRANSACTIONCURRENCY AS costTransationtype,
               CC.NAME AS COSTCENTER,
               D.DEPT_NAME AS SAP_DEPT_NAME,
               D.DEPT_NO AS SAP_DEPT_NO,
               '' ph2,
               '' CUSTOMER,
               POSTINGDATE WORK_DATE,
               CASE
                 WHEN SC.FLAG = 'S' THEN
                  TO_NUMBER(SC.TRANSACTIONVALUE)
                 WHEN SC.FLAG = 'H' THEN
                  -TO_NUMBER(SC.TRANSACTIONVALUE)
               END AS TRANSACTIONVALUE,             
               CASE
                 WHEN SC.FLAG = 'S' THEN
                  TO_NUMBER(SC.COVALUE)
                 WHEN SC.FLAG = 'H' THEN
                  -TO_NUMBER(SC.COVALUE)
               END AS COVALUE   
          FROM FI_SAP_COST SC
          LEFT JOIN CM_COSTCENTER CC  ON SC.COSTCENTER = CC.CODE
          LEFT JOIN CM_DEPART D   ON CC.DEPT_ID = D.DEPT_NO
          LEFT JOIN CM_OVERHEAD O   ON SC.INTERNALORDERNO = O.INTERNAL_ORDER
         WHERE O.OVERHEAD_CODE IS NOT NULL) tur
  join V2_v_Rpt_ALLProject p on tur.projectCode = p.PROJ_CODE
  LEFT join V2_V_RPT_PH ph on tur.ph2 = ph.ph2Code
  LEFT join V2_V_RPT_CUSTOMER C ON tur.customer = C.id
  where tur.WORK_DATE >= to_date('2015/3/1' , 'yyyy/mm/dd') and tur.WORK_DATE < to_date('2015/3/13' || ' 23:59:59' , 'yyyy/mm/dd hh24:mi:ss')
  group by p.PROJ_CODE,
           p.PROJ_INTERNALORDER,
           p.PROJ_DESCRIPTION,
           p.PROJ_CATEGORY,
           p.PROJ_HNTE,
           p.PROJ_TECH_CATEGORY,
           p.PROJ_TYPE,
           ph.ph2Code,
           ph.ph2Name,
           ph.ph1Code,
           ph.ph1Name,
           c.name,  
           c.cSapCode,
           c.cSapName,         
           tur.COSTCENTER,
       tur.GLACCOUNT,
           tur.costTransationtype
           
           
--根据项目编号和日期获取设备费用
 select  to_char(sum(tur.euqipmentAmount)) euqipmentAmount      
  from (select work_date,
               equipmentcode,
               projectcode,
               WORK_HOURS,
               RATE,
               eqName,
               eqCnName,
               eqDescription,
               eqDepartment,
               WORK_HOURS * RATE euqipmentAmount,
               p.PH2_CODE ph2,
               p.customer
          from v2_v_rpt_usageitem u
          LEFT join V2_V_RPT_PROJECT p  ON u.projectcode = p.PROJ_CODE) tur
  join V2_v_Rpt_ALLProject p on tur.projectCode = p.PROJ_CODE
  left join V2_V_RPT_PH ph on tur.ph2 = ph.ph2Code
  left join V2_V_RPT_CUSTOMER C ON tur.customer = C.id
  where tur.WORK_DATE >= to_date('2015/3/1' , 'yyyy/mm/dd') and tur.WORK_DATE < to_date('2015/3/13' || ' 23:59:59' , 'yyyy/mm/dd hh24:mi:ss') and p.PROJ_CODE='991/BE-10008.1-DS1'  
  
  --根据项目编号和日期获取人员费用