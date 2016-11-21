package com.bestseller.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bestseller.common.Constant;
import com.bestseller.common.JsonUtils;
import com.bestseller.common.ResponseUtils;
import com.bestseller.common.md5.Md5Service;
import com.bestseller.common.session.SessionProvider;
import com.bestseller.pojo.Buyer;
import com.bestseller.pojo.City;
import com.bestseller.pojo.Province;
import com.bestseller.pojo.Town;
import com.bestseller.pojo.query.CityQuery;
import com.bestseller.pojo.query.TownQuery;
import com.bestseller.service.BuyerService;
import com.bestseller.service.CityService;
import com.bestseller.service.ProvinceService;
import com.bestseller.service.TownService;
import com.octo.captcha.service.image.ImageCaptchaService;

@Controller
public class PortalController {
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private ImageCaptchaService imgageCaptchaService;
	@Autowired
	private Md5Service md5Service;
	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private TownService townService;
	@RequestMapping(value="/user/userCheck/login.shtml",method=RequestMethod.GET)
	public String login(){
		return "buyer/login";
	}
	
	@RequestMapping(value="/user/userCheck/login.shtml",method=RequestMethod.POST)
	public String login(Buyer buyer,String captcha,String returnUrl,ModelMap model,HttpServletRequest request,HttpServletResponse response){
			if(StringUtils.isNotBlank(buyer.getUsername())){
				if(StringUtils.isNotBlank(buyer.getPassword())){
					if(StringUtils.isNotBlank(captcha)){
						if(imgageCaptchaService.validateResponseForID(sessionProvider.getSessionId(response,request), captcha)){
							Buyer buyered = buyerService.getBuyerByKey(buyer.getUsername());
							if(null!=buyered){
								if(buyered.getPassword().equals(md5Service.md5Encrypt(buyer.getPassword()))){
									sessionProvider.setAttribute(response,request, Constant.SESSION_USER_NAME, buyered);
									if(StringUtils.isNotBlank(returnUrl)&&!returnUrl.equals("null")){
										return "redirect:"+returnUrl;
									}else{
										return "redirect:/buyer/index.shtml";
									}
									
								}else{
									model.addAttribute("error", "输入的密码有误!");
								}
							}else{
								model.addAttribute("error", "此用户不存在!");
							}
						}else{
							model.addAttribute("error", "验证码不匹配!");
						}
					}else{
						model.addAttribute("error", "请输入验证码!");
					}
				}else{
					model.addAttribute("error", "请输入密码!");
				}
			}else{
				model.addAttribute("error", "请输入用户名!");
			}
		
		
		return "buyer/login";
	}
	
	//用户中心
	@RequestMapping("/buyer/index.shtml")
	public String index(){
		return "buyer/index";
	}
	
	//个人资料
	@RequestMapping("/buyer/profile.shtml")
	public String profile(HttpServletResponse response,HttpServletRequest request,ModelMap model){
		Buyer buyer=(Buyer) sessionProvider.getAttribute(response,request, Constant.SESSION_USER_NAME);
		Buyer b = buyerService.getBuyerByKey(buyer.getUsername());
		model.addAttribute("buyer", b);
		List<Province> provinces= provinceService.getProvinceList(null);
		model.addAttribute("provinces", provinces);
		
		//市
		CityQuery cityQuery=new CityQuery();
		cityQuery.setProvince(b.getProvince());
		List<City> citys = cityService.getCityList(cityQuery);
		model.addAttribute("citys", citys);
		
		//县
		TownQuery townQuery=new TownQuery();
		townQuery.setCity(b.getCity());
		List<Town> towns = townService.getTownList(townQuery);
		model.addAttribute("towns", towns);
		return "buyer/profile";
	}
	
	//城市加载
	@RequestMapping("/buyer/city.shtml")
	public void city(String code,HttpServletResponse response){
		CityQuery cityQuery=new CityQuery();
		cityQuery.setProvince(code);
		List<City> citys = cityService.getCityList(cityQuery);
		Map<String,Object> map=new HashMap<>();
		map.put("citys", citys);
		ResponseUtils.renderJson(response,JsonUtils.objectToJson(map));
	}
	
	//县加载
	@RequestMapping("/buyer/town.shtml")
	public void town(String code,HttpServletResponse response){
		TownQuery townQuery=new TownQuery();
		townQuery.setCity(code);
		List<Town> towns = townService.getTownList(townQuery);
		Map<String,Object> map=new HashMap<>();
		map.put("towns", towns);
		ResponseUtils.renderJson(response,JsonUtils.objectToJson(map));
	}
	
	//收货地址
	@RequestMapping("/buyer/deliver_address.shtml")
	public String deliverAddress(){
		return "buyer/deliver_address";
	}
}
