/**
 * UCSTimesheetService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ekingwin.webservice;

import org.springframework.stereotype.Service;

import com.ebon.platform.util.Config;

public class UCSTimesheetService_ServiceLocator extends org.apache.axis.client.Service implements com.ekingwin.webservice.UCSTimesheetService_Service {

    private java.lang.String UCSTimesheetServiceImplPort_address = null;

	public UCSTimesheetService_ServiceLocator() {
		
    }


    public UCSTimesheetService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UCSTimesheetService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    public java.lang.String getUCSTimesheetServiceImplPortAddress() {
        return UCSTimesheetServiceImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String UCSTimesheetServiceImplPortWSDDServiceName = "UCSTimesheetServiceImplPort";

    public java.lang.String getUCSTimesheetServiceImplPortWSDDServiceName() {
        return UCSTimesheetServiceImplPortWSDDServiceName;
    }

    public void setUCSTimesheetServiceImplPortWSDDServiceName(java.lang.String name) {
        UCSTimesheetServiceImplPortWSDDServiceName = name;
    }

    public com.ekingwin.webservice.UCSTimesheetService_PortType getUCSTimesheetServiceImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UCSTimesheetServiceImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUCSTimesheetServiceImplPort(endpoint);
    }

    public com.ekingwin.webservice.UCSTimesheetService_PortType getUCSTimesheetServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ekingwin.webservice.UCSTimesheetServiceImplPortBindingStub _stub = new com.ekingwin.webservice.UCSTimesheetServiceImplPortBindingStub(portAddress, this);
            _stub.setPortName(getUCSTimesheetServiceImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUCSTimesheetServiceImplPortEndpointAddress(java.lang.String address) {
        UCSTimesheetServiceImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ekingwin.webservice.UCSTimesheetService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ekingwin.webservice.UCSTimesheetServiceImplPortBindingStub _stub = new com.ekingwin.webservice.UCSTimesheetServiceImplPortBindingStub(new java.net.URL(UCSTimesheetServiceImplPort_address), this);
                _stub.setPortName(getUCSTimesheetServiceImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("UCSTimesheetServiceImplPort".equals(inputPortName)) {
            return getUCSTimesheetServiceImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.ekingwin.com/", "UCSTimesheetService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.ekingwin.com/", "UCSTimesheetServiceImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("UCSTimesheetServiceImplPort".equals(portName)) {
            setUCSTimesheetServiceImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
