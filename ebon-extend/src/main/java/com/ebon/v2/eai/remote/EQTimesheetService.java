package com.ebon.v2.eai.remote;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface EQTimesheetService {
	
	public  byte[] getEQTimesheet();
	
	public  byte[] getEQTimesheetByDate(@WebParam(name="startDate") String startDate);
}
