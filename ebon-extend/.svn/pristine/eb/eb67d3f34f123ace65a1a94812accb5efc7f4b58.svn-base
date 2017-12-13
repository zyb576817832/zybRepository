package com.ebon.v2.eai.helper;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import weblogic.apache.org.apache.velocity.VelocityContext;
import weblogic.apache.org.apache.velocity.app.VelocityEngine;
import weblogic.apache.org.apache.velocity.exception.VelocityException;

import com.ebon.platform.util.Config;




@Component
public class MailHelper {
	 Logger log = Logger.getLogger(MailHelper.class);  
     
	 	@Autowired
	    private JavaMailSender javaMailSender;  
	    private VelocityEngine velocityEngine;          //模板解析  
	      
	      
	    /** 
	     * @param mailBean 
	     * @return 
	     * @throws MessagingException 
	     */  
	    public boolean send(MailBean mailBean) throws MessagingException {  
	  
	        MimeMessage msg = createMimeMessage(mailBean);  
	        if(msg == null){  
	            return false;  
	        }  
	          
	        this.sendMail(msg, mailBean);  
	          
	        return true;  
	    }  
	      
	    private void sendMail(MimeMessage msg, MailBean mailBean){  
	                javaMailSender.send(msg);  
	                System.out.println("$$$ Send mail Subject:" +  mailBean.getSubject()   
	                        + ", TO:" + arrayToStr(mailBean.getToEmails()) );  
	  
	    }  
	      
	    /* 
	     * 记日记用的 
	     */  
	    private String arrayToStr(String[] array){  
	        if(array == null || array.length == 0){  
	            return null;  
	        }  
	        StringBuffer sb = new StringBuffer();  
	        for(String str : array){  
	            sb.append(str+" , ") ;  
	        }  
	        return sb.toString();  
	    }  
	  
	    /*  
	     * 根据 mailBean 创建 MimeMessage 
	     */  
	    private MimeMessage createMimeMessage(MailBean mailBean) throws MessagingException {  
	        if (!checkMailBean(mailBean)) {  
	            return null;  
	        }  
//	        String text = getMessage(mailBean);
	        String text = mailBean.getContent();
	        if(text == null){  
	            log.warn("@@@ warn mail text is null (Thread name="   
	                    + Thread.currentThread().getName() + ") @@@ " +  mailBean.getSubject());  
	            return null;  
	        }  
	        MimeMessage msg = javaMailSender.createMimeMessage();  
	        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");  
	        //messageHelper.setFrom(mailBean.getFrom());  
	        try {  
	            messageHelper.setFrom(this.mailFrom, this.mailUserName);  
	        } catch (UnsupportedEncodingException e) {  
	  
	        }  
	        messageHelper.setSubject(mailBean.getSubject());  
	        messageHelper.setTo(mailBean.getToEmails());  
	        messageHelper.setText(text, true); // html: true  
	          
	        return msg;  
	    }  
	      
	    /* 
	     * 模板解析 
	     * @param mailBean 
	     * @return 
	     */  
	    private String getMessage(MailBean mailBean) {  
	        StringWriter writer = null;  
	        try {  
	  
	            writer = new StringWriter();  
	            VelocityContext context = new VelocityContext(mailBean.getData());  
	  
	            velocityEngine.evaluate(context, writer, "", mailBean.getContent());  
	  
	            return writer.toString();  
	        } catch (VelocityException e) {  
	            log.error(" VelocityException : " + mailBean.getSubject() + "\n" + e);  
	        } catch (IOException e) {  
	            log.error(" IOException : " + mailBean.getSubject() + "\n" + e);  
	        } finally {  
	            if (writer != null) {  
	                try {  
	                    writer.close();  
	                } catch (IOException e) {  
	                    log.error("###StringWriter close error ... ");  
	                }  
	            }  
	        }  
	        return null;  
	    }  
	      
	    /* 
	     * check 邮件 
	     */  
	    private boolean checkMailBean(MailBean mailBean){  
	        if (mailBean == null) {  
	            log.warn("@@@ warn mailBean is null (Thread name="   
	                    + Thread.currentThread().getName() + ") ");  
	            return false;  
	        }  
	        if (mailBean.getSubject() == null) {  
	            log.warn("@@@ warn mailBean.getSubject() is null (Thread name="   
	                    + Thread.currentThread().getName() + ") ");  
	            return false;  
	        }  
	        if (mailBean.getToEmails() == null) {  
	            log.warn("@@@ warn mailBean.getToEmails() is null (Thread name="   
	                    + Thread.currentThread().getName() + ") ");  
	            return false;  
	        }  
	        if (mailBean.getContent() == null) {  
	            log.warn("@@@ warn mailBean.getTemplate() is null (Thread name="   
	                    + Thread.currentThread().getName() + ") ");  
	            return false;  
	        }  
	        return true;  
	    }  
	  
	      
	    /*===================== setter & getter =======================*/  
	    public void setJavaMailSender(JavaMailSender javaMailSender) {  
	        this.javaMailSender = javaMailSender;  
	    }  
	  
	    public void setVelocityEngine(VelocityEngine velocityEngine) {  
	        this.velocityEngine = velocityEngine;  
	    }  
	    
	    @Config("mail.from")
	    String mailFrom;
	    @Config("mail.username")
	    String mailUserName;
}
