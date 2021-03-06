package com.ebon.v3.action;

import java.io.BufferedInputStream; 
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ebon.framework.vo.Result;
import com.ebon.platform.action.BaseAction;
import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.util.Guid;
import com.ebon.platform.vo.CusGridData;
import com.ebon.v3.service.IAppTaskDocService;
import com.ebon.v3.service.IAppUserTaskPlatfromService;
import com.ebon.v3.vo.AppTaskDoc;

@Controller
@RequestMapping("/app/taskDoc")
public class AppTaskDocController extends BaseAction {
	
	@Autowired
	IAppTaskDocService appTaskDocService;
	@Autowired
	IAppUserTaskPlatfromService appUserTaskPlatfromService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(String ids,String updateUser){
		
		Result result = appTaskDocService.delete(ids.split(","), updateUser);
		return result;
	}
	
	@RequestMapping("/getVlist")
	@ResponseBody
	public CusGridData<AppTaskDoc> getVlist(AppTaskDoc query, Page page){
		List<AppTaskDoc> list = appTaskDocService.getVlist(query, page);
		CusGridData<AppTaskDoc> gridData = new CusGridData<AppTaskDoc>(page, list);
		return gridData;
	}
	
	/**
	 * 
	 * @param file
	 * @param userId
	 * @param taskId
	 * @param type 统一任务中心第一季tabs标示  QX 20180108
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file, String userId, String taskId, String type, HttpServletRequest request, Model model){
		saveFile(request, file, userId, taskId);
		model.addAttribute("userId", userId);
		model.addAttribute("type", type);
		model.addAttribute("taskId", taskId);
		return "v3/task/doc";
	}
	
	@RequestMapping("/downloadFile")
	public void downloadFile(String pathId, String fileName, HttpServletRequest request, HttpServletResponse response){
		String realPath = request.getSession().getServletContext().getRealPath("/")+ "v3upload/";
		File file = new File(realPath, pathId);
		if(file.exists()){
			try {
				response.setContentType("application/octet-stream");
				response.addHeader("Content-Disposition",
						"attachment;filename="+new String((fileName).getBytes("gb2312"),"ISO8859-1"));
				FileInputStream fis = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
						response.getOutputStream());
				int c = bis.read();//读取bis流中的下一个字节
				while (c != -1) {
					bufferedOutputStream.write(c);
					c = bis.read();
				}
				bufferedOutputStream.flush();
				bis.close();
				fis.close();
			} catch (Exception e) {
				// TODO: handle exception
			}			 
		}
	}
	@RequestMapping
	@ResponseBody
	public Result judgeStatus(String id){
		
		
		return null;
	}
	
	private boolean saveFile(HttpServletRequest request, MultipartFile file, String userId, String taskId) {
		boolean ret = false;
		if (!file.isEmpty()){
			try {
				//将数据库实例的Id作为该目录下的文件名
				String pathId = saveDoc(taskId, userId, file.getOriginalFilename(), file.getSize());
				String filePath = request.getSession().getServletContext()
						.getRealPath("/")
						+ "v3upload/" + pathId;
				System.err.println(filePath);
				File saveDir = new File(filePath);
				if (!saveDir.getParentFile().exists())
					saveDir.getParentFile().mkdirs();
				System.err.println(filePath);
				// 转存文件  
				file.transferTo(saveDir);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
			}  
		}
		return ret;
	}
	
	/**
	 * 实例化到数据库文档信息
	 * @param size 
	 * @param name 
	 * @param userId 
	 * @param taskId 
	 * @return
	 */
	private String saveDoc(String taskId, String userId, String name, long size){
		AppTaskDoc doc = new AppTaskDoc();
		doc.setId(Guid.getGuid());
		doc.setTaskId(taskId);
		doc.setCreateUser(userId);
		doc.setName(name);
		doc.setFileSize(String.valueOf(size));
		appTaskDocService.add(doc);
		return doc.getId();
	}
}
