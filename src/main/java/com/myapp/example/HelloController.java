package com.myapp.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.example.repositories.MyDataRepository;

@Controller
public class HelloController {
	
	@Autowired
	MyDataRepository repository;
	
	/**
	 * indexメソッド
	 * @param mydata
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView index(
			@ModelAttribute("formModel") MyData mydata,
			ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg", "This is sample content");
		Iterable<MyData> list = repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}
	
	/**
	 * formメソッド
	 * @param myData
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/", method=RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView form(
			@ModelAttribute("formModel") MyData myData,
			ModelAndView mav) {
		repository.saveAndFlush(myData);
		return new ModelAndView("redirect:/");
	}
	
}
