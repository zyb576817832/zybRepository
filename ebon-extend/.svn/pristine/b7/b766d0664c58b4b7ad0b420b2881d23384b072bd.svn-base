package com.ekingwin.webservice;

public class UCSTimesheetServiceProxy implements com.ekingwin.webservice.UCSTimesheetService_PortType {
  private String _endpoint = null;
  private com.ekingwin.webservice.UCSTimesheetService_PortType uCSTimesheetService_PortType = null;
  
  public UCSTimesheetServiceProxy() {
    _initUCSTimesheetServiceProxy();
  }
  
  public UCSTimesheetServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initUCSTimesheetServiceProxy();
  }
  
  private void _initUCSTimesheetServiceProxy() {
    try {
      uCSTimesheetService_PortType = (new com.ekingwin.webservice.UCSTimesheetService_ServiceLocator()).getUCSTimesheetServiceImplPort();
      if (uCSTimesheetService_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)uCSTimesheetService_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)uCSTimesheetService_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (uCSTimesheetService_PortType != null)
      ((javax.xml.rpc.Stub)uCSTimesheetService_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.ekingwin.webservice.UCSTimesheetService_PortType getUCSTimesheetService_PortType() {
    if (uCSTimesheetService_PortType == null)
      _initUCSTimesheetServiceProxy();
    return uCSTimesheetService_PortType;
  }
  
  public byte[] getTimesheet(java.lang.String startDate) throws java.rmi.RemoteException{
    if (uCSTimesheetService_PortType == null)
      _initUCSTimesheetServiceProxy();
    return uCSTimesheetService_PortType.getTimesheet(startDate);
  }
  
  
}