package com.gs.spider.controller.home;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gs.spider.controller.BaseController;
import com.gs.spider.utils.AppInfo;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController extends BaseController {

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("panel/welcome/welcome");
		modelAndView.addObject("appName", AppInfo.APP_NAME).addObject("appVersion", AppInfo.APP_VERSION)
				.addObject("onlineDocumentation", AppInfo.ONLINE_DOCUMENTATION);
		return modelAndView;
	}
}
