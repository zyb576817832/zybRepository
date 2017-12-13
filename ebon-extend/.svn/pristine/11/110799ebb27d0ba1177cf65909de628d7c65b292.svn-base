package com.ebon.v2.interceptor;

import org.apache.cxf.interceptor.Fault;  
import org.apache.cxf.message.Message;  
import org.apache.cxf.phase.AbstractPhaseInterceptor;  
import org.apache.cxf.phase.Phase;  

public class CxfSystemEnvIntercepter extends AbstractPhaseInterceptor<Message>{  
  
  
    public CxfSystemEnvIntercepter(String phase) {  
            super(phase);  
    }  
      
    public CxfSystemEnvIntercepter(){  
        super(Phase.RECEIVE);  
    }  
          
    @Override  
    public void handleMessage(Message message) throws Fault {  
          
        if(null==System.getProperty("org.apache.cxf.binding.soap.messageFactoryClassName")||"".equals(System.getProperty("org.apache.cxf.binding.soap.messageFactoryClassName"))){  
              
            System.setProperty("org.apache.cxf.binding.soap.messageFactoryClassName","com.sun.xml.internal.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl");  
//linux 环境下需要做此设置 要不按Woodstox 4.2.0 有点问题  
            System.setProperty("org.apache.cxf.stax.allowInsecureParser","1");      
            System.setProperty("org.apache.cxf.stax.maxChildElements","50000");  
            System.setProperty("org.apache.cxf.stax.maxElementDepth","100");  
            System.setProperty("org.apache.cxf.stax.maxAttributeCount","500");  
            System.setProperty("org.apache.cxf.stax.maxAttributeSize",String.valueOf(64 * 1024));  
            System.setProperty("org.apache.cxf.stax.maxTextLength",String.valueOf(128 * 1024 * 1024));  
            System.setProperty("org.apache.cxf.stax.maxElementCount",String.valueOf(Long.MAX_VALUE));  
            System.setProperty("org.apache.cxf.stax.maxXMLCharacters",String.valueOf(Long.MAX_VALUE));  
            System.setProperty("org.apache.cxf.staxutils.innerElementCountThreshold",String.valueOf(50000));  
            System.setProperty("org.apache.cxf.staxutils.innerElementLevelThreshold",String.valueOf(100));  
        }  
          
          
    }  
      
  
}  
