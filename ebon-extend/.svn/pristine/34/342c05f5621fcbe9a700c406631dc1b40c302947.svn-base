package com.ebon.v2.eai.lims.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.util.Config;
import com.ebon.platform.util.StringUtil;
import com.ebon.v2.eai.lims.model.ProjectInfo;
import com.ebon.v2.eai.lims.model.ProjectInfoCommand;
import com.starlims.www.webservices.GenericServices;
import com.starlims.www.webservices.GenericServicesLocator;
import com.starlims.www.webservices.GenericServicesSoap;

@Component
public class MainProjectInfoService extends BaseService {

	@Config("rpt.limsTimesheetWebServiceUrl")
	String limsTimesheetWebServiceUrl;
	
	@Config("rpt.limsTimesheetWebServiceUsername")
	String limsTimesheetWebServiceUsername;
	
	@Config("rpt.limsTimesheetWebServicePwd")
	String limsTimesheetWebServicePwd;

	public ProjectInfoCommand pushProjectInfo() throws Exception {
		/**
		 * 1, 获取需要推送的项目信息 2，调用Lims的相关webservice 3，如果成功记录时间
		 */
		List<ProjectInfo> projectInfoes = getProjectInfos();
		ProjectInfoCommand projectInfoCommand = doPush(projectInfoes);
		signProjectInfoCommand(projectInfoCommand, projectInfoes);
		return projectInfoCommand;

	}

	public ProjectInfoCommand pushAllProjectInfo() throws Exception {
		/**
		 * 1, 获取需要推送的项目信息 2，调用Lims的相关webservice 3，如果成功记录时间
		 */
		List<ProjectInfo> projectInfoes = getAllProjectInfos();
		ProjectInfoCommand projectInfoCommand = doPush(projectInfoes);
		signProjectInfoCommand(projectInfoCommand, projectInfoes);
		return projectInfoCommand;

	}

	private void signProjectInfoCommand(ProjectInfoCommand projectInfoCommand, List projectInfoes) throws DaoException {
		myBatisDao.save("V2Mapper.insertProjectInfoCommand", projectInfoCommand);
	}

	private ProjectInfoCommand doPush(List projectInfoes) throws UnsupportedEncodingException, MalformedURLException, ServiceException {
		ProjectInfoCommand projectInfoCommand = new ProjectInfoCommand();
		if (projectInfoes.size() == 0) {
			projectInfoCommand.setStatus(ProjectInfoCommand.STATUS_NODATA);
		} else {
			byte[] data = this.buildXml(projectInfoes, projectInfoCommand);
			projectInfoCommand.setData(new String(data));
			boolean okay = false;
			GenericServices services = new GenericServicesLocator();
			//LIMS厂商的字符集为GBK，所以在这里做了一个字符集转换
			String outputStr = new String(new String(data, "UTF-8").getBytes("GBK"), "GBK");
			GenericServicesSoap soap = services.getGenericServicesSoap(new URL(limsTimesheetWebServiceUrl));
			try{
				Object obj = soap.runActionDirect("WebServices.UPMS.SyncProjectsToLims", new Object[]{outputStr}, limsTimesheetWebServiceUsername, limsTimesheetWebServicePwd);
				Object[] lst = (Object[])obj;
				if (lst.length > 0){
					okay = lst[0].toString().equalsIgnoreCase("true");
				}
			}catch (Exception e) {
				log.warn(e);
				String description = this.getExceptionStackTrace(e);
				projectInfoCommand.setStatus(ProjectInfoCommand.STATUS_FAIL);
				projectInfoCommand.setDescription(description);
			}
			projectInfoCommand.setStatus(okay ? ProjectInfoCommand.STATUS_SUCCESS : ProjectInfoCommand.STATUS_FAIL);
		}
		return projectInfoCommand;
	}

	byte[] buildXml(List projectInfoes, ProjectInfoCommand projectInfoCommand) {
		return buildXmlAccordingToLims(projectInfoes, projectInfoCommand) ;
	}
	
	
	byte[] buildXmlAccordingToLims(List projectInfoes, ProjectInfoCommand projectInfoCommand) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			document.setXmlVersion("1.0");
			document.setXmlStandalone(true);

			Element root = document.createElement("DATASET");
			document.appendChild(root);

