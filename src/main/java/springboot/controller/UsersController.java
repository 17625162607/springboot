package springboot.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import springboot.bean.Users;
import springboot.service.IRoleService;
import springboot.service.IUsersService;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private IUsersService usersService;
    
    @Autowired
    private IRoleService roleService;
    
    @RequestMapping("/tolist")
    public String tolist(){
    	return "userslist";
    }
    
    @RequestMapping("/usersinfo")
    @ResponseBody
    public String userinfo(String qname,String page,String rows){
    	Map<String,Object> map=new HashMap<>();
    	map.put("name", qname);
    	map.put("pageNum", page);
    	map.put("pageSize", rows);
    	String json="";
    	try {
			json=usersService.selectUsers(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return json;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(Users user,HttpServletRequest request){
    	MultipartRequest mreq=(MultipartRequest)request;
		MultipartFile file = mreq.getFile("userfile");
		int k=0;
		try {
			if(file!=null){
				String filename=file.getOriginalFilename();
				String filepath="E:\\upload";
				//上传
				if(!new File(filepath).exists()){
					new File(filepath).mkdir();
				}
				File f=new File(filepath,filename);
				file.transferTo(f);
				//将表单基本信息保存到数据库
				user.setFilepath(filepath+"\\"+filename);
			}
			k=usersService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
    	
    }
    
   //删除
  	@RequestMapping("/del")
  	@ResponseBody
  	public String del(String ids){
  		int k=0;
  		try {
  			k=usersService.del(ids);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		return String.valueOf(k);
  	} 
  	
  	@RequestMapping("/getObjById")
  	@ResponseBody
  	public String getObjById(String id){
  		Users users = null;
  		try {
			users = usersService.getObjById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		String user = JSONObject.toJSONString(users);
  		return user;
  	}
  	
    //下载附件
  	@RequestMapping("/downfile")
  	public void downfile(String id,HttpServletResponse response){
  		try {
  			Users user=usersService.getObjById(id);
  			String filepatname=user.getFilepath();
  			String filename=filepatname.substring(filepatname.lastIndexOf("\\")+1,filepatname.length());
  			File file=new File(filepatname);
  			if(file==null || !file.exists()){
  				return;
  			}
  			filename = new String(filename.getBytes(),"ISO8859-1");
              response.setContentType("application/octet-stream;charset=ISO8859-1");
              response.setHeader("Content-Disposition", "attachment;filename="+ filename);
              response.addHeader("Pargam", "no-cache");
              response.addHeader("Cache-Control", "no-cache");
              OutputStream out = response.getOutputStream();
              out.write(FileUtils.readFileToByteArray(file));
              out.flush();
              out.close();
          } catch (Exception e) {
              e.printStackTrace();
          }
  	}
  	
    //导出excel
  	@RequestMapping("/exportExcel")
  	@ResponseBody
  	public void exportExcel(HttpServletRequest req,HttpServletResponse response){
  		//查询条件
  		String qname=req.getParameter("name");
  		logger.info("查询条件qname=="+qname);
  		Map<String,Object> map=new HashMap<String,Object>();
  		map.put("name", qname);
  		HSSFWorkbook wb = null;
		try {
			wb = usersService.exportExcel(map);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
  		
  		String fileName = "用户信息表"+System.currentTimeMillis()+".xls";
          try {
  			fileName = new String(fileName.getBytes(),"ISO8859-1");
  			response.setContentType("application/octet-stream;charset=ISO8859-1");
  	        response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
  	        response.addHeader("Pargam", "no-cache");
  	        response.addHeader("Cache-Control", "no-cache");
  	        OutputStream os = response.getOutputStream();
  	        wb.write(os);
  	        os.flush();
  	        os.close();
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  	}
  	
    //分配角色
  	@RequestMapping("/getRoles")
  	@ResponseBody
  	public String getRoles(String userid){
  		String json="";
  		try {
  			json=roleService.getRoles(userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		return json;
  	}
    //保存用户 与 角色 到用户角色表
  	@RequestMapping("/saveUserRole")
  	@ResponseBody
  	public String saveUserRole(String userid,String roleid){
  		int k=0;
  		try {
  			k=usersService.saveUserRole(userid,roleid);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		return String.valueOf(k);
  	}
}
