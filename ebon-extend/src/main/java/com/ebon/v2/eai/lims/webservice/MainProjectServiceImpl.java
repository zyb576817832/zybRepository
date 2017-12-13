package com.ebon.v2.eai.lims.webservice;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.v2.eai.lims.model.ProjectInfo;
import com.ebon.v2.eai.lims.model.ProjectInfoCommand;

@WebService(endpointInterface = "com.ebon.v2.eai.lims.webservice.MainProjectService") 
public class MainProjectServiceImpl  extends BaseService  implements MainProjectService{
	
	public   List<ProjectInfo>  getMainProjectInfoList(List<String>  projectCodeList){
		List<ProjectInfo> projectInfoes = null;
		try {
			if(projectCodeList.size() != 0){
				if(this.myBatisDao != null){
					projectInfoes = this.myBatisDao.getList("V2Mapper.selectProjectInfoes", projectCodeList);
				}else{
					projectInfoes = this.buildProjectInfoes(projectCodeList);
				}
			}else{
				projectInfoes = new ArrayList();
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projectInfoes;
	}
	
	private List<ProjectInfo> buildProjectInfoes(List<String>  projectCodeList){
		List<ProjectInfo> projectInfoes = new ArrayList();
		for(int i=0;i<projectCodeList.size();i++){
			ProjectInfo projectInfo = new ProjectInfo();
			projectInfo.setProjectCode(projectCodeList.get(i));
			projectInfoes.add(projectInfo);
		}
		return projectInfoes;
	}
	
	public   byte[] getMainProjectInfoes(String[] projectCodes){
		List projectCodeList = new ArrayList();
		for(int i=0;i<projectCodes.length;i++){
			projectCodeList.add(projectCodes[i]);
		}
		List<ProjectInfo>  projectInfoes = this.getMainProjectInfoList(projectCodeList);
		return this.buildXml(projectInfoes);
	}
	
	byte[] buildXml(List projectInfoes) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			document.setXmlVersion("1.0");

			Element root = document.createElement("projects");
			document.appendChild(root);

			for (int i = 0; i < projectInfoes.size(); i++) {
				ProjectInfo projectInfo = (ProjectInfo) projectInfoes.get(i);
				Element elemProject = document.createElement("project");

				if(projectInfo.getProjectCode() != null){
					elemProject.setAttribute("projectCode", projectInfo.getProjectCode());
				}
				if(projectInfo.getProjectName() != null){
					elemProject.setAttribute("projectName", projectInfo.getProjectName());
				}
				if(projectInfo.getProjectType() != null){
					elemProject.setAttribute("projectType", projectInfo.getProjectType());
				}
				if(projectInfo.getPh1() != null){
					elemProject.setAttribute("ph1", projectInfo.getPh1());
				}
				if(projectInfo.getPh2() != null){
					elemProject.setAttribute("ph2", projectInfo.getPh2());
				}
				if(projectInfo.getNumberB() != null){
					elemProject.setAttribute("numberB", projectInfo.getNumberB());
				}
				if(projectInfo.getNumberO() != null){
					elemProject.setAttribute("numberO", projectInfo.getNumberO());
				}
				if(projectInfo.getProjectPm() != null){
					elemProject.setAttribute("projectPm", projectInfo.getProjectPm());
				}
				if(projectInfo.getCustomerCode() != null){
					elemProject.setAttribute("customerCode", projectInfo.getCustomerCode());
				}
				if(projectInfo.getSOP() != null){
					elemProject.setAttribute("SOP", projectInfo.getSOP().toString());
				}
				if(projectInfo.getCreateDate() != null){
					elemProject.setAttribute("createDate", projectInfo.getCreateDate().toString());
				}
				if(projectInfo.getCSOP() != null){
					elemProject.setAttribute("CSOP", projectInfo.getCSOP());
				}
				if(projectInfo.getCustomerABBR() != null){
					elemProject.setAttribute("customerABBR", projectInfo.getCustomerABBR());
				}
				if(projectInfo.getProjectSize() != null){
					elemProject.setAttribute("projectSize", projectInfo.getProjectSize());
				}
				if(projectInfo.getProjectStage() != null){
					elemProject.setAttribute("projectStage", projectInfo.getProjectStage());
				}
				if(projectInfo.getProjectStatus() != null){
					elemProject.setAttribute("projectStatus", projectInfo.getProjectStatus());
				}
				if(projectInfo.getSapName() != null){
					elemProject.setAttribute("sapName", projectInfo.getSapName());
				}
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
	
	
}
