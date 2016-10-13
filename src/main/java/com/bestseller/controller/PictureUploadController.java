package com.bestseller.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bestseller.common.PictureResult;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import net.fckeditor.response.UploadResponse;

@Controller
@RequestMapping("/back")
public class PictureUploadController {
	@ResponseBody
	@RequestMapping("/image/upload.do")
	public PictureResult imageUpload(@RequestParam(value="pic",required=false) MultipartFile multipatFile,HttpServletResponse response ){
		PictureResult result=new PictureResult();
		//文件的扩展名
		String ext=FilenameUtils.getExtension(multipatFile.getOriginalFilename());
		//图片名生成
		String format=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		Random random = new Random();
		int end3 = random.nextInt(999);
		String str = format + String.format("%03d", end3);
		Client client=new Client();
		//数据库路径
		String path="upload/"+str+"."+ext;
		String url="http://localhost:8088/image-web/"+path;
		result.setPath(path);
		result.setUrl(url);
		WebResource resource = client.resource(url);
		try {
			byte[] bytes = multipatFile.getBytes();
			resource.put(String.class,bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping("/upload/uploadFck.do")
	public void uploadFCK(HttpServletRequest request,HttpServletResponse response){
		MultipartHttpServletRequest mt=(MultipartHttpServletRequest)request;
		Map<String, MultipartFile> fileMaps = mt.getFileMap();
		Set<Entry<String, MultipartFile>> entrySet = fileMaps.entrySet();
		for(Entry<String, MultipartFile> fileMap:entrySet){
			MultipartFile multipartFile = fileMap.getValue();
			String ext=FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			DateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String format=df.format(new Date());
			//随机三位数
			Random r = new Random();
			// n 1000   0-999   99
			for(int i=0 ; i<3 ;i++){
				format += r.nextInt(10);
			}
			
			Client client=new Client();
			String path="upload/"+format+"."+ext;
			String url="http://localhost:8088/image-web/"+path;
			WebResource resource = client.resource(url);
			try {
				byte[] bytes = multipartFile.getBytes();
				resource.put(String.class,bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			UploadResponse ok = UploadResponse.getOK(url);
			try {
				response.getWriter().print(ok);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
