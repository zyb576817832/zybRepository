package com.ebon.v2.eai.remote;

import javax.jws.WebService;

@WebService
public interface MainProjectService {
	
	public  boolean  setMainProjectInfoList (byte[] data);
}