			for (int i = 0; i < projectInfoes.size(); i++) {
				ProjectInfo projectInfo = (ProjectInfo) projectInfoes.get(i);
				projectInfo.setProjectInfoCommandId(projectInfoCommand.getId());
				Element elemProject = document.createElement("TABLE");				
				String projectCode = projectInfo.getProjectCode();
				String projectName = projectInfo.getProjectName();
				String projectType = projectInfo.getProjectType();
				String ph1 = projectInfo.getPh1();
				String ph2 = projectInfo.getPh2();				
				String numberB = projectInfo.getNumberB();	
				String numberO = projectInfo.getNumberO();				
				String projectPm = projectInfo.getProjectPm();
				String customerCode =  projectInfo.getCustomerCode();
				String customerName = projectInfo.getCustomerName();
				String SOP = format(projectInfo.getSOP());				
				String createDate = format(projectInfo.getCreateDate());
				String CSOP = projectInfo.getCSOP();
				if(CSOP != null && !CSOP.trim().equals("") && CSOP.length()>10){
					CSOP = CSOP.substring(0, 10);
				}
				String customerABBR = projectInfo.getCustomerABBR();
				String projectSize =  projectInfo.getProjectSize();
				String projectStage =  projectInfo.getProjectStage();
				String projectStatus =  projectInfo.getProjectStatus();
				String sapName = projectInfo.getSapName();				
				String internalOrder  = projectInfo.getInternalOrder();
								
				/**
				 *  <A> PROJECTTYPE </A>
				 *  <B> PROJECTCODE </B>
				 *  <C> PROJECTNAME </C>
				 *  <D> PH1</D>
				 *  <E> PH2</E>
 				 *  <F> NUMBERB </F>
				 *  <G> NUMBER0</G>
				 *  <H> PM </H>
				 *  <I> CUSTOMERCODE </I>
				 *  <J> CUSTOMERNAME </J>
				 *  <K> SOP </K>
				 *  <L> CREATED_DATE </L>
				 *  <M> CSOP </M>
				 *  <N> CUSTOMERABBR </N>
				 *  <O> PROJECTSIZE </O>
				 *  <P> PROJECTSTAGE </P>
				 *  <Q> PROJECTSTATUS </Q>
				 *  <R> INTERNALORDER </R>
				 *  <S> SAP_NAME </S>
				 */
				this.buildProjectElement(document, elemProject, "A", projectType);
				this.buildProjectElement(document, elemProject, "B", projectCode);
				this.buildProjectElement(document, elemProject, "C", projectName);
				this.buildProjectElement(document, elemProject, "D", ph1);
				this.buildProjectElement(document, elemProject, "E", ph2);
				this.buildProjectElement(document, elemProject, "F", numberB);
				this.buildProjectElement(document, elemProject, "G", numberO);
				this.buildProjectElement(document, elemProject, "H", projectPm);
				this.buildProjectElement(document, elemProject, "I", customerCode);
				this.buildProjectElement(document, elemProject, "J", customerName);
				this.buildProjectElement(document, elemProject, "K", SOP);
				this.buildProjectElement(document, elemProject, "L", createDate);
				this.buildProjectElement(document, elemProject, "M", CSOP);
				this.buildProjectElement(document, elemProject, "N", customerABBR);
				this.buildProjectElement(document, elemProject, "O", projectSize);
				this.buildProjectElement(document, elemProject, "P", projectStage);
				this.buildProjectElement(document, elemProject, "Q", projectStatus);
				this.buildProjectElement(document, elemProject, "R", internalOrder);
				this.buildProjectElement(document, elemProject, "S", sapName);			
				root.appendChild(elemProject);
			}

			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transFormer = transFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);

			// export string

			transFormer.transform(domSource, new StreamResult(bos));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bos.toByteArray();
	}
	
	private void buildProjectElement(Document document,Element elemProject,String tagName,String textContent){
		Element elem = document.createElement(tagName);
		// 根据LIMS厂商要求，将空内容强转为null字符串写入XML
		elem.setTextContent(StringUtil.isNotNull(textContent) ? textContent : "null");
		elemProject.appendChild(elem);
	} 
	

	byte[] buildXmlDefault(List projectInfoes, ProjectInfoCommand projectInfoCommand) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			document.setXmlVersion("1.0");

			Element root = document.createElement("projects");
			document.appendChild(root);

			for (int i = 0; i < projectInfoes.size(); i++) {
				ProjectInfo projectInfo = (ProjectInfo) projectInfoes.get(i);
				projectInfo.setProjectInfoCommandId(projectInfoCommand.getId());
				Element elemProject = document.createElement("project");

				String projectCode = "";
				String projectName = "";
				String projectType = "";
				String ph1 = "";
				String ph2 = "";
				
				
				if(projectInfo.getProjectCode() != null){
					projectCode = projectInfo.getProjectCode();
				}
				elemProject.setAttribute("projectCode", projectCode);
				if(projectInfo.getProjectName() != null){
					projectName = projectInfo.getProjectName();
				}
				elemProject.setAttribute("projectName", projectName);
				if(projectInfo.getProjectType() != null){
					projectType = projectInfo.getProjectType();
				}
				elemProject.setAttribute("projectType", projectType);
				if(projectInfo.getPh1() != null){
					ph1 = projectInfo.getPh1();
				}
				elemProject.setAttribute("ph1", ph1);
				if(projectInfo.getPh2() != null){
					ph2 = projectInfo.getPh2();
				}
				elemProject.setAttribute("ph2", ph2 );
				String numberB = "";
				if(projectInfo.getNumberB() != null){
					numberB = projectInfo.getNumberB();
				}
				elemProject.setAttribute("numberB", numberB);
				String numberO = "";
				if(projectInfo.getNumberO() != null){
					numberO = projectInfo.getNumberO();
				}
				elemProject.setAttribute("numberO", numberO);
				String projectPm = "";
				if(projectInfo.getProjectPm() != null){
					projectPm = projectInfo.getProjectPm();
				}
				elemProject.setAttribute("projectPm", projectPm);
				String  customerCode = "";
				if(projectInfo.getCustomerCode() != null){
					customerCode = projectInfo.getCustomerCode();
					
				}
				elemProject.setAttribute("customerCode", customerCode);
				String SOP = "";
				if(projectInfo.getSOP() != null){
					SOP = format(projectInfo.getSOP());
					elemProject.setAttribute("SOP", SOP);
				}
				elemProject.setAttribute("SOP", SOP);
				
				String createDate = "";
				if(projectInfo.getCreateDate().toString() != null){
					createDate = format(projectInfo.getCreateDate());
				}
				elemProject.setAttribute("createDate",createDate);
				String CSOP = "";
				if(projectInfo.getCSOP() != null){
					CSOP = projectInfo.getCSOP();
				}
				elemProject.setAttribute("CSOP", CSOP);
				String customerABBR = "";
				if(projectInfo.getCustomerABBR() != null){
					customerABBR =  projectInfo.getCustomerABBR();
				}
				elemProject.setAttribute("customerABBR", customerABBR);
				String projectSize = "";
				if(projectInfo.getProjectSize() != null){
					projectSize = projectInfo.getProjectSize();
				}
				elemProject.setAttribute("projectSize", projectSize);
				String projectStage = "";
				if(projectInfo.getProjectStage() != null){
					projectStage = projectInfo.getProjectStage();
				}
				elemProject.setAttribute("projectStage", projectStage);
				String projectStatus = "";
				if(projectInfo.getProjectStatus() != null){
					projectStatus = projectInfo.getProjectStatus();
				}
				elemProject.setAttribute("projectStatus", projectStatus);
				String sapName = "";
				if(projectInfo.getSapName() != null){
					sapName = projectInfo.getSapName();
				}
				elemProject.setAttribute("sapName", sapName);
				
				String internalOrder = "";
				if(projectInfo.getSapName() != null){
					internalOrder = projectInfo.getInternalOrder();
				}
				elemProject.setAttribute("internalOrder", internalOrder);
				
				if(projectInfo.getProjectInfoCommandId() != null){
					elemProject.setAttribute("projectInfoCommandId", projectInfo.getProjectInfoCommandId());
				}
				root.appendChild(elemProject);
			}

			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transFormer = transFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);

			// export string

			transFormer.transform(domSource, new StreamResult(bos));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bos.toByteArray();
	}


	private String format(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return		dateFormat.format(date);
	}

	@Config("")
	String mainProjectServiceWebServiceUrl;

	String getExceptionStackTrace(Exception e) {
		e.printStackTrace();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintWriter printWriter = new PrintWriter(baos, true);
		e.printStackTrace(printWriter);
		// return baos.toString();
		return e.getMessage();

	}

	/**
	 * 获取向LIMS同步的增量项目主数据
	 * @return
	 * @throws DaoException
	 */
	private List<ProjectInfo> getProjectInfos() throws DaoException {
		return this.myBatisDao.getList("V2Mapper.selectProjects");
	}
	
	/**
	 * 获取向LIMS同步的全量项目主数据
	 * @return
	 * @throws DaoException
	 */
	private List<ProjectInfo> getAllProjectInfos() throws DaoException {
		return this.myBatisDao.getList("V2Mapper.selectAllProjects");
	}

	public List getProjectInfos(List projectIds) throws DaoException {
		return this.myBatisDao.getList("V2Mapper.selectProjects", projectIds);
	}

}
