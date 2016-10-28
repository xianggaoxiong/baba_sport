package com.bestseller.service.staticPage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class StaticPageServiceImpl implements StaticPageService ,ServletContextAware{
	
	private Configuration conf;
	private ServletContext servletContext;
	
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer fmc){
		this.conf=fmc.getConfiguration();
	}

	@Override
	public void productStaticized(Map<String, Object> map,Integer id) {
		Writer out=null;
		String path=servletContext.getRealPath("/html/product/"+id+".html");
		try {
			Template template = conf.getTemplate("productDetail.html");
			File file=new File(path);
			File parentFile=file.getParentFile();
			if(!parentFile.exists()){
				parentFile.mkdirs();
			}
			out=new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			template.process(map, out);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext=servletContext;
		
	}

}
